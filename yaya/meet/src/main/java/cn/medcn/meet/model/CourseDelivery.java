package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lixuan on 2017/9/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_delivery")
public class CourseDelivery {

    @Id
    protected String id;

    protected String authorId;//作者ID csp用户ID

    protected Integer sourceId;//资源ID 对应AudioCourse的ID

    protected Date deliveryTime;//投稿时间

    protected Integer acceptId;//接受者(单位号ID)

    protected Boolean viewState;//是否查看

    protected Boolean publishState;//是否发布

    protected String info;//简介
}
