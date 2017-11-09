package cn.medcn.csp.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huanghuibin on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_user_info")
public class CspUserInfo implements Serializable{
    @Id
    protected String id;

    protected String nickName;

    protected String userName;

    protected String password;

    protected String avatar;

    protected String country;

    protected String province;

    protected String city;

    protected String district;

    protected String email;

    protected String mobile;

    protected String major;

    protected Date lastLoginTime;

    protected String lastLoginIp;

    protected String token;

    protected Date registerTime;

    protected String info;

    // 国内=0、海外=1
    protected Boolean abroad;
    // 是否有激活 未激活=0 已激活=1
    protected Boolean active;

    @Transient
    protected Integer flux; // 流量

}
