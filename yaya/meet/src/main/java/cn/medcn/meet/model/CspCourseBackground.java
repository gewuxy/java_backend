package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 课件背景
 * Created by lixuan on 2018/1/18.
 */
@Data
@NoArgsConstructor
public class CspCourseBackground implements Serializable {

    @Id
    protected Integer id;
    //标题
    protected String title;
    /**
     * @see BackgroundType
     */
    protected Integer type;
    //音频时长
    protected Integer duration;
    //访问地址
    protected String  url;
    //文件大小
    protected Long fileSize;
    //是否生效
    protected Boolean active;

    /**
     * 背景类型
     */
    public enum BackgroundType{
        picture,
        audio;
    }
}
