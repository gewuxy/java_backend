package cn.medcn.transfer.model.writeable;

/**
 * Created by lixuan on 2017/6/16.
 */
public class VideoCourseDetail {
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPreId() {
        return preId;
    }

    public void setPreId(Integer preId) {
        this.preId = preId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
