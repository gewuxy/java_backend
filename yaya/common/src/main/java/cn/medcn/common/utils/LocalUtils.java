package cn.medcn.common.utils;

import cn.medcn.common.Constants;

import java.util.Locale;

/**
 * Created by lixuan on 2017/9/22.
 */
public class LocalUtils {

    protected static final ThreadLocal<Locale> threadLocal = new ThreadLocal<>();

    protected static final ThreadLocal<String> strLocal = new ThreadLocal<>();

    protected static final ThreadLocal<Boolean> abroadLocal = new ThreadLocal<>();

    protected static final ThreadLocal<String> osTypeLocal = new ThreadLocal<>();

    public static void setOsTypeLocal(String osType){
        osTypeLocal.set(osType);
    }


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

    /**
     * 是否是国外 并且iOS系统 这种情况需要设置特定的极光ID
     * @return
     */
    public static Boolean isAbroadAndIOS() {
        return isAbroad() && Constants.OS_TYPE_IOS.equalsIgnoreCase(osTypeLocal.get());
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

    public enum Abroad{
        N(),
        Y();
    }
}
