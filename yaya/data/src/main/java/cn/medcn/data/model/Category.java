package cn.medcn.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/18.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_data_category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;
    /**栏目名称*/
    private String name;
    /**父节点ID*/
    private String preId;
    /**排序号*/
    private Integer sort;
    /**是否是叶子节点*/
    private Boolean leaf;

    @Transient
    private Category category;
}
