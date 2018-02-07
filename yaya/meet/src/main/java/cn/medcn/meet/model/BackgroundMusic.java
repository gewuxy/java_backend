package cn.medcn.meet.model;

import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.CheckUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    @Transient
    protected String timeStr;

    public static void HandelMusicUrl(List<BackgroundMusic> list, String fileBase){
        if(list != null){
            for(BackgroundMusic music : list){
                if (CheckUtils.isNotEmpty(music.getUrl())) {
                    music.setUrl(fileBase + music.getUrl());
                }
            }
        }
    }

    public static void tranTimeStr(List<BackgroundMusic> list){
        if(list != null){
            for(BackgroundMusic music : list){
                if(music.getDuration() != null){
                    music.setTimeStr(CalendarUtils.secToTime(music.getDuration()));
                }
            }
        }
    }


}
