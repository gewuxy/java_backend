package cn.medcn.csp.security;

import cn.medcn.user.model.Principal;

/**
 * Created by lixuan on 2017/9/18.
 */
public class SecurityUtils {

    protected static final ThreadLocal<Principal> threadLocal = new ThreadLocal<>();

    public static Principal get(){
        return threadLocal.get();
    }

    public static void set(Principal principle){
        threadLocal.set(principle);
    }
}
