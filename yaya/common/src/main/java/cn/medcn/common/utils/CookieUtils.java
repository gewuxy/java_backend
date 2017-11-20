package cn.medcn.common.utils;

import com.google.common.collect.Maps;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/7/25.
 */
public class CookieUtils {

    public static final long EXPIRE_TIME_ONE_DAY = TimeUnit.DAYS.toSeconds(1);

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0)  {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }


    public static void setCookie(HttpServletResponse response, String name, String value){
        setCookie(response, name, value, Long.valueOf(EXPIRE_TIME_ONE_DAY).intValue());
    }

    /**
     * 根据cookie名称获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        Cookie cookie = null;
        if(cookieMap.containsKey(name)){
            cookie = cookieMap.get(name);
        }
        return cookie;
    }

    /**
     * 获取cookie值
     * @param request
     * @param name
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name){
        String cookieValue = null;
        Cookie cookie = getCookie(request, name);
        if (cookie != null){
            cookieValue = cookie.getValue();
        }
        return cookieValue;
    }

    /**
     * 获取所有的cookie
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = Maps.newHashMap();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 清除cookie
     * @param response
     * @param key
     */
    public static void clearCookie(HttpServletResponse response, String key){
        setCookie(response, key, null, 0);
    }
}
