package cn.medcn.csp.admin.utils;

import org.springframework.core.convert.converter.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @Author：jianliang
 * @Date: Creat in 18:00 2017/11/15
 */
public class DateConveter implements Converter<String, Date>{
    @Override
    public Date convert(String source) {
        try {
            if(null != source){//2016:11-05 11_43-50
                DateFormat df = new SimpleDateFormat("yyyy:MM-dd HH_mm-ss");
                return df.parse(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
