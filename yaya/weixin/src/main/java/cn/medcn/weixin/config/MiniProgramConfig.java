package cn.medcn.weixin.config;

/**
 * Created by LiuLP on 2018/1/9/009.
 */
public class MiniProgramConfig {

    public static final String UNIONID_URL = "https://api.weixin.qq.com/sns/jscode2session";

    //获取access_token
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    //获取小程序码url
    public static final String MINI_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";


    public static final String MINI_APPID_KEY = "appid";

    public static final String MINI_SECRET_KEY = "secret";

    public static final String JS_CODE_KEY = "js_code";

    public static final String GRANT_TYPE_KEY = "grant_type";

    public static final String SESSION_KEY_STR = "session_key";

    public static final String ERR_CODE_STR = "errcode";

    public static final String UNION_ID_STR = "unionid";

    public static final String ACCESS_TOKEN_STR = "access_token";

    public static final String PAGE_STR = "page";

    public static final String SCENE_STR = "scene";

    //小程序码的宽度
    public static final String CODE_WIDTH_STR = "width";

    //小程序码自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
    public static final String CODE_AUTO_COLOR_STR = "auto_color";

    //auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
    public static final String CODE_LINE_COLOR_STR = "line_color";


    //获取unionid的授权类型
    public static final String UNION_ID_GRANT_TYPE_VALUE = "authorization_code";

    //获取access_token的授权类型
    public static final String ACCESS_TOKEN_GRANT_TYPE_VALUE = "client_credential";



}
