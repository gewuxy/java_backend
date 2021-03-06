package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/9/27.
 */
@Data
@NoArgsConstructor
public class LiveOrderDTO implements Serializable{

    public static final int ORDER_LIVE=  0;//直播

    public static final int ORDER_SYNC = 1;//同步

    public static final int ORDER_KICK = 2;//踢人

    public static final int ORDER_KICK_REFUSE = 3;//拒绝被踢

    public static final int ORDER_KICK_ACCEPT = 4;//接受被踢

    public static final int ORDER_DUPLICATE = 5;//存在重复登录

    public static final int ORDER_USER_JOIN = 6;//用户进入

    public static final int ORDER_FLUX_NOT_ENOUGH = 7;//流量不足预警

    public static final int ORDER_FLUX_EXHAUSTED = 8;//流量耗尽

    public static final int ORDER_FLUX_AMPLE = 9;//流量充足

    public static final int ORDER_VIDEO_LIVE_CLOSE = 10;//视频直播关闭

    public static final int ORDER_LIVE_START = 11;//直播开始

    public static final int ORDER_LIVE_STREAM_PUSH = 12;//直播推流

    public static final int ORDER_STAR_RATE_START = 13;//开始星评

    public static final int ORDER_LIVE_OVER = 14;//直播结束

    public static final int ORDER_SCAN_SUCCESS = 100;

    public static final String LIVE_TYPE_PPT = "0";

    public static final String LIVE_TYPE_VIDEO = "1";

    public static final int ORDER_HEART_BEAT = 101;//心跳指令

    //直播课件ID
    protected String courseId;

    protected Integer detailId;
    //指令代码 0表示直播 1表示同步 2表示踢人指令 3表示拒绝被踢 4表示接受被踢
    protected int order;
    //PPT的当前页码
    protected int pageNum;
    //当前页面的图片地址 直播时才有
    protected String imgUrl;
    //当前的页面音频路径 在直播的时候才有
    protected String audioUrl;
    //当前页视频路径 直播时才有
    protected String videoUrl;
    //指令来自哪个端 app , web
    protected String orderFrom;
    //消息内容
    protected String msg;
    //消息发送人的会话ID
    protected String sid;
    //在线人数
    protected int onLines;

    protected String liveType;


    public static LiveOrderDTO buildUserJoinOrder(String courseId, String sid, int onLines){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_USER_JOIN);
        order.setCourseId(courseId);
        order.setSid(sid);
        order.setOnLines(onLines);
        return order;
    }


    public static LiveOrderDTO buildKickOrder(String courseId, String sid, String liveType){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_KICK);
        order.setSid(sid);
        order.setLiveType(liveType);
        order.setCourseId(courseId);
        return order;
    }


    public static LiveOrderDTO buildKickAcceptOrder(String courseId, String sid, String liveType){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_KICK_ACCEPT);
        order.setLiveType(liveType);
        order.setCourseId(courseId);
        order.setSid(sid);
        return order;
    }

    public static LiveOrderDTO buildKickRefuseOrder(String courseId, String sid, String liveType){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_KICK_REFUSE);
        order.setLiveType(liveType);
        order.setCourseId(courseId);
        order.setSid(sid);
        return order;
    }


    public static LiveOrderDTO buildDuplicateOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_DUPLICATE);
        order.setCourseId(courseId);
        return order;
    }

    public static LiveOrderDTO buildHeartBeatOrder(){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_HEART_BEAT);
        return order;
    }


    public static LiveOrderDTO buildFluxNotEnoughOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(courseId);
        order.setOrder(ORDER_FLUX_NOT_ENOUGH);
        return order;
    }

    public static LiveOrderDTO buildFluxExhaustedOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(courseId);
        order.setOrder(ORDER_FLUX_EXHAUSTED);
        return order;
    }

    public static LiveOrderDTO buildFluxAmpleOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(courseId);
        order.setOrder(ORDER_FLUX_AMPLE);
        return order;
    }


    public static LiveOrderDTO buildVideoLiveCloseOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setCourseId(courseId);
        order.setOrder(ORDER_VIDEO_LIVE_CLOSE);
        return order;
    }

}
