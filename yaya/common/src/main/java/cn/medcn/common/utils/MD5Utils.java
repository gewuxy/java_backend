package cn.medcn.common.utils;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by lixuan on 2017/1/5.
 */
public class MD5Utils {
    public static final String ENCRYPT_MODE_MD5 = "MD5";

    public static final String ENCRYPT_MODE_SHA1 = "SHA1";

    protected static final String ENCRYPT_DEFAULT_CHARSET = "utf-8";

    protected static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String md5(String origin, String charsetName) {
        return encrypt(origin, charsetName, ENCRYPT_MODE_MD5);
    }

    public static String MD5Encode(String origin){
        return md5(origin);
    }

    public static String MD5Encode(String origin, String charsetName){
        return md5(origin, charsetName);
    }


    public static String encrypt(String origin, String charsetName, String mode){
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance(mode);
            if (charsetName == null || "".equals(charsetName))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetName)));
        } catch (Exception exception) {
        }
        return resultString;
    }


    public static String sha1(String origin, String charsetName){
        return encrypt(origin, charsetName, ENCRYPT_MODE_SHA1);
    }

    public static String sha1(String origin){
        return sha1(origin, ENCRYPT_DEFAULT_CHARSET);
    }

    public static String md5(String origin){
        return md5(origin, ENCRYPT_DEFAULT_CHARSET);
    }

    public static String signature(String secret, String nonce, String timeStamp, String encryptMode){
        if (CheckUtils.isEmpty(secret)) {
            throw new IllegalArgumentException("Secret can not be null");
        }

        if (CheckUtils.isEmpty(nonce)) {
            throw new IllegalArgumentException("Nonce can not be null");
        }

        if (CheckUtils.isEmpty(timeStamp)) {
            throw new IllegalArgumentException("Timestamp can not be null");
        }

        String[] array = new String[]{secret, nonce, timeStamp};
        Arrays.sort(array);
        StringBuffer buffer = new StringBuffer();
        for (String str : array) {
            buffer.append(str);
        }
        return encrypt(buffer.toString(), ENCRYPT_DEFAULT_CHARSET, encryptMode);
    }

}
