package cn.medcn.transfer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lixuan on 2017/5/5.
 */
public class PropertyUtils {


    public static Map<String, String> propCache = new HashMap<>();


    public static String readPropFromCache(String fileName, String key){
        if(propCache.get(key) != null){
            return propCache.get(key);
        }else{
            return readKeyValue(fileName, key);
        }
    }

    /**
     * 传入需要操作的properties文件名称
     * @param propertyFileName
     * @return
     */
    public static Properties read(String propertyFileName){
        InputStream inputStream = null;
        try{
            Properties property = new Properties();
            inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyFileName);
            property.load(inputStream);
            return property;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据文件名和键名获取值
     * @param fileName
     * @param key
     * @return
     */
    public static String readKeyValue(String fileName, String key){
        Properties prop = read(fileName);
        if(prop != null){
            propCache.put(key, prop.getProperty(key));
            return prop.getProperty(key);
        }
        return null;
    }

}
