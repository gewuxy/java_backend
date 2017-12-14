package cn.medcn.csp.utils;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.UrlConverter;

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

    private final static String PUSH_URL = "rtmp://%s.livepush.mycloud.com/live/%s";

    private final static String RTMP_PULL_URL = "rtmp://%s.liveplay.mycloud.com/live/%s";

    private final static String HLS_PULL_URL = "http://%s.liveplay.mycloud.com/live/%s.m3u8";

    private final static String FLV_PULL_URL = "http://%s.liveplay.mycloud.com/live/%s.flv";

    private final static String TX_SECRET_KEY = "txSecret";

    private final static String TX_TIME_KEY = "txTime";


    public enum StreamPullType{
        rtmp,
        flv,
        hls;
    }

    /**
     * 根据频道ID生成推流腾讯直播推流地址
     * @param channelId 频道ID csp中用课件的ID
     * @param expire 推流地址过期时间 单位秒
     * @return
     */
    public static String genPushUrl(String channelId, long expire){
        if (expire == 0) {
            expire = System.currentTimeMillis() + PUSH_URL_DEFAULT_EXPIRE * 1000;
        }

        String pushUrl = String.format(PUSH_URL, BIZ_ID, channelId);

        String hexStr = timeToHexString(expire);

        UrlConverter converter = UrlConverter.newInstance(pushUrl).put(TX_SECRET_KEY, genTxSecret(channelId, hexStr)).
                put(TX_TIME_KEY, hexStr);
        return converter.convert();
    }

    /**
     * 获取拉流地址
     * @param channelId
     * @return
     */
    public static String genStreamPullUrl(String channelId, StreamPullType pullType){
        String pullUrl = null;
        switch (pullType) {
            case flv:
                pullUrl = String.format(FLV_PULL_URL, BIZ_ID, channelId);
                break;
            case rtmp:
                pullUrl = String.format(RTMP_PULL_URL, BIZ_ID, channelId);
                break;
            case hls:
                pullUrl = String.format(HLS_PULL_URL, BIZ_ID, channelId);
                break;
            default:
                break;
        }
        return pullUrl;
    }


    /**
     * 将long型时间戳转换成16进制字符串
     * @param time
     * @return
     */
    private static String timeToHexString(long time){
        StringBuffer buffer = new StringBuffer();
        int remainder = (int)(time % 16); //余数
        int quotient = (int)(time / 16); //商

        boolean convertOver = false;
        while(!convertOver){

            if (quotient == 0) {
                convertOver = true;
            }

            buffer.append(MD5Utils.hexDigits[remainder]);
            remainder = quotient % 16;
            quotient = quotient / 16;

        }

        buffer.reverse();
        String result = buffer.toString().toUpperCase();
        return result;
    }

    /**
     * 生成防盗链密码
     * @return
     */
    private static String genTxSecret(String channelId, String hexStr){
        String signStr = channelId + PICK_PROOF_KEY + hexStr;
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

}
