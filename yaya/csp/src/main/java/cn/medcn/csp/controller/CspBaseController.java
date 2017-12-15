package cn.medcn.csp.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.model.CspPackage;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    @Value("${web.socket.url}")
    protected String webSocketUrl;

    @Value("${app.is.pro}")
    protected Integer appPro;




    /**
     * 获取web端用户认证信息
     * @return
     */
    protected Principal getWebPrincipal(){
        return (Principal) SecurityUtils.getSubject().getPrincipal();
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

    /**
     * 判断是否是微信访问
     *
     * @param request
     * @return
     */
    protected boolean isWeChat(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent == null || userAgent.indexOf("micromessenger") == -1) {
            return false ;
        }
        return true;
    }

    /**
     * 判断是否为IOS系统访问
     *
     * @param request
     * @return
     */
    protected boolean isIOSDevice(HttpServletRequest request) {
        boolean isMobile = false;
        final String[] ios_sys = { "iPhone", "iPad", "iPod" };
        String userAgent = request.getHeader("user-agent");
        for (int i = 0; !isMobile && userAgent != null && !userAgent.trim().equals("") && i < ios_sys.length; i++) {
            if (userAgent.contains(ios_sys[i])) {
                isMobile = true;
                break;
            }
        }
        return isMobile;
    }

    /**
     * 检测用户会议是否已经超出限制
     * @return
     */
    protected boolean meetCountOut(){
        Principal principal = getWebPrincipal();
        //判断是否已经超出限制
        CspPackage cspPackage = principal.getCspPackage();
        if(cspPackage == null) return false;
        int maxUsableCount = cspPackage.getLimitMeets();
        int usedCount = principal.getCspPackage().getUsedMeetCount();

        return maxUsableCount > 0 && usedCount >= maxUsableCount;
    }


    protected String defaultRedirectUrl(){
        return "redirect:/mgr/meet/list";
    }

}
