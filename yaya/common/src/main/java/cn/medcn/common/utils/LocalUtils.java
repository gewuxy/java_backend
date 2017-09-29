package cn.medcn.common.utils;

import java.util.Locale;

/**
 * Created by lixuan on 2017/9/22.
 */
public class LocalUtils {

    protected static final ThreadLocal<Locale> threadLocal = new ThreadLocal<>();

    protected static final ThreadLocal<String> strLocal = new ThreadLocal<>();

    public static String getLocalStr(){
        return strLocal.get();
    }

    public static void setLocalStr(String _local){
        strLocal.set(_local);
    }

    public static void set(Locale local){
        threadLocal.set(local);
    }

    public static Locale get(){
        return threadLocal.get();
    }


    public static Locale getByKey(String key){
        if (CheckUtils.isEmpty(key)) {
            throw new RuntimeException("local key required");
        }
        String[] array = key.split("_");
        return new Locale(array[0], array[1]);

    }
}
