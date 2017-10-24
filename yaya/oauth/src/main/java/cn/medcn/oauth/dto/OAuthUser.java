package cn.medcn.oauth.dto;

import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.user.dto.CspUserInfoDTO;

/**
 * Created by lixuan on 2017/9/15.
 */
public class OAuthUser {

    protected String uid;

    protected String nickname;

    protected String gender;

    protected String province;

    protected String city;

    protected String country;

    protected int platformId;

    protected String iconUrl;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public static CspUserInfoDTO buildToCspUserInfoDTO(OAuthUser authUser) {
        CspUserInfoDTO dto = new CspUserInfoDTO();
        dto.setUniqueId(authUser.getUid());
        dto.setNickName(authUser.getNickname());
        dto.setToken(UUIDUtil.getUUID());
        dto.setCountry(authUser.getCountry());
        dto.setProvince(authUser.getProvince());
        dto.setCity(authUser.getCity());
        dto.setDistrict("");
        dto.setAvatar(authUser.getIconUrl());
        dto.setInfo("");
        dto.setEmail("");
        dto.setMobile("");
        dto.setUserName("");
        dto.setFlux(0);
        return dto;
    }
}
