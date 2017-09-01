package cn.medcn.transfer.utils;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/19.
 */
public class DateUtils {

    public static Date parseDate(String dateStr){
        if(dateStr == null || "".equals(dateStr)){
            return null;
        }
        dateStr = dateStr.replaceAll("-", "/");
        return new Date(Date.parse(dateStr));
    }
}
