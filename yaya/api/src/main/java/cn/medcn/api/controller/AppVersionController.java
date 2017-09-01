package cn.medcn.api.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lixuan on 2017/6/8.
 */
@Controller
@RequestMapping(value = "/api/version")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @Value("${app.file.base}")
    private String appFileBase;

    @RequestMapping(value = "/newly")
    @ResponseBody
    public String newly(HttpServletRequest request){
        String versionStr = request.getHeader(Constants.APP_VERSION_KEY);
        if(StringUtils.isEmpty(versionStr)){
            return APIUtils.success();
        }
        Integer version = Integer.parseInt(versionStr);
        String drive = AppVersion.DRIVE_TAG.ANDROID.type;
        AppVersion appVersion = appVersionService.findNewly(AppVersion.APP_TYPE.YAYA_YISHI.type, drive);
        if(appVersion != null && appVersion.getVersion() > version){
            if(!StringUtils.isEmpty(appVersion.getDownLoadUrl())){
                appVersion.setDownLoadUrl(appFileBase+appVersion.getDownLoadUrl());
            }
            return APIUtils.success(appVersion);
        }else{
            return APIUtils.success();
        }
    }
}
