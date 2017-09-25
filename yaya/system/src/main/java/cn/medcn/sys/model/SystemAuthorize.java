package cn.medcn.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lixuan on 2017/9/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_sys_authorize")
public class SystemAuthorize {

    protected Integer id;

    protected String appName;

    protected String appKey;

    protected String appSecret;


}
