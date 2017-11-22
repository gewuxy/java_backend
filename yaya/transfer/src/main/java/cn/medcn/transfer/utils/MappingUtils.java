package cn.medcn.transfer.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lixuan on 2017/6/14.
 */
public class MappingUtils {

    private static final String UNDER_LINE = "_";

    public static String humpToUnderline(String hump){
        if (hump==null||"".equals(hump.trim())){
            return "";
        }
        int len=hump.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=hump.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDER_LINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Object transferToPOJO(Map<String, Object> data, Class clazz) throws IntrospectionException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Object entity = clazz.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            Class properType = property.getPropertyType();
            //属性不是class 并且 属性的类型不是集合
            if(!"class".equals(key) && !Collection.class.isAssignableFrom(properType)){
                String underLineKey = humpToUnderline(key);
                if (data.containsKey(key) || data.containsKey(underLineKey)) {
                    Object value = data.get(key)==null?data.get(underLineKey):data.get(key);
                    Method setter = property.getWriteMethod();
                    if(properType.getName().equals("java.lang.Boolean")){
                        if(value != null){
                            Integer intValue = (Integer) value;
                            Boolean booleanValue = intValue>0?Boolean.TRUE:Boolean.FALSE;
                            setter.invoke(entity, booleanValue);
                            continue;
                        }
                    }
                    if(properType.getName().equals("java.util.Date")){
                        if(value != null){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date dateValue = format.parse(value.toString());
                                setter.invoke(entity, dateValue);
                                continue;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (properType.getName().equals("java.lang.Long")) {
                        if (value != null) {
                            setter.invoke(entity, Long.valueOf(value.toString()));
                            continue;
                        }
                    }
                    setter.invoke(entity, value);
                }
            }
        }
        return entity;
    }


    public static List<Object> transferToPOJOList(List<Map<String, Object>> datas, Class clazz) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        List<Object> list = new ArrayList<>();
        if(datas != null && datas.size() > 0){
            for(Map<String, Object> map : datas){
                list.add(transferToPOJO(map, clazz));
            }
        }
        return list;
    }


    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        Class test = ArrayList.class;
        Set<String> test2 = new HashSet<>();
        System.out.println(Collection.class.isAssignableFrom(test));
        System.out.println(test2.getClass().isAssignableFrom(Collection.class));
    }
}
