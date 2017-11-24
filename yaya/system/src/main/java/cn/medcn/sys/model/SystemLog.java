package cn.medcn.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * by create HuangHuibin 2017/11/23
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_sys_log")
public class SystemLog {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Integer userId;
    private String realName;
    private String action;
    private String actionName;
    private Date logDate;
    private String userName;
}
