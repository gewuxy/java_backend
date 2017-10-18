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

    public static final int ORDER_SYNC_LIVE = 2;//综合指令 同步和直播

    public static final int ORDER_SCAN_SUCCESS = 100;

    //直播课件ID
    protected String courseId;
    //指令代码 0表示直播 1表示同步 2表示同步和直播
    protected int order;
    //PPT的当前页码
    protected int pageNum;
    //当前的页面音频路径 在直播的时候才有
    protected String audioUrl;

}
