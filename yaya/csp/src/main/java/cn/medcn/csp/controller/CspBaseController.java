package cn.medcn.csp.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.user.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.service.CspPackageOrderService;
import cn.medcn.user.service.CspPackageService;
import cn.medcn.user.service.CspUserPackageService;
import cn.medcn.user.service.CspUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.sql.Driver;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.*;

/**
 * Created by lixuan on 2017/10/17.
 */
public class CspBaseController extends BaseController {

    @Autowired
    protected SMSService cspSmsService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected CspPackageService cspPackageService;

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    @Autowired
    protected CspPackageOrderService cspPackageOrderService;

    @Value("${web.socket.url}")
    protected String webSocketUrl;

    @Value("${app.is.pro}")
    protected Integer appPro;

    @Autowired
    protected AudioService audioService;

    /**
     * 获取web端用户认证信息
     *
     * @return
     */
    protected Principal getWebPrincipal() {
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        if (principal != null){
            String token = principal.getToken();
            //从缓存信息里查询最新版
            Principal principalCache = getPackageCache(token);
            if (principalCache == null) {
                //如果缓存过期更新缓存
                return updatePackagePrincipal(principal.getId());
            }
            return principalCache;
        }else {
            return principal;
        }

    }

    /**
     * 根据请求生成ws地址
     * @param request
     * @return
     */
    protected String genWsUrl(HttpServletRequest request, Integer courseId){
        StringBuffer buffer = new StringBuffer();
        buffer.append(webSocketUrl);
        buffer.append("?courseId=").append(courseId);
        String token = request.getHeader(Constants.TOKEN);
        if (CheckUtils.isNotEmpty(token)) {
            buffer.append("&token=").append(request.getHeader(Constants.TOKEN));
        }

        return buffer.toString();
    }


//    protected void handleHttpUrl(String fileBase, AudioCourse course){
//        if (course != null && !CheckUtils.isEmpty(course.getDetails())) {
//            for (AudioCourseDetail detail : course.getDetails()) {
//                if (CheckUtils.isNotEmpty(detail.getAudioUrl()) && !detail.getAudioUrl().startsWith("http")) {
//                    detail.setAudioUrl(fileBase + detail.getAudioUrl());
//                }
//                if (CheckUtils.isNotEmpty(detail.getImgUrl()) && !detail.getImgUrl().startsWith("http")) {
//                    detail.setImgUrl(fileBase + detail.getImgUrl());
//                }
//                if (CheckUtils.isNotEmpty(detail.getVideoUrl()) && !detail.getVideoUrl().startsWith("http")) {
//                    detail.setVideoUrl(fileBase + detail.getVideoUrl());
//                }
//            }
//        }
//    }


    /**
     * 发送手机号 获取极光短信id
     *
     * @param mobile  手机号
     * @param msgTmpId  短信模板id
     * @return
     * @throws SystemException
     */
    protected String sendMobileCaptcha(String mobile, Integer msgTmpId) throws SystemException {
            //10分钟内最多允许获取3次验证码
            Captcha captcha = (Captcha) redisCacheUtils.getCacheObject(Constants.CSP_MOBILE_CACHE_PREFIX_KEY + mobile);
            if (captcha == null) { //第一次获取
                // 发送短信
                String msgId = null;
                try {
                    msgId = cspSmsService.send(mobile, msgTmpId);
                } catch (Exception e) {
                    throw new SystemException(local("sms.error.send"));
                }
                Captcha firstCaptcha = new Captcha();
                firstCaptcha.setFirstTime(new Date());
                firstCaptcha.setCount(NUMBER_ZERO);
                firstCaptcha.setMsgId(msgId);
                redisCacheUtils.setCacheObject(CSP_MOBILE_CACHE_PREFIX_KEY + mobile, firstCaptcha, CAPTCHA_CACHE_EXPIRE_TIME); //15分钟有效期

            } else {
                Long between = System.currentTimeMillis() - captcha.getFirstTime().getTime();
                if (captcha.getCount() == NUMBER_TWO && between < TimeUnit.MINUTES.toMillis(NUMBER_TEN)) {
                    throw new SystemException(local("sms.frequency.send"));
                }
                // 发送短信
                String msgId = null;
                try {
                    msgId = cspSmsService.send(mobile, msgTmpId);
                } catch (Exception e) {
                    throw new SystemException(local("sms.error.send"));
                }

                captcha.setMsgId(msgId);
                captcha.setCount(captcha.getCount() + 1);
                redisCacheUtils.setCacheObject(CSP_MOBILE_CACHE_PREFIX_KEY + mobile, captcha, CAPTCHA_CACHE_EXPIRE_TIME);
            }

            return APIUtils.success();



    }


    /**
     * 检查验证码合法性
     *
     * @param mobile
     * @param captcha
     * @return
     * @throws SystemException
     */
    protected void checkCaptchaIsOrNotValid(String mobile, String captcha) throws SystemException {
        // 从缓存获取此号码的短信记录
        Captcha result = (Captcha) redisCacheUtils.getCacheObject(Constants.CSP_MOBILE_CACHE_PREFIX_KEY + mobile);
        try {

            Boolean bool = cspSmsService.verify(result.getMsgId(),captcha);
            if (!bool) {
                throw new SystemException(local("sms.error.captcha"));
            }

        } catch (Exception e) {
            throw new SystemException(local("sms.invalid.captcha"));
        }
    }

