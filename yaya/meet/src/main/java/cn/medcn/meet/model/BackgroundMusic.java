package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Liuchangling on 2018/1/19.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_background_music")
public class BackgroundMusic {
    @Id
    protected Integer id;
    // 音乐名称
    protected String name;
    // 音乐时长
    protected Integer duration;
    // 音乐大小
    protected Integer size;
    // 音乐地址
    protected String url;


}
