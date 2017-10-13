package cn.medcn.common.utils;

/**
 * 存放定制版信息的工具类
 * Created by lixuan on 2017/10/13.
 */
public class TailorMadeUtils {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();


    public static Integer get(){
        return threadLocal.get();
    }

    public static void set(Integer masterId){
        threadLocal.set(masterId);
    }
}
