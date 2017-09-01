package cn.medcn.transfer.utils;

import cn.medcn.transfer.support.Timer;

/**
 * Created by lixuan on 2017/6/16.
 */
public class LogUtils {

    private static final String DEBUG = "DEBUG";

    private static final String ERROR = "ERROR";

    private static final String WARM = "WARM";

    public static void debug(Class clazz, String log){
        printLog(DEBUG, clazz, log);
    }


    public static void error(Class clazz, String log){
        printLog(ERROR, clazz, log);
    }

    public static void warm(Class clazz, String log){
        printLog(WARM, clazz, log);
    }

    private static void printLog(String logTag, Class clazz, String log){
        Long usedTime = (System.currentTimeMillis() - Timer.startTime) / 1000;
        System.out.println(Thread.currentThread().getName()+" - "+logTag+" - "+log+ " - "+"Current UsedTime : "+format(usedTime));
    }


    private static String format(long usedTime){
        if(usedTime < 60){
            return usedTime+" s";
        }
        if(usedTime > 60 && usedTime < 3600){
            return usedTime / 60 +" m "+usedTime%60+" s";
        }else{
            return usedTime/3600 +" h "+usedTime%3600/60+" m "+usedTime%60+" s";
        }
    }
}
