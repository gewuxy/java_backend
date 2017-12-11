package cn.medcn.csp.utils;

import cn.medcn.common.utils.MD5Utils;

/**
 * 腾讯直播工具类
 * Created by lixuan on 2017/12/8.
 */
public class TXLiveUtils {

    //todo 所有腾讯直播相关的参数需替换成真实的值

    public final static String BIZID = "8888";

    public final static String APPKEY = "123123123123";
    //防盗链key
    public final static String PICKPROOF_KEY = "asdfasdfasdfasdfasdf";
    //推流地址默认超时时长
    public final static int PUSH_URL_DEFAULT_EXPIRE = 24 * 3600;

    public final static String PUSH_URL = "rtmp://%s.livepush.mycloud.com/live/%s";

    public final static String RTMP_PULL_URL = "rtmp://%s.liveplay.mycloud.com/live/%s";

    public final static String HLS_PULL_URL = "http://%s.liveplay.mycloud.com/live/%s.m3u8";

    public final static String FLV_PULL_URL = "http://%s.liveplay.mycloud.com/live/%s.flv";


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

        String pushUrl = String.format(PUSH_URL, BIZID, channelId);
        StringBuffer buffer = new StringBuffer();
        buffer.append(pushUrl);

        String hexStr = timeToHexString(expire);

        buffer.append("?txSecret=")
                .append(genTxSecret(channelId, hexStr))
                .append("&txTime=")
                .append(hexStr);
        return buffer.toString();
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
                pullUrl = String.format(FLV_PULL_URL, BIZID, channelId);
                break;
            case rtmp:
                pullUrl = String.format(RTMP_PULL_URL, BIZID, channelId);
                break;
            case hls:
                pullUrl = String.format(HLS_PULL_URL, BIZID, channelId);
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
        String signStr = channelId + PICKPROOF_KEY + hexStr;
        return MD5Utils.md5(signStr);
    }

    public static void main(String[] args) {
        String hexStr = timeToHexString(System.currentTimeMillis());
        System.out.println(hexStr);

        System.out.println(genPushUrl("123", 0));
    }
}
