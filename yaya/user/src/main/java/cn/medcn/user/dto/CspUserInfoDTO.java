package cn.medcn.user.dto;

import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    //是否激活
    protected String active;
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
    //注册设备
    protected Integer registerDevice;
    //套餐Id
    protected Integer packageId;
    //注册时间
    protected Date registerTime;
    //套餐开始时间
    protected Date packageStart;
    //套餐结束结束
    protected Date packageEnd;
    //冻结时间
    protected Date updateTime;
    //备注
    protected String remark;
    // 冻结
    protected Boolean frozenState;
    //冻结原因
    protected String frozenReason;
    //付费次数
    protected Integer payTimes;
    //付费人民币
    protected String payMoneyCn;
    //付费美元
    protected String payMoneyUs;
    //会议数量
    protected int meets;
    //会议限制
    protected Boolean unlimited;


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
        dto.setFrozenState(userInfo.getFrozenState());

        return dto;
    }

}
