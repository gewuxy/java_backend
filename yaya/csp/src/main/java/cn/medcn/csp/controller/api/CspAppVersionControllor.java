package cn.medcn.csp.controller.api;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static cn.medcn.common.Constants.ABROAD_KEY;

/**
 * CSP版本更新接口控制器
 * Created by lixuan on 2017/12/25.
 */
@Controller
@RequestMapping(value = "/api/version/")
public class CspAppVersionControllor extends CspUserController {

    @Autowired
    protected AppVersionService appVersionService;

    @Value("${app.file.base}")
    protected String appFileBase;

    @RequestMapping(value = "/newly")
    public String newly(HttpServletRequest request) {
        //从header中获取版本以及系统信息
        String versionStr = request.getHeader(Constants.APP_VERSION_KEY);
        String osType = request.getHeader(Constants.APP_OS_TYPE_KEY);
        Integer abroad = Integer.valueOf(request.getHeader(ABROAD_KEY));

        if (abroad == null) {
            abroad = 0;
        }

        String appType = abroad == CspUserInfo.AbroadType.home.ordinal() ?
                AppVersion.APP_TYPE.CSPMeeting_CN.type :
                AppVersion.APP_TYPE.CSPMeeting_US.type;

        if (CheckUtils.isEmpty(versionStr)) {
            return success();
        }

        if (osType.equalsIgnoreCase(AppVersion.DRIVE_TAG.ANDROID.type)) {
            osType = AppVersion.DRIVE_TAG.ANDROID.type;
        } else {
            osType = AppVersion.DRIVE_TAG.IOS.type;
        }
        //查询出最新的App版本信息并返回
        AppVersion appVersion = appVersionService.findNewly(appType, osType);
        if (appVersion != null && appVersion.getVersion().intValue() > Integer.valueOf(versionStr)) {
            if (!CheckUtils.isEmpty(appVersion.getDownLoadUrl())) {
                if (!appVersion.getDownLoadUrl().toLowerCase().startsWith("http")) {
                    appVersion.setDownLoadUrl(appFileBase + appVersion.getDownLoadUrl());
                }
            }
            return success(appVersion);
        }
        //未找到的情况下返回空的成功信息
        return success();
    }
}
