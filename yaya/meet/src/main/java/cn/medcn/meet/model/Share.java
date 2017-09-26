package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/9/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_share")
public class Share {
    protected String id;
    //  课程id
    protected Integer courseId;
    //  用户id
    protected String userId;
    //  分享时间
    protected Date shareTime;
    //  分享到的第三方平台
    protected String platform;

}
