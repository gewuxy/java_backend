package cn.medcn.user.model;

/**
 * Created by lixuan on 2017/5/2.
 */
public enum AppInfo {
    YAYA(0, "YAYA医师"),
    HLYY(1, "合理用药");

    private Integer appId;

    private String appName;

    public Integer getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }

    AppInfo(Integer appId, String appName){
        this.appId = appId;
        this.appName = appName;
    }
}
