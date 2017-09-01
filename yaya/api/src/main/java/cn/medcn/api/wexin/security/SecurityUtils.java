package cn.medcn.api.wexin.security;

/**
 * Created by lixuan on 2017/7/28.
 */
public class SecurityUtils {

    private static ThreadLocal<Principal> threadLocal = new ThreadLocal<>();

    /**
     * 在任意位置获取当前session中的用户的信息
     * @return
     */
    public static Principal getCurrentUserInfo(){
        Principal principal = threadLocal.get();
        return principal;
    }

    /**
     * 将session中的用户信息放到ThreadLocal中
     * @param principal
     */
    public static void setUserInfo(Principal principal){
        threadLocal.set(principal);
    }
}
