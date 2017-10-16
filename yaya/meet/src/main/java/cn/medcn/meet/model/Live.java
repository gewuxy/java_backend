package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lixuan on 2017/9/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_live")
public class Live {

    @Id
    protected String id;

    protected Integer courseId;//对应的课件ID

    protected Boolean videoLive;//是否视频直播

    protected String rtmpUrl;//rtmp协议拉流地址

    protected String hlsUrl;//hls协议拉流地址

    protected String hdlUrl;//hdl协议拉流地址

    protected String picUrl;//视频截图地址

    protected Date startTime;//直播开始时间

    protected Date endTime;//直播结束时间

    protected Integer liveState;//直播状态

    protected Integer closeType;//关闭状态

    protected String replayUrl;//回放地址

    protected Date expireDate;//过期时间

    protected Integer playCount;//观看总人数

    protected Integer onlineCount;//在线人数

    protected Integer livePage; //正在直播的ppt页码

    public enum LiveState{
        init(0),//初始状态
        usable(1),//可用的直播状态
        closed(2);//已关闭

        private Integer type;

        public Integer getType(){
            return type;
        }

        LiveState(Integer type){
            this.type = type;
        }
    }
}
