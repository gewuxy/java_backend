package cn.medcn.meet.dto;

import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCoursePlay;
import cn.medcn.meet.model.Live;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/9/27.
 */
@Data
@NoArgsConstructor
public class CourseDeliveryDTO implements Serializable {

    //courseId
    protected Integer id;

    protected String title;//标题

    protected Integer playType;//播放类型0表示录播 1表示ppt直播 2表示视频直播

    protected Date startTime;//开始时间(直播有效)

    protected Date endTime;//结束时间(直播)

    protected String coverUrl;//封面

    /**
     * @see cn.medcn.meet.model.AudioCoursePlay.PlayState
     */
    protected Integer liveState;

    // ppt总页数
    protected Integer pageCount;

    // 正在播放的页码(直播)
    protected Integer livePage;

    // 播放时长
    @Deprecated
    protected String playTime;

    // 录播 正在播放的页码
    protected Integer playPage;

    /**
     * @see cn.medcn.meet.model.AudioCoursePlay.PlayState
     */
    protected Integer playState;


    //投稿者头像
    private String avatar;

    //投稿者姓名
    private String name;

    //投稿者邮箱
    private String email;
    //分类
    private String category;
    //投稿者手机
    private String mobile;
    //投稿时间
    private String deliveryTime;
    //录播ppt总时长
    private Integer duration;

    //会议是否已查看
    private boolean viewState;

    //会议是否已发布
    private boolean publishState;

    protected String info;
    //是否可编辑
    protected Boolean editAble;

    protected Date serverTime = new Date();

    //会议是否被锁定
    protected Boolean locked;
    //是否是引导会议
    protected Boolean guide;

    //星评开关
    protected Boolean starRateFlag;

    //星评人数
    protected Integer scoreCount;
    //综合评分
    protected float avgScore;
    //观看密码
    protected String password;
    /**
     * @see cn.medcn.meet.model.AudioCourse.SourceType
     */
    protected Integer sourceType;

    public float getAvgScore(){
        DecimalFormat format = new DecimalFormat( "#0.0");
        String avgStr = format.format(this.avgScore);
        return Float.valueOf(avgStr);
    }


    public static void splitCoverUrl(List<CourseDeliveryDTO> list,String baseUrl){
        if(list != null){
            for (CourseDeliveryDTO deliveryDTO : list) {
                deliveryDTO.setServerTime(new Date());
                if (deliveryDTO.getGuide() == null) {
                    deliveryDTO.setGuide(false);
                }

                if (deliveryDTO.getGuide()) {
                    deliveryDTO.setTitle("【" + SpringUtils.getMessage("course.novice.guidance") + "】" + deliveryDTO.getTitle());
                }

                if (StringUtils.isNotEmpty(deliveryDTO.getCoverUrl())) {
                    deliveryDTO.setCoverUrl(baseUrl + deliveryDTO.getCoverUrl());
                }

                // 录播会议
                if (deliveryDTO.getPlayType().intValue() == AudioCourse.PlayType.normal.getType()) {
                    // 录播 当前播放第几页
                    if (deliveryDTO.getPlayState() == null || deliveryDTO.getPlayState() == AudioCoursePlay.PlayState.init.ordinal()) {
                        deliveryDTO.setPlayPage(0);
                    } else {
                        if (deliveryDTO.getPlayPage() == null) {
                            deliveryDTO.setPlayPage(0);
                        } else {
                            deliveryDTO.setPlayPage(deliveryDTO.getPlayPage() + 1);
                        }
                    }
                } else {
                    // 直播 当前播放第几页
                    if (deliveryDTO.getLiveState() == null || deliveryDTO.getLiveState() == AudioCoursePlay.PlayState.init.ordinal()) {
                        deliveryDTO.setLivePage(0);
                    } else {
                        if (deliveryDTO.getLivePage() == null) {
                            deliveryDTO.setLivePage(0);
                        } else {
                            deliveryDTO.setLivePage(deliveryDTO.getLivePage() + 1);
                        }
                    }
                }
            }
        }
    }


    public String getPlayTime(){
        long pt = 0;
        if (playType == AudioCourse.PlayType.normal.getType()) {
            pt = getDuration();
        } else {
            if (this.liveState == null) {
                this.liveState = 0;
            }
            if (this.liveState == 0)  {
                pt = 0;
            } else if (endTime != null) {
                pt = (endTime.getTime() - startTime.getTime()) / 1000;
            } else {
                pt = (System.currentTimeMillis() - startTime.getTime()) / 1000;
            }
        }
        return CalendarUtils.formatTimesDiff(pt);
    }

    public Integer getDuration(){
        if (this.playType == null) {
            this.playType = AudioCourse.PlayType.normal.getType();
        }
        if (playType.intValue() == AudioCourse.PlayType.normal.getType()) {
            return duration == null ? 0 : duration;
        } else {
            Long pt = 0l;
            if (liveState != null &&
                    liveState.intValue() == Live.LiveState.usable.ordinal()) {
                Date currentTime = new Date();
                // 直播中 从开始时间到当前时间 直播的时长
                pt = (currentTime.getTime() - startTime.getTime()) / 1000;
                return pt.intValue();
            }

            if (endTime != null && startTime != null) {
                pt = (endTime.getTime() - startTime.getTime()) / 1000;
                return pt.intValue();
            }
            return 0;
        }
    }
}

