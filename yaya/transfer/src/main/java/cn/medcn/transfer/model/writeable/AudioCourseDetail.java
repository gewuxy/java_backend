package cn.medcn.transfer.model.writeable;

/**
 * Created by lixuan on 2017/6/16.
 */
public class AudioCourseDetail {

    //MP3音频格式
    public static final String DETAIL_TYPE_AUDIO_MP3_KEY = "audio/mpeg";
    //aac音频格式
    public static final String DETAIL_TYPE_AUDIO_AAC_KEY = "audio/x-mei-aac";
    //图片格式
    public static final String DETAIL_TYPE_IMG_KEY = "image/jpeg";
    //视频格式
    public static final String DETAIL_TYPE_VIDEO_KEY = "video/mpeg4";

    public static final String DETAIL_TYPE_TEXT_KEY = "text/plain";

    private Integer id;
    /**排序号*/
    private Integer sort;
    /**ppt图片地址*/
    private String imgUrl;
    /**音频地址*/
    private String audioUrl;
    /**视频地址*/
    private String videoUrl;
    /**开始时间点 作为打点切割备用数据*/
    private Integer startpoint;
    /**结束时间点 作为打点切割备用数据*/
    private Integer endpoint;
    /**音频时长*/
    private Integer duration;
    /**所属课程ID*/
    private Integer courseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(Integer startpoint) {
        this.startpoint = startpoint;
    }

    public Integer getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Integer endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }


}
