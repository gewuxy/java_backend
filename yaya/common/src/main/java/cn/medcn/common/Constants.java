package cn.medcn.common;

import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/4/19.
 */
public class Constants {
    /**
     * 请求传递的token的key
     */
    public static final String TOKEN = "token";
    /**
     * token超时时间 2天
     */
    public static final int TOKEN_EXPIRE_TIME = (int) TimeUnit.DAYS.toSeconds(30);

    public static final String KEY_WORD = "keyword";

    /**缓存默认超时时间*/
    public static final int DEFAULT_CACHE_EXPIRE_TIME = 3*60;
    /* 短信验证码的超时时间*/
    public static final int CAPTCHA_CACHE_EXPIRE_TIME = 15*60;

    /**支付宝公共请求参数格式*/
    public static final String FORMAT = "json";

    /**支付宝请求使用的编码格式*/
    public static final String CHARSET = "utf-8";

    /**支付宝商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2*/
    public static final String SIGN_TYPE = "RSA2";

    /** 设置未付款支付宝交易的超时时间*/
    public static final String TIMEOUT = "30m";

    /** app支付销售产品码，商家和支付宝签约的产品码，为固定值*/
    public static final String PRODUCTCODE = "QUICK_MSECURITY_PAY";

    /** 电脑网站支付销售产品码*/
    public static final String WEB_ALIPAY_PRODUCTCODE = "FAST_INSTANT_TRADE_PAY";


    public static final String MEET_ID_KEY = "meetId";

    public static final String LOG_START_KEY = "log_start";

    public static final String UPLOAD_PROGRESS_KEY = "upload_progress";

    /** 文件后缀名: jpg*/
    public static final String FILE_SUFFIX_JPG = "jpg";

    public static final String PASSWORD_RESET_PREFIX_KEY = "pwd_reset_";

    //重置密码或者绑定邮箱时保存用户名所使用的key
    public static final String EMAIL_LINK_PREFIX_KEY = "link_";

    public static final long MEET_MATERIAL_LIMIT_SIZE = 8*1024*1024;
    /**
     * 未关注用户预览ppt语音页数
     */
    public static final int NOT_ATTENTION_PPT_VIEW_PAGES = 5;

    public static final String APP_VERSION_KEY = "app_version";

    public static final String APP_VERSION_MANAGER = "app_manager";

    public static final String APP_OS_TYPE_KEY = "os_type";

    public static final String APP_OS_VERSION_KEY = "os_version";

    public static final String OS_TYPE_IOS = "ios";

    public static final String OS_TYPE_ANDROID = "android";

    public static final String PRINCIPAL_KEY = "principal";

    //用户注册时默认关注敬信公众号
    public static final Integer DEFAULT_ATTENTION_PUBLIC_ACCOUNT = 207668;

    public static final int YAYA_TESTING_USER_ID = 1200007;

    public static final String DEFAULT_INVITE = "2603";

    public static final String DEFAULT_HOS_NAME = "敬信药草园";

    public static final String OFFICE_CONVERT_PROGRESS = "office_convert_progress";

    //短信验证码发送模板id
    public static final Integer DEFAULT_TEMPLATE_ID = 40198;

    public static final String JSMS_MSG_ID_KEY = "jsms_msg_id_key";

    //男性用户默认头像
    public static final String USER_DEFAULT_AVATAR_MAN = "others/metting-img-man.jpg";
    //女性用户默认头像
    public static final String USER_DEFAULT_AVATAR_FEMALE = "others/metting-img-girl.jpg";

    public static final String DEFAULT_MEET_COVER_URL = "default.png";

    public static final int NUMBER_ZERO = 0;

    public static final int NUMBER_ONE = 1;

    public static final int NUMBER_TWO = 2;

    public static final int NUMBER_THREE = 3;

    public static final int NUMBER_FOUR = 4;

    public static final int NUMBER_FIVE = 5;

    public static final int NUMBER_TEN = 10;

    public static final int NUMBER_HUNDRED = 100;

    public static final int NUMBER_THOUSAND = 1000;

    //一天秒钟数
    public static final long DAY_SECONDS = TimeUnit.DAYS.toSeconds(NUMBER_ONE);
    //一小时的秒钟数
    public static final long HOUR_SECONDS = TimeUnit.HOURS.toSeconds(NUMBER_ONE);
    //一分钟秒钟数
    public static final long MINUTE_SECONDS = TimeUnit.MINUTES.toSeconds(NUMBER_ONE);
    //一天的小时数
    public static final int DAY_HOURS = 24;

    public static final int BYTE_UNIT_K = 1024;

    public static final int BYTE_UNIT_M = BYTE_UNIT_K*BYTE_UNIT_K;

    public static final int BYTE_UNIT_G = BYTE_UNIT_M*BYTE_UNIT_K;

    public static final String WX_TOKEN_KEY_SUFFIX = "wx_token_";

    // 会议科室默认‘全部科室’图标路径
    public static final String MEET_DEFAULT_DEPARTMENT_ICON = "department/icon/0.png";

    // 要转换的日期格式
    public static final String DATE_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss";

    // 会议提醒提前时间 单位分钟
    public static final int MEET_NOTIFY_PRE_TIME = 15;

    public static final String DEFAULT_LOCAL = "zh_CN";

    public static final String LOCAL_KEY = "_local";

    public static final String ABROAD_KEY = "abroad";


    /* CSP短信验证码发送模板id */
    public static final Integer CSP_LOGIN_TEMPLATE_ID = 145643;

    /* CSP绑定手机短信验证码模板id*/
    public static final Integer CSP_BIND_TEMPLATE_ID =  145644;

    /* CSP 缓存验证码key */
    public static final String CSP_MOBILE_CACHE_PREFIX_KEY = "mobile_";

    /* CSP 广告倒计时默认值(s) */
    public static final int CSP_ADVERT_COUNT_DOWN = 3;

    public static final String DES_PRIVATE_KEY = "2b3e2d604fab436eb7171de397aee892";
    // 视频会议大小限制
    public static final int UPLOAD_VIDEO_SIZE_LIMIT = 500;
    // ppt课件视频大小限制
    public static final int UPLOAD_PPT_VIDEO_SIZE_LIMIT = 50;
    //麦瑞单位号ID
    public static final int MARY_MASTER_ID = 957425;
    //定制版单位号ID key
    public static final String MASTER_ID_KEY = "masterId";


    // CSP用户头像地址前缀
    public static final String AVATAR_URL_PREFIX = "http";

    // 资源平台 共享资源页面每页显示条数
    public static final int SHARE_PAGE_SIZE = 16;

    // CSP用户登录缓存账号
    public static final String LOGIN_USER_KEY = "csp_username";

    public static final String LOGIN_USER_ID_KEY = "csp_id";

    // CSP用户缓存时长
    public static final int LOGIN_COOKIE_MAX_AGE = 7;

    /********** 合理用药 *************/

}
