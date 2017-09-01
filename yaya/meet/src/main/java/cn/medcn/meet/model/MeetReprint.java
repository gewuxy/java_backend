package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/5/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_reprint")
public class MeetReprint implements Serializable{

    @Id
    private Integer id;

    // 转载用户Id
    private Integer userId;

    // 转载时间
    private Date reprintTime;

    // 会议Id
    private String meetId;

    // 会议所属者Id
    private Integer ownerId;

}
