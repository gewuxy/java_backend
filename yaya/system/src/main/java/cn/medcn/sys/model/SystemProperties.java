package cn.medcn.sys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**统一配置文件
 * Created by LiuLP on 2017/8/8/008.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_properties")
public class SystemProperties {
    @Id
    private Integer id;

    private String propKey;

    private String propValue;

    private String propDesc;

    //配置的类型
    private String propType;

    private Integer version;

    private String picture;
}
