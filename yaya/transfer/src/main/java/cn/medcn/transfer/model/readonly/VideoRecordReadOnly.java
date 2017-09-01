package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/16.
 */
public class VideoRecordReadOnly {

    private Long r_id;
    private Long videoId;
    private Long userId;
    private Integer playTime;
    private String playPercent;

    public Long getR_id() {
        return r_id;
    }

    public void setR_id(Long r_id) {
        this.r_id = r_id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public String getPlayPercent() {
        return playPercent;
    }

    public void setPlayPercent(String playPercent) {
        this.playPercent = playPercent;
    }
}
