package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_active_code")
public class ActiveCode implements Serializable{
    @Id
    private Integer id;
    /**激活码拥有者*/
    private Integer onwerid;
    /**激活码*/
    private String code;
    /**是否已经使用*/
    private Boolean used;
    /**使用者ID*/
    private Integer usedId;
    /**发送给哪个使用者 真实姓名*/
    private String toName;
    /**是否激活的*/
    private Boolean actived;
    /**激活码生成时间*/
    private Date sendTime;
    /**激活码使用时间*/
    private Date activeTime;

    // 激活码所属单位号名称
    @Transient
    private String nickname;
}
