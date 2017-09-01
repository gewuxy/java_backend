package cn.medcn.common.utils;

import java.util.Collection;

/**
 * 检测工具类
 * Created by lixuan on 2017/7/19.
 */
public class CheckUtils {

    /**
     * 检测集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        if(collection == null){
            return true;
        }
        return collection.isEmpty();
    }

    /**
     * 检测数组是否为Null 或者 空数组
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        if (array == null){
            return true;
        }
        return array.length == 0;
    }

    /**
     * 检测两个字符串是否相等
     * @param source
     * @param target
     * @return
     */
    public static boolean isEquals(String source, String target){
        if(source == null || target == null){
            return false;
        }
        return source.equals(target);
    }

    /**
     * 检测两个整型对象是否相等
     * @param source
     * @param target
     * @return
     */
    public static boolean isEquals(Integer source, Integer target){
        if(source == null || target == null){
            return false;
        }
        return source.intValue() == target.intValue();
    }

    /**
     * 检测两个长型对象是否相等
     * @param source
     * @param target
     * @return
     */
    public static boolean isEquals(Long source, Long target){
        if(source == null || target == null){
            return false;
        }
        return source.longValue() == target.longValue();
    }

    /**
     * 检测两个浮点数对象是否相等
     * @param source
     * @param target
     * @return
     */
    public static boolean isEquals(Float source, Float target){
        if(source == null || target == null){
            return false;
        }
        return source.floatValue() == target.floatValue();
    }

    /**
     * 检测两个双精度数对象是否相等
     * @param source
     * @param target
     * @return
     */
    public static boolean isEquals(Double source, Double target){
        if(source == null || target == null){
            return false;
        }
        return source.doubleValue() == target.doubleValue();
    }

    /**
     * 检测字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return null == str || "".equals(str);
    }
}
