package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/2.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_log")
public class AppLog {
    private Long id;
    /**所属APPID 1是YaYa医师 2是合理用药 3是csp*/
    private Integer appId;
    /**用户操作*/
    private String action;
    /**记录用户ID*/
    private Integer userId;
    /**记录时间*/
    private Date logTime;
    /**执行时间*/
    private Integer useTime;
    /**请求的IP地址*/
    private String logIp;
    /**app版本号*/
    private String appVersion;
    /**用户系统类型*/
    private String osType;
    /**用户手机系统版本*/
    private String osVersion;
}
