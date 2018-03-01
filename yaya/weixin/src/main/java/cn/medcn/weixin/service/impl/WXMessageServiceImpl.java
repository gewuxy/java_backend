package cn.medcn.weixin.service.impl;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.XMLUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.config.WeixinEventType;
import cn.medcn.weixin.dto.TemplateMessageDTO;
import cn.medcn.weixin.model.PubWxReply;
import cn.medcn.weixin.service.WXMessageService;
import cn.medcn.weixin.service.WXReplyService;
import cn.medcn.weixin.service.WXTokenService;
import cn.medcn.weixin.service.WXUrlService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.mapper.Mapper;
import io.netty.handler.codec.http2.Http2SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.medcn.common.service.BaseService.DEFAULT_CACHE;

/**
 * 微信消息服务
 * Created by lixuan on 2017/7/24.
 */
@Service
public class WXMessageServiceImpl extends WXBaseServiceImpl implements WXMessageService {

    @Autowired
    private WXUrlService wxUrlService;

    @Autowired
    protected WXTokenService wxTokenService;

    @Autowired
    private WXReplyService wxReplyService;

    @Value("${app.yaya.base}")
    protected String appBaseUrl;

    @Override
    public Mapper getBaseMapper() {
        return null;
    }

    @Override
    protected WXTokenService getWxTokenService() {
        return wxTokenService;
    }

    @Override
    public String autoReply(String serverName, String openid, String sceneId, String pubUserName, boolean bindStatus) {
        String msgType = "text";
        String content;
        if (bindStatus) {
            if (StringUtils.isEmpty(sceneId)) {
                content = "感谢关注YaYa医师。";
            } else {
                content = "感谢关注“" + pubUserName + "”YaYa医师单位号。";
            }
        } else {
            if (StringUtils.isEmpty(sceneId)) {
                content = autoReplyNoScene();
            } else {
                content = autoReplyHasScene(sceneId, pubUserName);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ToUserName", openid);
        jsonObject.put("FromUserName", serverName);
        jsonObject.put("MsgType", msgType);
        jsonObject.put("Content", content);
        jsonObject.put("CreateTime", System.currentTimeMillis());
        return XMLUtils.jsonToXML(jsonObject);
    }

    private String autoReplyNoScene() {
        String content;
        try {
            content = XMLUtils.doParseXML(this.getClass().getClassLoader().getResourceAsStream(AUTO_REPLY_XML_FILE_NAME), DEFAULT_REPLY_ID, REPLY_CONTENT_KEY);
            content = content.replaceAll(BIND_URL_KEY, getBindUrl(null));
            content = replaceBr(content);
            return content;
        } catch (Exception e) {
            return null;
        }

    }

    private String autoReplyHasScene(String sceneId, String pubUserName) {
        String content;
        try {
            content = XMLUtils.doParseXML(this.getClass().getClassLoader().getResourceAsStream(AUTO_REPLY_XML_FILE_NAME), SCENE_REPLY_ID, REPLY_CONTENT_KEY);
            content = content.replaceAll(BIND_URL_KEY, getBindUrl(sceneId));
            content = content.replaceAll(UNIT_NAME_KEY, pubUserName);
            content = replaceBr(content);
            return content;
        } catch (Exception e) {
            return null;
        }
    }


    private String replaceBr(String content) {
        if (!StringUtils.isEmpty(content)) {
            content = content.replaceAll("<[Bb][Rr] ?/?>", "\n");
        }
        return content;
    }

    private String getBindUrl(String sceneId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("weixin/bind");
        if (!CheckUtils.isEmpty(sceneId)) {
            buffer.append("?")
                    .append(WeixinConfig.SCENE_ID_KEY)
                    .append("=")
                    .append(sceneId);
        }
        return wxUrlService.generateWXURL(buffer.toString());
    }


    @Override
    public TemplateMessageDTO build(TEMPLATE_MESSAGE message, String openid, String url, String remark, String... values) {
        TemplateMessageDTO.Builder builder = new TemplateMessageDTO.Builder(openid);
        builder.first(message.first)
                .remark(remark)
                .template_id(message.messageId)
                .url(url)
                .values(values);
        return builder.build();
    }

    @Override
    public void send(TemplateMessageDTO messageDTO) {
        String accessToken = wxTokenService.getGlobalAccessToken();
        String msgSendURL = WeixinConfig.TEMPLATE_MESSAGE_SEND_URL;
        JSONObject jsonObject = (JSONObject) JSON.toJSON(messageDTO);
        wxPostJSON(msgSendURL, accessToken, jsonObject);
    }

    @Override
    public String formatMessageDate(Date date) {
        return new SimpleDateFormat("yyyy年M月d日 HH:mm").format(date);
    }

    @Override
    public String passiveResponse(Map<String,String> data) {
        String msgType = data.get(WeixinEventType.EVENT_MSG_TYPE);
        String content = data.get(WeixinEventType.EVENT_CONTENT);
        String serverName = data.get(WeixinEventType.EVENT_FROM_USERNAME);
        String openid = data.get(WeixinEventType.EVENT_TO_USRENAME);
        String message =null;
        String questions = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ToUserName", serverName);
        jsonObject.put("FromUserName", openid);
        jsonObject.put("MsgType", msgType);
        jsonObject.put("CreateTime", System.currentTimeMillis());
        Integer num ;
        if (isNumeric(content)){
             num = Integer.valueOf(content).intValue();

        }else{
            num = 0;
        }
        message = noReply(num, message, questions, content);
        message = hasReply(message,num);
        jsonObject.put("Content", message);
        return XMLUtils.jsonToXML(jsonObject);
    }

    @Override
    public void send(TEMPLATE_MESSAGE message, String openid, String url, String remark, String... values) {
        TemplateMessageDTO messageDTO = build(message, openid, url, remark, values);
        send(messageDTO);
    }

    @Override
    public void sendByMeetId(TEMPLATE_MESSAGE message, String openid, String meetId, String remark, String... values) {
        String meetClickUrl = appBaseUrl + "weixin/meet/info?meetId="+meetId;
        send(message, openid, meetClickUrl, remark, values);
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    @Cacheable(value = DEFAULT_CACHE,key = "'auto_reply_question'")
    public  String noReply(Integer num,String message,String questions,String content){
        List<PubWxReply> pubWxReplyList = wxReplyService.selectByContent(num);
        if (pubWxReplyList.size()>0){
            for (PubWxReply pubWxReply:pubWxReplyList) {
                questions +=pubWxReply.getAnswerId()+"、"+pubWxReply.getQuestion()+"\n";
                if (Integer.valueOf(content).intValue() == pubWxReply.getId()){
                    message="【"+pubWxReply.getId()+"】"+pubWxReply.getContent()+"\n";
                    message+=questions;
                }else {
                    if (Integer.valueOf(content).intValue() == pubWxReply.getAnswerId()){
                        message=pubWxReply.getAnswerId()+"、"+pubWxReply.getQuestion()+"\n"+pubWxReply.getAnswer();
                    }
                }
            }
        }
        return message;
    }

    @Cacheable(value = DEFAULT_CACHE,key = "'auto_reply_parent_question'")
    public String hasReply(String message,Integer num){
        List<PubWxReply> pubWxReplyList = wxReplyService.selectByContent(num);
        if (pubWxReplyList.size() == 0){
            List<PubWxReply> replyList = wxReplyService.selectAll();
            message ="回复以下数字查找相关问题:\n";
            for (PubWxReply pubWxReply:replyList) {
                message+="【"+pubWxReply.getId()+"】"+pubWxReply.getContent()+"\n";
            }
        }
        return message;
    }
}
