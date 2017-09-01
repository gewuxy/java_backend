package cn.medcn.common.utils;

import org.apache.commons.logging.Log;

/**
 * Created by lixuan on 2017/1/3.
 */
public class LogUtils {

    public static void debug(Log log, String msg){
        if (log.isDebugEnabled())
            log.debug(msg);
    }

    public static void info(Log log,String msg){
        if (log.isInfoEnabled()){
            log.info(msg);
        }
    }

    public static void error(Log log, String msg){
        log.error(msg);
    }
}
