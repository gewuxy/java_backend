package cn.medcn.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Created by lixuan on 2017/1/11.
 */
public class UUIDUtil {

    /**
     * 获取唯一ID
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

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
        Random random = new Random();
        return format.format(calendar.getTime())+getRandom5();
    }


    public static String getRandom5(){
        Random random = new Random();
        String ret = (random.nextInt(90000)+10000)+"";
        return ret;
    }

    public static void main(String[] args) {

        String id = null;
        for(int i =0 ; i < 1000; i++){
            id = getNowStringID();
            System.out.println(id+" - length="+id.length());
        }
    }
}
