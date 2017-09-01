package cn.medcn.transfer.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixuan on 2017/6/14.
 */
public class StringUtils {

    /**
     *  获取时间毫秒级别点的ID
     *  格式示例：170421114855167+随机五位数
     *  建议不要在高并发的情况下使用
     *  会造成重复ID
     * @return
     */
    public static String getNowStringID(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
        return format.format(calendar.getTime())+getRandom5();
    }


    public static String getRandom5(){
        Random random = new Random();
        String ret = (random.nextInt(90000)+10000)+"";
        return ret;
    }

    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }

}
