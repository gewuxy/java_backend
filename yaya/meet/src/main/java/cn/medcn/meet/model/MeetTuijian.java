package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_meet_tuijian")
public class MeetTuijian implements Serializable{

    @Id
    private Integer id;

    private String meetId;
    /**主讲者姓名*/
    private String lecturer;
    /**主讲者职位*/
    private String lecturerTitle;
    /**主讲者头像*/
    private String headImg;
}
