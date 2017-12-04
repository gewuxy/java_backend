package cn.medcn.user.dto;

import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by huanghuibin on 2017/11/30
 */
@NoArgsConstructor
@Data
public class PatientDTO {
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

    // 用户绑定的第三方平台
    protected List<BindInfo> bindInfoList;

    public static PatientDTO buildToPatientUserInfoDTO(Patient userInfo) {
        PatientDTO dto = new PatientDTO();
        dto.setUid(userInfo.getId());
        dto.setNickName(userInfo.getNickName());
        dto.setProvince(userInfo.getProvince());
        dto.setCity(userInfo.getCity());
        dto.setDistrict(userInfo.getDistrict());
        dto.setAvatar(userInfo.getAvatar());
        dto.setToken(userInfo.getToken());
        dto.setEmail(userInfo.getEmail());
        dto.setMobile(userInfo.getMobile());
        dto.setUserName(userInfo.getUserName());
        return dto;
    }
}
