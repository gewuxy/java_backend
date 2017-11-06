package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    public static final int ORDER_SCAN_SUCCESS = 100;

    public static final String LIVE_TYPE_PPT = "0";

    public static final String LIVE_TYPE_VIDEO = "1";

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

}
