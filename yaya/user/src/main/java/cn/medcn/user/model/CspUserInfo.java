package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/** csp用户信息
 * Created by LiuLP on 2017/4/24.
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




}
