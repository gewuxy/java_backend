package cn.medcn.user.dto;

import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Liuchangling on 2017/9/22.
 */
@NoArgsConstructor
@Data
public class CspUserInfoDTO  {
    // 用户id
    protected String uid;
    // 姓名
    protected String userName;
    // 邮箱
    protected String email;
    // 手机
    protected String mobile;
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

    // 国内=0、海外=1
    protected Boolean abroad;

    // 第三方平台id
    protected Integer thirdPartyId;
    // 个人简介
    protected String info;
    // token
    protected String token;
    // 用户流量值
    protected Integer flux;
    //是否老用户
    protected Boolean state;
    // 用户绑定的第三方平台
    protected List<BindInfo> bindInfoList;

    //上传的ppt数量
    protected int pptCount;
    //分享的ppt数量
    protected int shareCount;

    // 用户套餐信息
    protected CspPackage cspPackage;



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
        dto.setEmail(userInfo.getEmail());
        dto.setMobile(userInfo.getMobile());
        dto.setUserName(userInfo.getUserName());
        if (userInfo.getFlux() == null) {
            dto.setFlux(0);
        } else {
            dto.setFlux(userInfo.getFlux());
        }

        return dto;
    }

}
