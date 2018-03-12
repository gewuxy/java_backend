package cn.medcn.api.wexin;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.FileUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.XMLUtils;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.Principal;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.config.WeixinEventType;
import cn.medcn.weixin.dto.SignatureDTO;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.pay.WXPayUtil;
import cn.medcn.weixin.service.WXMenuService;
import cn.medcn.weixin.service.WXMessageService;
import cn.medcn.weixin.service.WXOauthService;
import cn.medcn.weixin.service.WXUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static cn.medcn.weixin.config.WeixinEventType.EVENT_KEY;

/**
 * Created by lixuan on 2017/7/18.
 */
@Controller
@RequestMapping(value = "/weixin")
public class CallBackController extends BaseController {

    private final static Log log = LogFactory.getLog(CallBackController.class);

    @Value("${WeChat.custom_token}")
    private String weChatCustomToken;

    @Value("${WeChat.Server.app_id}")
    private String appId;

    @Value("${WeChat.Server.app_secret}")
    private String secret;

    @Value("${WeChat.EncodingAESKey}")
    private String encodingAESKey;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private WXMessageService wxMessageService;

    @Autowired
    private WXUserInfoService wxUserInfoService;

    @Autowired
    protected WXOauthService wxOauthService;


    /**
     * 微信被动回调接口
     * @param signature
     * @param request
     * @return
     */
    @RequestMapping(value = "/callback")
    @ResponseBody
    public String callback(SignatureDTO signature, HttpServletRequest request){
        if (!StringUtils.isEmpty(signature.getEchostr())){
            LogUtils.debug(log, "微信回调接口设置成功");
            return signature.getEchostr();
        } else {
            return handleEvent(request);
        }
    }

    /**
     * 处理事件
     * @param request
     * @return
     */
    private String handleEvent(HttpServletRequest request){
        String postData = WeixinConfig.DEFAULT_REPLY_SUCESS;
        String responseXML;
        try {
            request.setCharacterEncoding("utf-8");
            responseXML = FileUtils.readFromInputStream(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return postData;
        }
        Map<String, String> map;
        try {
            map = WXPayUtil.xmlToMap(responseXML);
            String event = map.get(EVENT_KEY);
            if (event != null){
                return handleEvent(event, map);
            }else{
                //处理被动回复消息
                return handleMessage(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return WeixinConfig.DEFAULT_REPLY_SUCESS;
        }
    }

    /**
     * 需要做处理的事件
     * 1 用户关注
     * 2 用户取消关注
     * 3 已关注用户扫码
     * @param event
     * @param data
     * @return
     */
    protected String handleEvent(String event, Map<String, String> data){
        String reply = WeixinConfig.DEFAULT_REPLY_SUCESS;
        switch (event){
            case WeixinEventType.EVENT_TYPE_SUBSCRIBE : //关注事件
                reply =  handleSubscribe(data);
                break;
            case WeixinEventType.EVENT_TYPE_UNSUBSCRIBE : //取消关注事件
                reply = handleUnSubscribe(data);
                break;
            case WeixinEventType.EVENT_TYPE_SCAN : //已关注用户扫描事件
                reply = handleScan(data);
                break;
            case WeixinEventType.EVENT_TYPE_CLICK ://公众号菜单点击事件
                reply = handleClick(data);
                break;
            default:
                break;
        }
        return reply;
    }

    /**
     * 处理用户关注事件
     * @param data
     * @return
     */
    protected String handleSubscribe(Map<String, String> data){
        String openid = data.get(WeixinEventType.EVENT_FROM_USERNAME);
        String sceneId = data.get("EventKey");
        String serverName = data.get(WeixinEventType.EVENT_TO_USRENAME);
        return bindUser(openid, sceneId, serverName);
    }


    /**
     * 绑定用户同时返回欢迎语
     * @param openid
     * @param sceneId
     * @param serverName
     * @return
     */
    protected String bindUser(String openid, String sceneId, String serverName){
        sceneId = CheckUtils.isEmpty(sceneId)?null:sceneId.substring(sceneId.indexOf("_")+1);
        AppUser slaver = getBindWxUser(openid);
        if(!CheckUtils.isEmpty(sceneId)){
            AppUser pubUser = appUserService.selectByPrimaryKey(Integer.parseInt(sceneId));
            String pubUserName = pubUser == null ? null : pubUser.getNickname();
            //检查是否已经关注了此单位号
            if (slaver != null){
                Integer userId = slaver.getId();
                boolean attention = appUserService.checkAttention(Integer.parseInt(sceneId), userId);
                if (!attention){
                    appUserService.executeAttention(userId, Integer.parseInt(sceneId));
                }
            }
            return wxMessageService.autoReply(serverName,
                    openid,
                    pubUserName == null ? null:sceneId, pubUserName, slaver != null);
        }else{
            return wxMessageService.autoReply(serverName,
                    openid,
                    null, null, slaver != null);
        }
    }

    protected AppUser getBindWxUser(String openid){
        try {
            WXUserInfo oauthUser = wxOauthService.getWXUserInfo(openid);
            if(oauthUser != null){
                WXUserInfo existedWxUser = wxUserInfoService.findWXUserInfo(oauthUser.getUnionid());
                if (existedWxUser != null){
                    /**
                     * 如果已经存在微信用户信息
                     * 将微信用户信心的是否关注改为true ，
                     * openid 改为相对公众号的openid
                     * 只有在已经关注公众号之后才能推送微信消息
                     */
                    existedWxUser.setSubscribe(true);
                    existedWxUser.setOpenid(openid);
                    wxUserInfoService.updateByPrimaryKey(existedWxUser);
                    AppUser appUser = appUserService.findUserByUnoinId(existedWxUser.getUnionid());
                    return appUser;
                }
            }
        } catch (SystemException e) {
           e.printStackTrace();

        }
        return null;
    }

    /**
     * 处理用户扫描事件
     * @param data
     * @return
     */
    protected String handleScan(Map<String, String> data){
        String openid = data.get(WeixinEventType.EVENT_FROM_USERNAME);
        String sceneId = data.get("EventKey");
        String serverName = data.get(WeixinEventType.EVENT_TO_USRENAME);
        return bindUser(openid, sceneId, serverName);
    }

    /**
     * 处理用户取消关注事件
     * @param data
     * @return
     */
    protected String handleUnSubscribe(Map<String, String> data){
        String openid = data.get(WeixinEventType.EVENT_FROM_USERNAME);
        wxUserInfoService.doUnSubscribe(openid);
        return WeixinConfig.DEFAULT_REPLY_SUCESS;
    }

    /**
     * 公众号被动回复
     * @param data
     * @return
     */
    protected String handleMessage(Map<String,String> data){
        return wxMessageService.passiveResponse(data);
    }

    /**
     * 公众号菜单回复入口
     * @param data
     * @return
     */
    protected String handleClick(Map<String,String> data){
        if (data.get(WeixinEventType.EVENT_TYPE_EVENTKEY).equals(WeixinEventType.EVENT_MENU_KEY)){
            return wxMessageService.menuReply(data);
        }
        return null;
    }

}
