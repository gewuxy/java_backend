package cn.medcn.user.dto;

import cn.medcn.user.model.CspUserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/9/22.
 */
@NoArgsConstructor
@Data
public class CspUserInfoDTO  {
    // 用户id
    protected String uid;
    // 昵称
    protected String nickName;
    // 性别
    protected String gender;
    // 国家
    protected String country;
    // 省份
    protected String province;
    // 城市
    protected String city;
    // 地区
    protected String district;
    // 第三方平台唯一id
    protected String uniqueId;
    // 头像
    protected String avatar;

    // 第三方平台id
    protected Integer thirdPartyId;
    // 个人简介
    protected String info;
    // token
    protected String token;

    public static CspUserInfoDTO buildToCspUserInfoDTO(CspUserInfo userInfo) {
        CspUserInfoDTO dto = new CspUserInfoDTO();
        dto.setUid(userInfo.getId());
        dto.setNickName(userInfo.getNickName());
        dto.setCountry(userInfo.getCountry());
        dto.setProvince(userInfo.getProvince());
        dto.setCity(userInfo.getCity());
        dto.setDistrict(userInfo.getDistrict());
        dto.setAvatar(userInfo.getAvatar());
        dto.setInfo(userInfo.getInfo());
        dto.setToken(userInfo.getToken());
        return dto;
    }


}
