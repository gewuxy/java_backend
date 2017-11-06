package cn.medcn.csp.controller.web;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.model.Meet;
import cn.medcn.oauth.service.OauthService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.VideoLiveRecordDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuLP on 2017/10/11/011.
 */
@Controller
@RequestMapping(value = "/mgr/user")
public class UserCenterController extends CspBaseController{

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected OauthService oauthService;

    @Autowired
    protected EmailTempService tempService;



    @Value("${app.file.upload.base}")
    protected String uploadBase;

    @Value("${app.file.base}")
    protected String fileBase;


    /**
     * 个人中心的用户信息
     * @return
     */
    @RequestMapping("/info")
    public String userInfo(Model model){
        addBaseUserInfo(model);
        return localeView("/userCenter/userInfo");
    }

    /**
     * 左侧的基本用户信息
     * @param model
     */
    private CspUserInfoDTO addBaseUserInfo(Model model) {
        String userId = getWebPrincipal().getId();
        CspUserInfoDTO dto = cspUserService.findCSPUserInfo(userId);
        if (!StringUtils.isEmpty(dto.getAvatar())) {
            dto.setAvatar(fileBase + dto.getAvatar());
        }
        List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userId);
        dto.setBindInfoList(bindInfoList);
        model.addAttribute("dto", dto);
        return dto;
    }


    /**
     * 跳转到头像设置页面
     * @return
     */
    @RequestMapping("/toAvatar")
    public String toAvatar(Model model){
        addBaseUserInfo(model);
        return localeView("/userCenter/setAvatar");
    }

    /**
     * 修改用户头像
     *
     * @return
     */

    @RequestMapping(value = "/updateAvatar",method = RequestMethod.POST)
    @ResponseBody
    public String updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file,Integer limitSize){
        if (file == null) {
            return error(local("upload.error.null"));
        }

        if (file.getSize() > limitSize ){
            return error(local("upload.fileSize.err"));
        }

        String userId = getWebPrincipal().getId();
        String url = null;
        try {
            url = cspUserService.updateAvatar(file,userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        Principal principal = getWebPrincipal();
        principal.setAvatar(url);
        SecurityUtils.set(principal);
        return success(url);
    }


    /**
     * 跳转到修改密码页面
     * @param model
     * @return
     */
    @RequestMapping("toReset")
    public String toResetPwd(Model model){
        CspUserInfoDTO dto = addBaseUserInfo(model);
        model.addAttribute("email",dto.getEmail());
        return localeView("/userCenter/pwdReset");

    }

    /**
     * 重置密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping("/resetPwd")
    @ResponseBody
    public String resetPwd(String oldPwd,String newPwd) {

        if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
            return error(local("user.empty.password"));
        }
        String userId = getWebPrincipal().getId();
        try {
            cspUserService.resetPwd(userId,oldPwd,newPwd);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 更新个人信息中的姓名和简介
     */
    @RequestMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(CspUserInfo info) {
        info.setId(getWebPrincipal().getId());
        cspUserService.updateByPrimaryKeySelective(info);
        return success();
    }


    /**
     * 跳转到账号管理页面
     * @return
     */
    @RequestMapping("/toAccount")
    public String toAccount(Model model){
        CspUserInfoDTO dto = addBaseUserInfo(model);
        List<BindInfo> list = dto.getBindInfoList();
        String localStr = LocalUtils.getLocalStr();

        for(BindInfo info:list){
            //英文版
            if(LocalUtils.Local.en_US.name().equals(localStr)){
                if(info.getThirdPartyId() == BindInfo.Type.FACEBOOK.getTypeId()){
                    model.addAttribute("facebook",info.getNickName());
                }
                if(info.getThirdPartyId() == BindInfo.Type.TWITTER.getTypeId()){
                    model.addAttribute("twitter",info.getNickName());
                }
            }else{
                if(info.getThirdPartyId() == BindInfo.Type.WE_CHAT.getTypeId()){
                    model.addAttribute("weChat",info.getNickName());
                }
                if(info.getThirdPartyId() == BindInfo.Type.WEI_BO.getTypeId()){
                    model.addAttribute("weiBo",info.getNickName());
                }
            }

            if(info.getThirdPartyId() == BindInfo.Type.YaYa.getTypeId()){
                model.addAttribute("YaYa",info.getNickName());
            }
        }
        return localeView("/userCenter/accountBind");
    }


    /**
     * 跳转到第三方授权页面
     * @param type
     * @return
     * @throws SystemException
     */
    @RequestMapping("/jumpOauth")
    @ResponseBody
    public String jumpOauthUrl(Integer type) throws SystemException {
        if(type == null){
            return error("请选择要绑定的类型");
        }
        String url = oauthService.jumpThirdPartyAuthorizePage(type);
        return success(url);
    }

    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @return
     */
    @RequestMapping("/bindMobile")
    @ResponseBody
    public String bindMobile(String mobile,String captcha)  {
        if(!StringUtils.isMobile(mobile) || StringUtils.isEmpty(captcha)){
            return error(local("error.param"));
        }
        try {
            //检查验证码合法性
            cspUserService.checkCaptchaIsOrNotValid(mobile,captcha);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        String userId = getWebPrincipal().getId();
        try {
            cspUserService.doBindMobile(mobile,captcha,userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 发送绑定邮件
     */
    @RequestMapping("/bindEmail")
    @ResponseBody
    public String toBind(String email,String password) {

        String userId = getWebPrincipal().getId();
        String localStr = LocalUtils.getLocalStr();
        try {
            cspUserService.sendBindMail(email,password,userId,localStr);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();

    }

    /**
     * 绑定第三方账号
     * @param info
     * @return
     */
   @RequestMapping("/bind")
    @ResponseBody
    public String bind(BindInfo info){
        String userId = getWebPrincipal().getId();
       try {
           cspUserService.doBindThirdAccount(info,userId);
       } catch (SystemException e) {
           return error(e.getMessage());
       }
        return success();
   }

    /**
     * 解绑账号
     * @param type
     * @return
     */
   @RequestMapping("/unbind")
    @ResponseBody
    public String unbind(Integer type){
       String userId = getWebPrincipal().getId();
        //解绑手机或邮箱
        if(type == BindInfo.Type.MOBILE.getTypeId() || type == BindInfo.Type.EMAIL.getTypeId()){
            try {
                cspUserService.doUnbindEmailOrMobile(type,userId);
            } catch (SystemException e) {
                return error(e.getMessage());
            }
            return success();
        }

        //解绑第三方账号
       try {
           cspUserService.doUnbindThirdAccount(type,userId);
       } catch (SystemException e) {
           return error(e.getMessage());
       }

        return success();
   }

    /**
     * 跳转到流量管理页面
     * @param pageable
     * @param model
     * @return
     */
   @RequestMapping("/toFlux")
    public String toFlux(Pageable pageable,Model model){
        addBaseUserInfo(model);
        String userId = getWebPrincipal().getId();
        pageable.put("userId",userId);
        int flux = cspUserService.findFlux(userId);
        model.addAttribute("flux",flux);
        model.addAttribute("now",new Date());
        //直播视频记录
        MyPage<VideoLiveRecordDTO> myPage = cspUserService.findVideoLiveRecord(pageable);
        VideoLiveRecordDTO.transExpireDay(myPage.getDataList());
        model.addAttribute("page",myPage);
       return localeView("/userCenter/toFlux");
   }


}
