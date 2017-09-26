package cn.medcn.csp.security;

/**
 * Created by lixuan on 2017/9/18.
 */
public class SecurityUtils {

    protected static final ThreadLocal<Principle> threadLocal = new ThreadLocal<>();

    public static Principle get(){
        return threadLocal.get();
    }

    public static void set(Principle principle){
        threadLocal.set(principle);
    }
}
