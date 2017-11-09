package cn.medcn.csp.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.user.dto.Captcha;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
        buffer.append(request.getScheme().toLowerCase().equals("https") ? "wss" : "ws");
        buffer.append("://").append(request.getServerName()).append(":").append(request.getServerPort());
        buffer.append("/live/order?courseId=").append(courseId);
        String token = request.getHeader(Constants.TOKEN);
        if (CheckUtils.isNotEmpty(token)) {
            buffer.append("&token=").append(request.getHeader(Constants.TOKEN));
        }
        return buffer.toString();
    }


    protected void handleHttpUrl(String fileBase, AudioCourse course){
        if (course != null && !CheckUtils.isEmpty(course.getDetails())) {
            for (AudioCourseDetail detail : course.getDetails()) {
                if (CheckUtils.isNotEmpty(detail.getAudioUrl()) && !detail.getAudioUrl().startsWith("http")) {
                    detail.setAudioUrl(fileBase + detail.getAudioUrl());
                }
                if (CheckUtils.isNotEmpty(detail.getImgUrl()) && !detail.getImgUrl().startsWith("http")) {
                    detail.setImgUrl(fileBase + detail.getImgUrl());
                }
                if (CheckUtils.isNotEmpty(detail.getVideoUrl()) && !detail.getVideoUrl().startsWith("http")) {
                    detail.setVideoUrl(fileBase + detail.getVideoUrl());
                }
            }
        }
    }


    /**
     * 发送手机号 获取极光短信id
     *
     * @param mobile  手机号
     * @param msgTmpId  短信模板id
     * @return
     * @throws SystemException
     */
    protected String sendMobileCaptcha(String mobile, Integer msgTmpId) throws SystemException {
        try {
            //10分钟内最多允许获取3次验证码
            Captcha captcha = (Captcha) redisCacheUtils.getCacheObject(Constants.CSP_MOBILE_CACHE_PREFIX_KEY + mobile);
            if (captcha == null) { //第一次获取
                // 发送短信
                String msgId = cspSmsService.send(mobile, msgTmpId);
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
                String msgId = cspSmsService.send(mobile, msgTmpId);

                captcha.setMsgId(msgId);
                captcha.setCount(captcha.getCount() + 1);
                redisCacheUtils.setCacheObject(CSP_MOBILE_CACHE_PREFIX_KEY + mobile, captcha, CAPTCHA_CACHE_EXPIRE_TIME);
            }

            return APIUtils.success();

        } catch (Exception e) {
            throw new SystemException(local("sms.error.send"));
        }

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
}
