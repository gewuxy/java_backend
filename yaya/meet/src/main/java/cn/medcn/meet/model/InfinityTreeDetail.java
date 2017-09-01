package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/7/27.
 */
@Data
@NoArgsConstructor
@Entity
@Table (name = "t_infinity_tree_detail")
public class InfinityTreeDetail implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    // 文件夹ID
    private String infinityId;
    // 资源ID
    private String resourceId;
    // 资源名称
    private String resourceName;

}
