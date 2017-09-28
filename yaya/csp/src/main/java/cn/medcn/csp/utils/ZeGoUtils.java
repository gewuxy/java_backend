package cn.medcn.csp.utils;

import cn.medcn.common.utils.MD5Utils;

/**
 * 即构appKey工具类
 * Created by lixuan on 2017/9/28.
 */
public class ZeGoUtils {

    public static byte[] parseSignKeyFromString(String strSignKey) throws NumberFormatException {
        String[] keys = strSignKey.split(",");
        if (keys.length != 32) {
            throw new NumberFormatException("App Sign Key Illegal");
        }
        byte[] byteSignKey = new byte[32];
        for (int i = 0; i < 32; i++) {
            int data = Integer.valueOf(keys[i].trim().replace("0x", ""), 16);
            byteSignKey[i] = (byte) data;
        }
        return byteSignKey;
    }

    public static String byteArrayToString(byte[] signKey) {
        StringBuilder buffer = new StringBuilder();
        for (int b : signKey) {
            buffer.append("0x").append(Integer.toHexString((b & 0x000000FF) | 0xFFFFFF00).substring(6)).append(",");
        }
        buffer.setLength(buffer.length() - 1);
        return buffer.toString();
    }


    public static String parseAppKey(String appKey){
        return MD5Utils.byteArrayToHexString(parseSignKeyFromString(appKey));
    }
}
