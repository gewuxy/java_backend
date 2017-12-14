package cn.medcn.csp.utils;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.UrlConverter;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.medcn.csp.CspConstants.TX_LIVE_API_TIME_OUT;

/**
 * 腾讯直播API
 * Created by lixuan on 2017/12/8.
 */
public class TXLiveUtils {
    //腾讯视频直播 appId
    private static final String APP_ID = "1254946096";
    //腾讯推拉流子域名标识
    private final static String BIZ_ID = "17932";
    //腾讯API鉴权key 主要用于回调时判断请求是否合法用
    private final static String API_AUTHENTICATION_KEY = "7d13dea73dba1fdc83a621f600359d3b";
    //防盗链key
    private final static String PICK_PROOF_KEY = "807f96218e8343557a2cac85c7c2329c";
    //推流地址默认超时时长
    private final static int PUSH_URL_DEFAULT_EXPIRE = 24 * 3600;

    private final static String BASE_PUSH_URL = "livepush.myqcloud.com/live/";

    private final static String BASE_PULL_URL = "liveplay.myqcloud.com/live/";

    private final static String RTMP_PREFIX = "rtmp://";

    private final static String HTTP_PREFIX = "http://";

    private final static String HLS_SUFFIX = ".m3u8";

    private final static String FLV_SUFFIX = ".flv";

    private final static String TX_SECRET_KEY = "txSecret";

    private final static String TX_TIME_KEY = "txTime";

    private final static String BIZ_ID_KEY = "bizid";

    private static String getFileName(String channelId){
        return BIZ_ID + "_" + channelId;
    }

    /**
     * 获取基础推流地址
     * @return
     */
    private static String getBasePushUrl(String channelId){
        StringBuilder builder = new StringBuilder();
        builder.append(RTMP_PREFIX).append(BIZ_ID).append(".").append(BASE_PUSH_URL).append(getFileName(channelId));
        return builder.toString();
    }


    public enum StreamPullType{
        rtmp,
        flv,
        hls;
    }

    /**
     * 根据频道ID生成推流腾讯直播推流地址
     * @param channelId 频道ID csp中用课件的ID
     * @param expire 推流地址过期时间 单位毫秒
     * @return
     */
    public static String genPushUrl(String channelId, long expire){
        if (expire == 0) {
            expire = System.currentTimeMillis() + PUSH_URL_DEFAULT_EXPIRE * 1000;
        }

        String pushUrl = getBasePushUrl(channelId);

        String hexStr = Long.toHexString(expire / 1000).toUpperCase();

        UrlConverter converter = UrlConverter.newInstance(pushUrl).put(TX_SECRET_KEY, genTxSecret(channelId, hexStr)).
                put(TX_TIME_KEY, hexStr).
                put(BIZ_ID_KEY, BIZ_ID);
        return converter.convert();
    }

    /**
     * 获取拉流地址
     * @param channelId
     * @return
     */
    public static String genStreamPullUrl(String channelId, StreamPullType pullType){

        String fileNamePrefix = getFileName(channelId);
        String pullUrl = null;
        switch (pullType) {
            case flv:
                pullUrl = HTTP_PREFIX + BIZ_ID + "." + BASE_PULL_URL + fileNamePrefix + FLV_SUFFIX;
                break;
            case rtmp:
                pullUrl = RTMP_PREFIX + BIZ_ID + "." + BASE_PULL_URL + fileNamePrefix;
                break;
            case hls:
                pullUrl = HTTP_PREFIX + BIZ_ID + "." + BASE_PULL_URL + fileNamePrefix + HLS_SUFFIX;
                break;
            default:
                break;
        }
        return pullUrl;
    }

    /**
     * 生成防盗链密码
     * @return
     */
    private static String genTxSecret(String channelId, String hexStr){
        String signStr = PICK_PROOF_KEY + getFileName(channelId) + hexStr;
        return MD5Utils.md5(signStr);
    }


    /**
     * 验证签名
     * @param sign
     * @param t
     * @return
     */
    public static void verify(String sign, long t) throws SystemException {

        if (CheckUtils.isEmpty(sign)) {
            throw new SystemException("sign can not be null");
        }

        long now = System.currentTimeMillis();
        //请求已经超过10分钟 视为无效请求
        if (now > (t + TX_LIVE_API_TIME_OUT * 60) * 1000) {
            throw new SystemException("tx live api request time out ");
        }

        if (!sign.equals(MD5Utils.md5(API_AUTHENTICATION_KEY + t))) {
            throw new SystemException("invalid sign : " + sign);
        }
    }

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1469848425000l)));
        System.out.println(Long.toHexString(1469848425 ));

    }

}
