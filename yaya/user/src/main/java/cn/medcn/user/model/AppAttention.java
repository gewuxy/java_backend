package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/21.
 * 关注实体类
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_attention")
public class AppAttention implements Serializable{

    @Id
    private Integer id;
    /**关注时间*/
    private Date attentionTime;
    /**关注者ID*/
    private Integer slaverId;
    /**被关注者ID*/
    private Integer  masterId;

    public AppAttention(Integer slaverId, Integer masterId){
        this.slaverId = slaverId;
        this.masterId = masterId;
    }
}
