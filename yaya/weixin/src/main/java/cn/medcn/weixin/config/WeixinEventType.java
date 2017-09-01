package cn.medcn.weixin.config;

/**
 * Created by lixuan on 2017/7/24.
 */
public class WeixinEventType {
    //事件Key
    public final static String EVENT_KEY = "Event";
    //用户关注公众号
    public final static String EVENT_TYPE_SUBSCRIBE = "subscribe";
    //用户进入公众号
    public final static String EVENT_TYPE_ENTER_SESSION = "user_scan_product_enter_session";
    //用户取消关注公众号
    public final static String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    //用户上报地理位置
    public final static String EVENT_TYPE_LOCATION = "LOCATION";
    //用户已经关注过后的扫描
    public final static String EVENT_TYPE_SCAN = "SCAN";

    public final static String EVENT_TO_USRENAME = "ToUserName";

    public final static String EVENT_FROM_USERNAME = "FromUserName";

}
