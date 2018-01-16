package cn.medcn.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

/**
 * Created by lixuan on 2017/4/17.
 */
public class APIUtils {

    public static final String CODE_KEY = "code";

    public static final String DATA_KEY = "data";

    public static final String MESSAGE_KEY = "err";

    public static final String SUCCESS_CODE = "0" ;

    public static final String ERROR_CODE = "-1";

    //-2代表密码错误，前端在此接口需要根据密码错误的次数弹出修改密码提示
    public static final String ERROR_PASSWORD = "-2";

    //该用户已注册
    public static final String USER_EXIST_CODE = "101";

    //该用户已绑定
    public static final String USER_BIND_CODE = "102";

    // 用户未认证错误码
    public static final String ERROR_CODE_UNAUTHED = "100";

    // 用户账号被冻结错误码
    public static final String ERROR_CODE_FROZEN = "103";

    // 用户象数不足错误码
    public static final String ERROR_CODE_NOTENOUGH_CREDITS = "201";

    // 导出excel文件出错
    public static final String ERROR_CODE_EXPORT_EXCEL = "300";

    public static String success(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_KEY,SUCCESS_CODE);
        return jsonObject.toJSONString();
    }

    public static String success(Object object){
        if (object == null){
            return success();
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CODE_KEY, SUCCESS_CODE);
            jsonObject.put(DATA_KEY,object);
            return jsonObject.toJSONString();
        }
    }

    public static String success(String msg, Object object){
        if (StringUtils.isEmpty(msg)){
            return success(object);
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CODE_KEY, SUCCESS_CODE);
            jsonObject.put(MESSAGE_KEY,msg);
            jsonObject.put(DATA_KEY,object);
            return jsonObject.toJSONString();
        }
    }

    public static String error(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_KEY,ERROR_CODE);
        return jsonObject.toJSONString();
    }

    public static String error(Object data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_KEY,ERROR_CODE);
        jsonObject.put(DATA_KEY, data);
        return jsonObject.toJSONString();
    }

    public static String error(String msg){
        if (StringUtils.isEmpty(msg)){
            return error();
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CODE_KEY,ERROR_CODE);
            jsonObject.put(MESSAGE_KEY,msg);
            return jsonObject.toJSONString();
        }
    }

    public static String error(String code, String msg){
        if(StringUtils.isEmpty(code)){
            return error(msg);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_KEY,code);
        jsonObject.put(MESSAGE_KEY,msg);
        return jsonObject.toJSONString();
    }
}
