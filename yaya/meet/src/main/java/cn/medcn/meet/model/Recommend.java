package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recDate;
    // 排序 序号
    private Integer sort;

    //是否固定推荐
    private Integer fixed;

    @Transient
    private String meetName;

    @Transient
    /**会议状态0表示草稿 1表示未开始 2表示进行中 3表示已结束 4表示已撤销/已删除 5表示未发布（从草稿复制过来） 6表示已关闭*/
    private Short state;

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
