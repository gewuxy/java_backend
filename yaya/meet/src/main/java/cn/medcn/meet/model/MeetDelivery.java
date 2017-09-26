package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 投稿实体类
 * Created by lixuan on 2017/9/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_delivery")
public class MeetDelivery {

    @Id
    protected Integer id;

    protected String sourceId;

    protected String title;

    protected String author;

    protected Integer acceptId;

    protected Integer meetType;

    protected Date acceptTime;

    protected Date startTime;

    protected Date endTime;

    protected String authorAvatar;
}
