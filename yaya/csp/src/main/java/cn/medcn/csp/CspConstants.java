package cn.medcn.csp;

import java.util.Arrays;

/**
 * Created by lixuan on 2017/9/27.
 */
public class CspConstants {

    public static final String TOKEN_KEY = "csp_token";

    public static final String COURSE_ID_KEY = "courseId";
    //响应即构回调成功的code
    public static final String ZEGO_SUCCESS_CODE = "1";
    //发布直播会议最少流量限制 单位G
    public static final int MIN_FLUX_LIMIT = 10;

    public static final String LIVE_TYPE_KEY = "liveType";

    //直播流量监控间隔 单位秒
    public static final int FLUX_MONITOR_SPACE = 5;

    //直播流量报警阈值 单位M
    public static final int FLUX_WARN_THRESHOLD = 2048;

    public static final float FLUX_USAGE_PER_SECOND = 0.48f / 8;//直播单人流量消耗 单位 mbps 兆每秒

    //腾讯视频直播api超时时间 单位分钟
    public static final int TX_LIVE_API_TIME_OUT = 10;


    public static final String MEET_COUNT_OUT_TIPS_KEY = "meet_count_out_tips";

    //套餐订单标识
    public static final String PACKAGE_ORDER_FLAG = "T";
}
