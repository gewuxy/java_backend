package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/6/7.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_jpush_message_history")
public class JpushMessageHistory implements Serializable {

    @Id
    private Long id;
    /**用户ID*/
    private Integer userId;
    /**消息ID*/
    private Long messageId;
    /**查看时间*/
    private Date viewTime;


}
