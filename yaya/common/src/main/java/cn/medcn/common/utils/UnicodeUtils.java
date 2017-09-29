package cn.medcn.common.utils;

/**
 * Created by lixuan on 2017/9/29.
 */
public class UnicodeUtils {

    /**
     * 将Unicode编码的字符串转换成中文
     * 需确保ascii为Unicode编码
     * @param ascii
     * @return
     */
    public static String ascii2native(String ascii) {
        int n = ascii.length() / 6;
        StringBuffer sb = new StringBuffer(n);
        for (int i = 0, j = 2; i < n; i++, j += 6) {
            String code = ascii.substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            sb.append(ch);
        }
        return sb.toString();
    }
}
