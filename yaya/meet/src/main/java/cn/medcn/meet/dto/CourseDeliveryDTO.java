package cn.medcn.meet.dto;

import cn.medcn.common.utils.StringUtils;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.Live;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    // 直播状态 0表示未开始 1表示正在直播 2表示已关闭
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

    // 录播状态 0表示未开始 1表示录播中 2表示录播结束
    protected Integer playState;


    //投稿者头像
    private String avatar;

    //投稿者姓名
    private String name;

    //投稿者邮箱
    private String email;

    //录播ppt总时长
    private Integer duration;

    //会议是否已查看
    private boolean viewState;

    //会议是否已发布
    private boolean publishState;

    protected String info;
    //是否可编辑
    protected Boolean editAble;

    public static void splitCoverUrl(List<CourseDeliveryDTO> list,String baseUrl){
        if(list != null){
            for(CourseDeliveryDTO dto :list){
                if(!StringUtils.isEmpty(dto.getCoverUrl())){
                    dto.setCoverUrl(baseUrl + dto.getCoverUrl());
                }
            }
        }
    }


    public String getPlayTime(){
        int pt = getDuration();
        if (pt == 0) {
            return  "00'00\"";
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(pt / 60);
            buffer.append("'").append(pt % 60).append("\"");
            return buffer.toString();
        }
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