    //是否需要添加头像路径前缀
    protected Boolean needAvatarPrefix(String path){
        if(!StringUtils.isEmpty(path) &&  path.startsWith(Constants.AVATAR_URL_PREFIX) == false){
            return true;
        }
        return false;
    }

    /**
     * 课件不可编辑异常
     * @return
     */
    protected String courseNonEditAbleError(){
        return local("course.error.editable");
    }


    protected String courseNonDeleteAble(){
        return local("course.error.delete");
    }





    protected Principal getCommonPrincipal(CspUserInfo.RegisterDevice drive){
        Principal principal = null;
        switch (drive) {
            case APP:
                principal = cn.medcn.csp.security.SecurityUtils.get();
                break;
            case WEB:
                principal = getWebPrincipal();
                break;
        }
        return principal;
    }

    /**
     * 检测用户会议是否已经达到限制
     * @param drive
     * @return
     */
    protected boolean meetCountNotEnough(CspUserInfo.RegisterDevice drive){
        Principal principal = getCommonPrincipal(drive);
        if(principal != null){
            //判断是否已经超出限制
            CspPackage cspPackage = principal.getCspPackage();
            if(cspPackage == null) return false;
            int maxUsableCount = cspPackage.getLimitMeets();
            int usedCount = principal.getCspPackage().getUsedMeetCount();

            return maxUsableCount > 0 && usedCount >= maxUsableCount;
        } else {
            return false;
        }

    }

    /**
     * 检测用户会议是否已经超出限制
     * @param drive
     * @return
     */
    protected boolean meetCountOut(CspUserInfo.RegisterDevice drive){
        Principal principal = getCommonPrincipal(drive);
        if(principal != null){
            //判断是否已经超出限制
            CspPackage cspPackage = principal.getCspPackage();
            if(cspPackage == null) return false;
            int maxUsableCount = cspPackage.getLimitMeets();
            int usedCount = principal.getCspPackage().getUsedMeetCount();

            return maxUsableCount > 0 && usedCount > maxUsableCount;
        } else {
            return false;
        }

    }

    /**
     * 缓存更新用户套餐信息(用户套餐更改，会议数量更改，套餐基础信息变更 使用)
     *
     * @param userId
     * @return
     */
    protected Principal updatePackagePrincipal(String userId) {
        // 获取最新的用户信息
        CspUserInfo userInfo = cspUserService.selectByPrimaryKey(userId);
        // 用户token
        String token = userInfo.getToken();
        // 将用户最新信息设置到缓存
        Principal principal = Principal.build(userInfo);
        // 获取用户最新套餐信息
        CspPackage cspPackage = cspPackageService.findUserPackageById(userId);
        principal.setPackageId(cspPackage == null ? null : cspPackage.getId());
        principal.setCspPackage(cspPackage);
        principal.setNewUser(cspPackage == null);

        redisCacheUtils.setCacheObject(Constants.TOKEN +"_" + token, principal, Constants.TOKEN_EXPIRE_TIME);
        return principal;
    }

    /**
     * 通过付费的套餐变更时添加提示信息
     *
     * @param packageId
     * @param type  添加信息类型
     */
    protected void updatePackageMsg(String userId, Integer packageId, Integer type) {
        CspUserInfo userInfo = cspUserService.selectByPrimaryKey(userId);
        Principal setPrincipal = getPackageCache(userInfo.getToken());
        if (type == Constants.NUMBER_ZERO) { // 删除提示信息
            setPrincipal.setPkChangeMsg(null);
        } else if (type == Constants.NUMBER_ONE) {   // 续费成功
            setPrincipal.setPkChangeMsg(local("package.keep.success"));
        } else if (type == Constants.NUMBER_TWO) {  // 选择标准版
            setPrincipal.setPkChangeMsg(local("package.standran.success.msg"));
        } else {                            // 购买高级或者专业版
            setPrincipal.setPkChangeMsg(local("package.buy.pf.success", new Object[]{cspPackageOrderService.getLocalPackage(packageId)}));
        }
        redisCacheUtils.setCacheObject(Constants.TOKEN + "_" + setPrincipal.getToken(), setPrincipal, Constants.TOKEN_EXPIRE_TIME);
    }

    /**
     * 根据token获取缓存
     *
     * @param token
     * @return
     */
    protected Principal getPackageCache(String token) {
        return redisCacheUtils.getCacheObject(Constants.TOKEN + "_" + token);
    }


    protected String defaultRedirectUrl(){
        return "redirect:/mgr/meet/list";
    }

    protected String accountFrozenError(){
        
        return error(APIUtils.ERROR_CODE_FROZEN, local("user.frozen.account"));
    }

    /**
     * 判断是否是老用户
     * @param cspUserInfo
     */
    protected void modifyOldUser(CspUserInfo cspUserInfo){
        if (cspUserInfo != null && cspUserInfo.getState() == true){
            doOldUserSendProfessionalEdition(cspUserInfo);
            cspUserInfo.setState(false);
            cspUserService.updateByPrimaryKey(cspUserInfo);
        }
        updatePackagePrincipal(cspUserInfo.getId());
    }

    /**
     * 老用户赠送三个月专业版
     */
    private void doOldUserSendProfessionalEdition(CspUserInfo cspUserInfo){
        String userId = cspUserInfo.getId();
        //根据id查出版本信息
        if ( cspUserInfo != null && cspUserInfo.getState() == true ){
                cspUserPackageService.modifySendOldUser(userId);
            }
        }

}
