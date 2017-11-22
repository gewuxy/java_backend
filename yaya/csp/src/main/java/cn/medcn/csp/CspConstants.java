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

    public static final String JI_GUANG_APP_KEY = "61f4b84e5c33850ca329d67d";

    public static final String JI_GUANG_SECRET = "fce25fcc5975048a9f1c32e5";

    //直播流量监控间隔 单位秒
    public static final int FLUX_MONITOR_SPACE = 5;

    //直播流量报警阈值 单位M
    public static final int FLUX_WARN_THRESHOLD = 2048;

    public static final float FLUX_USAGE_PER_SECOND = 0.48f;//直播单人流量消耗 单位 mbps 兆每秒

}
