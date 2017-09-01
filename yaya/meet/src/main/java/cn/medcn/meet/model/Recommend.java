package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/7/26.
 * 推荐资源数据 实体
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_recommend")
public class Recommend implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    // 推荐资源ID
    private String resourceId;
    // 推荐类型 1 会议文件夹  2 会议  3 单位号
    private Integer recType;
    // 是否推荐 0 不推荐  1 推荐
    private Boolean recFlag;
    // 推荐日期
    private Date recDate;
    // 排序 序号
    private Integer sort;

    /* 推荐类型 */
    public enum recommendResource {
        MEET_FOLDER(1, "会议文件夹"),
        MEET(2, "会议"),
        APPUSER(3, "单位号");

        private Integer type;
        private String lable;

        public Integer getType() {
            return type;
        }

        public String getLable() {
            return lable;
        }

        recommendResource(Integer type, String lable) {
            this.type = type;
            this.lable = lable;
        }
    }


}
