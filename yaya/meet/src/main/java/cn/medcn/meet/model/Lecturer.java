package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/16.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_meet_lecturer")
public class Lecturer implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String headimg;

    private String title;

    private String hospital;

    private String depart;

    private String meetId;
}
