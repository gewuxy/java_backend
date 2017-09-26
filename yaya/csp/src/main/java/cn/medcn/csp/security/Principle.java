package cn.medcn.csp.security;

import cn.medcn.user.model.CspUserInfo;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/12.
 */
public class Principle implements Serializable{

    protected String id;

    protected String nickName;

    protected String mobile;

    protected String email;

    protected String token;

    protected String avatar;

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

    public static Principle build(CspUserInfo userInfo){
        if (userInfo != null) {
            Principle principle = new Principle();
            //principle.setId(userInfo.getId());
            principle.setMobile(userInfo.getMobile());
            principle.setEmail(userInfo.getEmail());
            principle.setNickName(userInfo.getNickName());
            principle.setToken(userInfo.getToken());

            principle.setAvatar(userInfo.getAvatar());

            return principle;
        }

        return null;
    }

}
