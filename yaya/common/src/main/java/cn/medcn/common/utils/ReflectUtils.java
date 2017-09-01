package cn.medcn.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lixuan on 2017/7/19.
 */
public class ReflectUtils {

    public final static String SET_METHOD_PREFIX = "set";

    public final static String GET_METHOD_PREFIX = "get";


    public static String getReadOnlyMethodName(String fieldName){
        if(CheckUtils.isEmpty(fieldName)){
            return null;
        }
        fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
        return  GET_METHOD_PREFIX+fieldName;
    }

    public static String getWriteAbleMethodName(String fieldName){
        if(CheckUtils.isEmpty(fieldName)){
            return null;
        }
        fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
        return  SET_METHOD_PREFIX+fieldName;
    }

    /**
     * 获取set方法
     * @param targetClass
     * @param field
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getWriteAbleMethod(Class targetClass, Field field) throws NoSuchMethodException {
        return targetClass.getMethod(getWriteAbleMethodName(field.getName()), field.getType());
    }


    public static Method getReadOnlyMethod(Class targetClass, Field field) throws NoSuchMethodException {
        return targetClass.getMethod(getReadOnlyMethodName(field.getName()));
    }

    /**
     * 获取属性值
     * @param object
     * @param field
     * @return
     */
    public static Object getFieldValue(Object object, Field field){
        try {
            Method getMethod = getReadOnlyMethod(object.getClass(), field);
            getMethod.setAccessible(true);
            Object value = getMethod.invoke(object);
            return value;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给对象属性设置值
     * @param object
     * @param field
     * @param value
     */
    public static void setFieldValue(Object object, Field field, Object value){
        try {
            Method setMethod = getWriteAbleMethod(object.getClass(), field);
            setMethod.setAccessible(true);
            setMethod.invoke(object, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(getWriteAbleMethodName("sayHello"));
    }
}
