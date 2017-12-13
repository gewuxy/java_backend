package cn.medcn.csp.security;

import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/12.
 */
public class Principal implements Serializable{

    protected String id;

    protected String nickName;

    protected String mobile;

    protected String email;

    protected String token;

    protected String avatar;

    protected Boolean abroad;

    //套餐id
    protected Integer packageId;

    protected CspPackage cspPackage;

    // 过期提醒
    protected String expireRemind;

    // 过期时间 天数
    protected Integer expireDays;

    public Boolean getAbroad() {
        return abroad;
    }

    public void setAbroad(Boolean abroad) {
        this.abroad = abroad;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPackageId(Integer packageId){this.packageId = packageId;}

    public Integer getPackageId(){return packageId;}

    public void setExpireRemind(String expireRemind) {
        this.expireRemind = expireRemind;
    }

    public String getExpireRemind() {
        return expireRemind;
    }

    public CspPackage getCspPackage() {
        return cspPackage;
    }

    public void setCspPackage(CspPackage cspPackage) {
        this.cspPackage = cspPackage;
    }

    public Integer getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(Integer expireDays) {
        this.expireDays = expireDays;
    }

    public static Principal build(CspUserInfo userInfo){
        if (userInfo != null) {
            Principal principle = new Principal();
            principle.setId(userInfo.getId());
            principle.setMobile(userInfo.getMobile());
            principle.setEmail(userInfo.getEmail());
            principle.setNickName(userInfo.getNickName());
            principle.setToken(userInfo.getToken());
            principle.setAbroad(userInfo.getAbroad() == null ? false : userInfo.getAbroad());
            principle.setAvatar(userInfo.getAvatar());

            return principle;
        }

        return null;
    }

}
