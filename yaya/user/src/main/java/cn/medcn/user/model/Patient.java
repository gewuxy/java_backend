package cn.medcn.user.model;

import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.PatientDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 合理用药 患者账号 实体类
 * Created by Liuchangling on 2017/11/23.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_patient")
public class Patient {

    protected String id;
    // 姓名
    protected String userName;
    // 昵称
    protected String nickName;
    // 手机
    protected String mobile;
    // 邮箱
    protected String email;
    // 密码
    protected String password;
    // 省份
    protected String province;
    // 城市
    protected String city;
    // 地区
    protected String district;
    // 性别 0表示未设置 1表示男 2表示女
    protected Integer gender;
    // 出生日期
    protected Date birthday;
    // 过敏史
    protected String allergy;
    // 血型
    protected String bloodType;
    // 头像
    protected String avatar;
    // 使用状态 0：未激活、未使用 1:已激活
    protected Boolean active;
    // token
    protected String token;
    // 用户角色
    protected Integer roleId;
    // 最后登录时间
    protected Date lastLoginTime;
    // 最后登录ip
    protected String lastLoginIp;
    // 注册时间
    protected Date registTime;

    // 前端页面账号
    @Transient
    protected String account;

    // 验证码
    @Transient
    protected String captcha;


    public static Patient buildToUserInfo(PatientDTO dto) {
        Patient userInfo = new Patient();
        userInfo.setId(StringUtils.nowStr());
        userInfo.setNickName(dto.getNickName());
        if(!StringUtils.isEmpty(dto.getAvatar())){
            userInfo.setAvatar(dto.getAvatar());
        }
        userInfo.setProvince(dto.getProvince());
        userInfo.setCity(dto.getCity());
        userInfo.setDistrict(dto.getDistrict());
        userInfo.setRegistTime(new Date());
        userInfo.setActive(true);
        return userInfo;
    }

}
