package cn.medcn.meet.model;

import cn.medcn.common.utils.FileUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_video_course_detail")
public class VideoCourseDetail implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**父ID*/
    private Integer preId;
    /**文件夹或者视频名称*/
    private String name;
    /**0表示文件夹 1表示视频*/
    private Integer type;
    /**所属course的ID*/
    private Integer courseId;
    /**视频路径 type为视频的时候才有效*/
    private String url;
    /**视频类型 0表示内部链接 1表示外部链接*/
    private Integer videoType;
    /**视频时长*/
    private Integer duration;
    /**内部链接文件大小*/
    protected Long fileSize;

    @Transient
    private Integer userdtime;

    @Transient
    protected String fileSizeStr;

    public String getFileSizeStr(){
        return fileSize == null ? null : FileUtils.mSize(fileSize);
    }

    /***
     * 视频明细类型
     */
    public enum VideoDetailType{
        FOLDER("文件夹"),
        VIDEO("视频");

        private String type;

        public String getType() {
            return type;
        }

        VideoDetailType(String type){
            this.type = type;
        }
    }

    /**
     * 视频类型
     */
    public enum VideoType{
        INNER_LINK("内部链接"),//保存在公司服务器上面的链接
        OUTER_LINK("外部链接");//保存在客户服务器上面的链接

        private String linkType;

        public String getLinkType() {
            return linkType;
        }

        VideoType(String lineType){
            this.linkType = lineType;
        }
    }
}
