package cn.medcn.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/2.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_sys_user")
public class SystemUser implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String username;

    private String password;

    private String realname;

    private String mobile;

    private String email;

    private String qq;

    private String headimg;

    private String openid;

    private Date lastLoginDate;

    private String lastLoginIp;

    private Boolean active;

    private String sign;

    private Integer roleId;

    @Transient
    private SystemRole role;

    @Transient
    private Boolean isAdmin;


    public Boolean getIsAdmin(){
        return 1==this.id;
    }

}
