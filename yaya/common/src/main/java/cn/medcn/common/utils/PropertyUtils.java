package cn.medcn.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lixuan on 2017/5/5.
 */
public class PropertyUtils {

    /**
     * 传入需要操作的properties文件名称
     * @param fileName
     * @return
     */
    public static Properties read(String fileName){
        InputStream in = null;
        try{
            Properties prop = new Properties();
            in = PropertyUtils.class.getClassLoader().getResourceAsStream(fileName);
            prop.load(in);
            return prop;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            return prop.getProperty(key);
        }
        return null;
    }

}
