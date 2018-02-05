package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Liuchangling on 2018/1/19.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_background_music")
public class BackgroundMusic {
    @Id
    protected Integer id;
    // 音乐名称
    protected String name;
    // 音乐时长
    protected Integer duration;
    // 音乐大小
    protected Float size;
    // 音乐地址
    protected String url;
    // 推荐列表排序
    protected Integer recomSort;
    // 更多列表排序
    protected Integer sort;
    // 是否隐藏
    protected Boolean hide;

    public static void HandelMusicUrl(List<BackgroundMusic> list, String fileBase){
        if(list != null){
            for(BackgroundMusic music : list){
                music.setUrl(fileBase + music.getUrl());
            }
        }
    }


}
