package cn.medcn.sys.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/5/4.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_sys_region")
public class SystemRegion implements Serializable {
    /**邮政编码*/
    @Id
    private Integer id;
    /**名称*/
    private String name;
    /**全拼*/
    private String spell;
    /**首字母拼写*/
    private String alpha;
    /**上级单位邮编*/
    private Integer preId;
    /**1表示省或直辖市 2表示市或者地区 3表示区或者县*/
    private Integer level;

    @Transient
    private SystemRegion parent;

    @Transient
    private List<SystemRegion> details = Lists.newArrayList();

    @Transient
    private String regionType;

    public String getRegionType(){
        return RegionType.values()[this.level-1].getType();
    }

    public enum RegionType{
        PROVINCE(1,"省/直辖市"),
        CITY(2,"地区/市"),
        ZONE(3,"区/县");

        private Integer level;

        private String type;

        public Integer getLevel() {
            return level;
        }

        public String getType() {
            return type;
        }

        RegionType(Integer level, String type){
            this.level = level;
            this.type = type;
        }
    }
}
