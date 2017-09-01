package cn.medcn.weixin.config;

/**
 * Created by lixuan on 2017/7/18.
 */
public class WeixinConfig {
    //微信默认编码
    public static final String WX_ENCODING = "ISO-8859-1";

    public static final String TARGET_ENCODING = "utf-8";
    //获取全局access_token
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    //获取授权access_token(授权页面)
    public static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    //获取服务器ip
    public static final String GET_SERVER_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
    //创建二维码地址
    public static final String QRCODE_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    //呈现二维码地址
    public static final String QRCODE_SHOW_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
    //获取用户信息地址
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

    public static final String GET_USERINFO_BY_OAUTH2_URL = "https://api.weixin.qq.com/sns/userinfo";
    //菜单创建地址
    public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
    //菜单删除地址
    public static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    //菜单查询地址
    public static final String MENU_SELECT_URL = "https://api.weixin.qq.com/cgi-bin/menu/get";
    //oauth认证地址
    public static final String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    //oauth获取token
    public static final String OAUTH_GET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    //模板消息发送接口
    public static final String TEMPLATE_MESSAGE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    //开发者appid
    public static final String PARAM_APPID_KEY = "appid";
    //开发者secret
    public static final String PARAM_SECRET_KEY = "secret";

    public static final String PARAM_REDIRECT_URL_KEY = "redirect_uri";

    public static final String PARAM_RESPONE_TYPE_KEY = "response_type";

    public static final String PARAM_SCOPE_KEY = "scope";

    public static final String SCOPE_TYPE_DEFAULT = "snsapi_base";

    public static final String SCOPE_TYPE_USERINFO = "snsapi_userinfo";

    public static final String WE_CHAT_REDIRECT = "wechat_redirect";

    public static final String PARAM_STATE_KEY = "state";

    public static final String DEFAULT_STATE = "medcn";

    //获取授权token时的参数
    public static final String CODE_KEY = "code";

    public static final String PARAM_GRANT_TYPE_KEY = "grant_type";

    public static final String GRANT_TYPE_GET_ACCESS_TOKEN = "client_credential";
    //获取网页授权token时的grant_type
    public static final String OAUTH2_GRANT_TYPE_GET_ACCESS_TOKEN = "authorization_code";

    public static final String WE_CHAT_ACCESS_TOKEN_KEY = "we_chat_access_token";
    //全局tokenkey
    public static final String WE_CHAT_GLOBAL_ACCESS_TOKEN_KEY = "we_chat_global_token";

    public static final String ACCESS_TOKEN_KEY = "access_token";

    public static final String OPENID_KEY = "openid";

    public static final int TOKEN_EXPIRE_CODE = 42001;

    public static final int TOKEN_MISS_ERROR = 41001;

    public static final String DEFAULT_REPLY_SUCESS = "success";

    public static final String RETURN_SUCCESS_MSG = "OK";

    public static final Integer TOKEN_EXPIRE_TIME = 7200;

    public final static String COOKIE_NAME_OPEN_ID = "jx_openid";

    public final static String COOKIE_NAME_UNION_ID = "jx_union_id";

    public static final String REDIRECT_HISTORY = "redirect_history";

    public final static String SCENE_ID_KEY = "scene_id";
}
