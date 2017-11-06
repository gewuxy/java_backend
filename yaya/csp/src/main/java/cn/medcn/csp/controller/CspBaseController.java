package cn.medcn.csp.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lixuan on 2017/10/17.
 */
public class CspBaseController extends BaseController {

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
}
