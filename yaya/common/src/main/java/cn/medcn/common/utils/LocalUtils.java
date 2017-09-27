package cn.medcn.common.utils;

import java.util.Locale;

/**
 * Created by lixuan on 2017/9/22.
 */
public class LocalUtils {

    protected static final ThreadLocal<Locale> threadLocal = new ThreadLocal<>();

    public static void set(Locale local){
        threadLocal.set(local);
    }

    public static Locale get(){
        return threadLocal.get();
    }


    public static Locale getByKey(String key){
        Locale locale = null;
        switch (key) {
            case "zh_CN" :
                locale = Locale.CHINA;
                break;
            case "en_US" :
                locale = Locale.US;
        }
        return locale;
    }
}
