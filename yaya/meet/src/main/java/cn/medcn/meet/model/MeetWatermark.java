package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 会议水印
 * Created by LiuLP on 2017/4/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_watermark")
public class MeetWatermark implements Serializable {

    @Id
    private Integer id;
    /**水印名称*/
    private String name;
    /**水印方向*/
    private Integer direction;
    /**课件id*/
    private Integer courseId;

    public enum Direction{
        LEFT_TOP,
        LEFT_BELOW,
        RIGHT_TOP,
        RIGHT_BELOW;
    }

}
