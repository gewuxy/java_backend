package cn.medcn.api.controller;

import cn.medcn.api.dto.Principal;
import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.sys.model.SystemProperties;
import cn.medcn.sys.service.SysPropertiesService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.AppUserDetail;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.dto.OAuthDTO;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXOauthService;
import cn.medcn.weixin.service.WXUserInfoService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/3.
 */
@Controller
@RequestMapping(value="/api/user")
public class AppUserController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private JSmsService jSmsService;

    @Autowired
    private WXUserInfoService wxUserInfoService;

    @Autowired
    private WXOauthService wxOauthService;

    @Value("${app.file.upload.base}")
    private String uploadBase;

    @Value("${upload.filetype.allowed}")
    private String fileTypeAllowed;

    @Value("${app.file.base}")
    private String fileBase;

    @Autowired
    private SysPropertiesService sysPropertiesService;

    /**
     * 前端定时请求此接口获取用户信息
     * @return
     */
    @RequestMapping(value="/info")
    @ResponseBody
    public String info(){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        AppUser user = appUserService.selectByPrimaryKey(principal.getId());
        Credits credits = new Credits();
        credits.setUserId(user.getId());
        Credits credit = creditsService.selectOne(credits);
        user.setCredits(credit.getCredit());
        AppUserDetail detail = appUserService.findUserDetail(principal.getId(), user.getRoleId());
        user.setUserDetail(detail);

        //获取微信昵称
        AppUserDTO dto = AppUserDTO.buildFromDoctor(user);
        WXUserInfo wxUserInfo = wxUserInfoService.selectByPrimaryKey(user.getUnionid());
        dto.setWxNickname(wxUserInfo == null ? null:wxUserInfo.getNickname());
        if(!StringUtils.isEmpty(dto.getHeadimg())){
            dto.setHeadimg(fileBase+dto.getHeadimg());
        }
        //设置医院级别
        String hosLevel = dto.getHosLevel();
        if(!StringUtils.isEmpty(hosLevel)){
            SystemProperties properties = new SystemProperties();
            properties.setPropValue(hosLevel);
            properties =  sysPropertiesService.selectOne(properties);
            String picture = properties.getPicture();
            if(properties != null && !StringUtils.isEmpty(picture)){
                properties.setPicture(fileBase + picture);

            }
            //dto.setSystemProperties(properties);
        }

        return success(dto);
    }



    /**
     * 修改用户头像
     * @return
     */
    @RequestMapping(value="/update_avatar", method = RequestMethod.POST)
    @ResponseBody
    public String updateAvatar(@RequestParam(value = "file", required = false)MultipartFile file){
        if(file == null){
            return error("不能上传空文件");
        }
        String suffix =  FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
        //相对路径
        String relativePath = FilePath.PORTRAIT.path + File.separator + UUIDUtil.getNowStringID() + "." + suffix;
        File saveFile = new File(uploadBase+relativePath);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return error("上传头像出错");
        }
        Map<String, String> map = Maps.newHashMap();
        map.put("url", fileBase+relativePath);
        //更新用户头像
        AppUser user = new AppUser();
        user.setId(SecurityUtils.getCurrentUserInfo().getId());
        user.setHeadimg(relativePath);
        appUserService.updateByPrimaryKeySelective(user);
        return success(map);
    }


    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    @RequestMapping(value="/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(AppUserDTO dto){
        if(dto.getHospitalLevel() != null){
            //设置医院级别
            SystemProperties properties = new SystemProperties();
            properties.setId(dto.getHospitalLevel());//前端传递t_properties的id
            properties = sysPropertiesService.selectOne(properties);
            if(properties != null){
                dto.setHosLevel(properties.getPropValue());
            }else {
                return error("医院级别不正确");
            }
        }
        AppUser user = AppUserDTO.rebuildToDoctor(dto);

        Principal principal = SecurityUtils.getCurrentUserInfo();
        user.setId(principal.getId());
        try {
            appUserService.updateDoctor(user);
        } catch (Exception e) {
            e.printStackTrace();
            return error("修改用户信息出错");
        }

        return success();
    }


    /**
     * 设置模块下绑定或解绑微信
     * @param code 如果是绑定操作，传递code
     * @param
     * @return
     *
     */
    @RequestMapping("/set_wx_bind_status")
    @ResponseBody
    public String changeWxBindStatus(String code)  {

        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        AppUser user = appUserService.selectByPrimaryKey(userId);
        String unionId = user.getUnionid();
        //解绑异常
        if(StringUtils.isEmpty(code) && unionId == null){
            return error("用户未绑定,不能执行解绑操作");
            //绑定操作
        }else if(!StringUtils.isEmpty(code)){
            if(unionId != null){
                return error("该yaya账号已绑定微信");
            }
            //获取unionId
            OAuthDTO oAuthDTO = null;
            try {
                oAuthDTO = wxOauthService.getOpenIdAndTokenByCode(code);
            } catch (SystemException e) {
                return error(e.getMessage());
            }
            if(oAuthDTO == null){
                return error("openid获取失败");
            }
            WXUserInfo result = wxUserInfoService.findWXUserInfo(oAuthDTO.getUnionid());
            //判断该微信号是否已被绑定
            if(result == null){   //没绑定
                //发起http请求获取微信用户基本信息
                WXUserInfo wxUserInfo = null;
                try {
                    wxUserInfo = wxOauthService.getUserInfoByOpenIdAndToken(oAuthDTO.getOpenid(),oAuthDTO.getAccess_token());
                } catch (SystemException e) {
                    return error(e.getMessage());
                }
                user.setUnionid(wxUserInfo.getUnionid());
                user.setWxUserInfo(wxUserInfo);
            }else{ //已绑定
                AppUser condition = new AppUser();
                condition.setUnionid(result.getUnionid());
                AppUser bindUser = appUserService.selectOne(condition);
                if(bindUser == null){
                    return error("未成功解绑");
                }
                String username = bindUser.getUsername();
                return error("该微信已绑定YaYa医师账号 "+ (StringUtils.isEmpty(username) ? bindUser.getMobile() :username));
            }

        }
        // doBindOrUnBindWeiXin方法根据user是否有WXUserInfo判断是解绑还是绑定操作
        appUserService.doBindOrUnBindWeiXin(user);
        WXUserInfo wxUserInfo = user.getWxUserInfo();
        if(wxUserInfo == null){
            //解绑成功
            return success();
        }else{  //绑定成功
            Map<String,String> map = new HashMap<>();
            map.put("wxNickname",wxUserInfo.getNickname());
            return success(map);
        }
    }


    /**
     * 手机只能绑定，更换绑定，不能解绑
     * @param mobile
     * @return
     */
    @RequestMapping("/set_bind_mobile")
    @ResponseBody
    public String changeMobileBindStatus(String mobile,String captcha){
        if (!RegexUtils.checkMobile(mobile)) {
            error("手机格式不正确");
        }

        AppUser result = appUserService.findAppUserByLoginName(mobile);
        if(result != null){
            //使用err=102 代表手机号被绑定
            return APIUtils.error(APIUtils.USER_BIND_CODE ,"该手机号已被绑定");
        }

        Captcha captcha1 = (Captcha)redisCacheUtils.getCacheObject(mobile);
        try {
            if(!jSmsService.verify(captcha1.getMsgId(),captcha)){
                return error("验证码不正确");
            }
        } catch (Exception e) {
            return error("验证码已被校验，请重新获取验证码");
        }
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        AppUser user = appUserService.selectByPrimaryKey(userId);
        user.setMobile(mobile);
        appUserService.updateByPrimaryKeySelective(user);
        return success();
    }

    /**
     * 解除绑定邮箱
     * @return
     */
    @RequestMapping("/unbind_email")
    @ResponseBody
    public String changeEmailBindStatus(){
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        AppUser user = appUserService.selectByPrimaryKey(userId);
        //检查用户有无绑定手机,没有绑定手机不可以解绑邮箱
        String mobile = user.getMobile();
        if(!RegexUtils.checkMobile(mobile)){
            return error("未绑定手机号，不能解绑");
        }
        //解绑
        user.setUsername(null);
        appUserService.updateByPrimaryKey(user);
        return success();
    }






    @RequestMapping(value="/resetPwd")
    @ResponseBody
    public String resetPwd(String oldPwd, String newPwd){
        if(StringUtils.isEmpty(oldPwd)){
            return error(SpringUtils.getMessage("user.oldpassword.notnull"));
        }
        if(StringUtils.isEmpty(newPwd)){
            return error(SpringUtils.getMessage("user.newpassword.notnull"));
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        if(!oldPwd.equals(newPwd)){
            AppUser user = appUserService.selectByPrimaryKey(principal.getId());
            if(!user.getPassword().equals(MD5Utils.MD5Encode(oldPwd))){
                return error("您输入的旧密码不正确");
            }
            user.setPassword(MD5Utils.MD5Encode(newPwd));
            appUserService.updateByPrimaryKeySelective(user);
        }else{
            return error("新密码和旧密码相同");
        }
        return success();
    }


}
