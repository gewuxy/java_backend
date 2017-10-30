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

    public static final int ORDER_SCAN_SUCCESS = 100;

    //直播课件ID
    protected String courseId;
    //指令代码 0表示直播 1表示同步 2表示踢人指令 3表示拒绝被踢 4表示接受被踢
    protected int order;
    //PPT的当前页码
    protected int pageNum;
    //当前的页面音频路径 在直播的时候才有
    protected String audioUrl;
    //指令来自哪个端 app , web
    protected String orderFrom;
    //消息内容
    protected String msg;
    //消息发送人的会话ID
    protected String sid;


    public static LiveOrderDTO buildKickOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_KICK);
        order.setCourseId(courseId);
        return order;
    }


    public static LiveOrderDTO buildKickAcceptOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_KICK_ACCEPT);
        order.setCourseId(courseId);
        return order;
    }

    public static LiveOrderDTO buildKickRefuseOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_KICK_REFUSE);
        order.setCourseId(courseId);
        return order;
    }


    public static LiveOrderDTO buildDuplicateOrder(String courseId){
        LiveOrderDTO order = new LiveOrderDTO();
        order.setOrder(ORDER_DUPLICATE);
        order.setCourseId(courseId);
        return order;
    }

}
