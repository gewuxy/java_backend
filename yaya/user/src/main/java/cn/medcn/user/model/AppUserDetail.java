package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
public class AppUserDetail implements Serializable{

    @Id
    private Integer id;
    /**医院名称*/
    protected String unitName;
    /**科室名称*/
    protected String subUnitName;
    /**用户ID*/
    protected Integer userId;

}
