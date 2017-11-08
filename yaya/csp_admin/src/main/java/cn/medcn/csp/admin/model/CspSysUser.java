package cn.medcn.csp.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * by create HuangHuibin 2017/11/3
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_sys_user")
public class CspSysUser {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String account;
    private String userName;
    private String password;
    private String mobile;
    private String email;
    private Boolean active;
    private Integer roleId;
    private Date lastLoginDate;
    private String lastLoginIp;

}
