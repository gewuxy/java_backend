package cn.medcn.common.utils;

import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

/**
 * Created by lixuan on 2017/4/24.
 */
public class ActiveCodeUtils {

    public static final String CODE_TYPE_DOC = "00";

    public static final String CODE_TYPE_PAT = "01";

    /**
     * 按类型生成激活码
     * @param type
     * @return
     */
    public static String genericActiveCode(String type){
        StringBuilder builder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yySSS");
        builder.append(type);
        builder.append(format.format(calendar.getTime()));
        builder.append(getRandomStr(5));
        return builder.toString();
    }

    /**
     * 生成激活码
     * 默认生成医生的激活码
     * @return
     */
    public static String genericActiveCode(){
        return genericActiveCode(CODE_TYPE_DOC);
    }

    /**
     * 根据长度生成随机数
     * @param len
     * @return
     */
    private static String getRandomStr(int len){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int reandomNum = 0;
        for(int i=0; i<len; i++){
            reandomNum = random.nextInt(10);
            builder.append(reandomNum);
        }
        return builder.toString();
    }


    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        for(int i = 0; i < 10000 ; i++){
            String random = genericActiveCode();
            System.out.println(random);
            map.put(random, random);
        }
        System.out.println("random map.size = "+map.size());

    }
}
