package cn.medcn.common.utils;

import cn.medcn.common.excptions.SystemException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by lixuan on 2017/1/3.
 */
public class JsonUtils {


    protected static final String JSON_ERROR = "JSON解析错误";

    /**
     * 根据json字符串和key 获取对应的value
     *
     * @param jsonString
     * @param key
     * @return
     */
    public static Object getValue(String jsonString, String key) {
        Assert.notNull(jsonString);
        Assert.notNull(key);
        return JSON.parseObject(jsonString).get(key);
    }

    /**
     * 解析单个对象
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     * @throws SystemException
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) throws SystemException {
        try {
            T obj = JSON.parseObject(jsonStr, clazz);
            return obj;
        } catch (JSONException e) {
            throw new SystemException(JSON_ERROR);
        }
    }

    /**
     * 解析结合
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     * @throws SystemException
     */
    public static <T> List<T> parseList(String jsonStr, Class<T> clazz) throws SystemException {
        try {
            List<T> list = JSON.parseArray(jsonStr, clazz);
            return list;
        } catch (JSONException e) {
            throw new SystemException(JSON_ERROR);
        }

    }
}
