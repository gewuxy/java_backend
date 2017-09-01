package cn.medcn.jcms.controller.front;

import cn.medcn.common.ctrl.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Liuchangling on 2017/8/29.
 * 扫描二维码下载APP
 */

public class AppDownloadController extends BaseController{

    public static Boolean isIos(HttpServletRequest request) {
        boolean isIos = false;
        final String[] ios_sys = {"iPhone", "iPad", "iPod"};
        String userAgent = request.getHeader("user-agent");
        for (int i = 0; !isIos && userAgent != null && !userAgent.trim().equals("") && i < ios_sys.length; i++) {
            if (userAgent.contains(ios_sys[i])) {
                isIos = true;
                break;
            }
        }
        return isIos;
    }

    public static Boolean isAndroid(HttpServletRequest request) {
        boolean isAndroid = false;
        return isAndroid;

    }
}
