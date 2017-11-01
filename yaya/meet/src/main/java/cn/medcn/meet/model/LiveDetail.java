package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/10/31.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_live_detail")
public class LiveDetail implements Serializable {

    @Id
    protected Integer id;

    protected Integer courseId;

    protected Integer sort;

    protected Integer duration;

    protected String imgUrl;

    protected String audioUrl;

    protected String videoUrl;
}
