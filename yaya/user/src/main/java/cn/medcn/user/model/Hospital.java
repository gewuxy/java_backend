package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lixuan on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_hospital")
public class Hospital {

    @Id
    private Integer id;
    /**医院名称*/
    private String name;
    /**详细地址*/
    private String address;
    /**级别*/
    private String level;
    /**省份*/
    private String province;
    /**城市*/
    private String city;
    /**联系电话*/
    private String tel;
    /**街道*/
    private String street;
}
