package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_member")
public class MeetMember implements Serializable{
    @Id
    private Integer id;

    private String meetId;

    private Integer attenderId;

    private String attenderName;
}
