package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/16.
 */
public class VideoReadOnly {

    private Long videoId;
    private Long meetingId;//会议ID
    private String videoTitle;//视频标题
    private String videoCatagroy;//视频栏目
    private String viewImgUrl;//试图URL
    private String videoUrl;//视频URL
    private Integer videoTime;//视频时长

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoCatagroy() {
        return videoCatagroy;
    }

    public void setVideoCatagroy(String videoCatagroy) {
        this.videoCatagroy = videoCatagroy;
    }

    public String getViewImgUrl() {
        return viewImgUrl;
    }

    public void setViewImgUrl(String viewImgUrl) {
        this.viewImgUrl = viewImgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Integer videoTime) {
        this.videoTime = videoTime;
    }
}
