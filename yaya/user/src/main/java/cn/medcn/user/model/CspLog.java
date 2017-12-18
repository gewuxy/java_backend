package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/12/15.
 */
@Entity
@Table(name = "t_csp_log")
@Data
@NoArgsConstructor
public class CspLog implements Serializable {
    private Long id;
    /**用户操作*/
    private String action;
    /**记录用户ID*/
    private String userId;
    /**记录时间*/
    private Date logTime;
    /**执行时间*/
    private Integer useTime;
    /**app版本号*/
    private String appVersion;
    /**用户系统类型*/
    private String osType;
    /**用户手机系统版本*/
    private String osVersion;
}
