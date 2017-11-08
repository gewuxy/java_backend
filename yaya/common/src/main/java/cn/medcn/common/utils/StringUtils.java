package cn.medcn.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixuan on 2017/9/12.
 * 字符串工具类
 */
public class StringUtils {

    // 手机号码正则表达式
    public static final String MOBILE_REG = "^1[3|4|5|7|8][0-9]{9}$";
    // 电子邮箱检测正则表达式
    public static final String EMAIL_REG = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";

    public static final int MAX_NUMBER_LEN = 8;

    public static boolean isEmpty(String str){
        return str == null || str.equals("");
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static boolean endsWith(String src, String suffix){
        if (isEmpty(src)) {
            return false;
        }
        return src.endsWith(suffix);
    }

    public static boolean startsWith(String src, String prefix){
        if (isEmpty(src)) {
            return false;
        }
        return src.startsWith(prefix);
    }


    public static boolean isMatch(String src, String reg){
        if (isEmpty(src)) {
            return false;
        }
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    public static boolean isMobile(String mobile){
        return isMatch(mobile, MOBILE_REG);
    }


    public static boolean isEmail(String email){
        return isMatch(email, EMAIL_REG);
    }

    /**
     * 获取唯一UUID字符串
     * @return
     */
    public static String uniqueStr(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取当前时间点的唯一ID
     * @return
     */
    public static String nowStr(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
        StringBuilder builder = new StringBuilder(format.format(new Date()));
        builder.append(randomNum(5));
        return builder.toString();
    }

    /**
     * 获取指定长度的随机数字
     * @param len
     * @return
     */
    public static int randomNum(int len){
        Random random = new Random();
        int max = max(len) ;
        int min = min(len) ;
        return random.nextInt(max - min) + min;
    }

    /**
     * 获取最小N为整数
     * @param len
     * @return
     */
    public static int min(int len){
        if (len > MAX_NUMBER_LEN)
            throw new RuntimeException("length out of range");
        int number = 1;
        for (int i = 0; i< len - 1; i++) {
            number *= 10;
        }
        return number;
    }

    /**
     * 获取最大len位整数
     * @param len
     * @return
     */
    public static int max(int len){
        if (len > MAX_NUMBER_LEN)
            throw new RuntimeException("length out of range");
        int number = 1;
        for (int i = 0; i < len ; i++) {
            number *= 10;
        }
        return number - 1;
    }


    public static void main(String[] args) {
        String email = "121.123@qq.com";
        System.out.println(isEmail(email));
//        System.out.println(uniqueStr());
    }
}
