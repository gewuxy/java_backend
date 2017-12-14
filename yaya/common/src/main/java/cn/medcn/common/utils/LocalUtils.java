package cn.medcn.common.utils;

import java.util.Locale;

/**
 * Created by lixuan on 2017/9/22.
 */
public class LocalUtils {

    protected static final ThreadLocal<Locale> threadLocal = new ThreadLocal<>();

    protected static final ThreadLocal<String> strLocal = new ThreadLocal<>();

    protected static final ThreadLocal<Boolean> abroadLocal = new ThreadLocal<>();


    public static Boolean isAbroad(){
        return abroadLocal.get() == null ? false : abroadLocal.get();
    }

    public static void setAbroad(Boolean abroad){
        abroadLocal.set(abroad == null ? false : abroad);
    }

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

    /**
     * 是否是海外的请求
     * @return
     */
    public boolean isAbroadRequest(){
        return !get().equals(Locale.CHINA);
    }


    public static Locale getByKey(String key){
        if (CheckUtils.isEmpty(key)) {
            throw new RuntimeException("local key required");
        }
        String[] array = key.split("_");
        return new Locale(array[0], array[1]);

    }

    public enum Local {
        en_US(),
        zh_CN(),
        zh_TW();
    }
}
