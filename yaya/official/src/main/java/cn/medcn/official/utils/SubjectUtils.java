package cn.medcn.official.utils;

import cn.medcn.official.security.Principal;
import org.apache.shiro.SecurityUtils;

/**
 * Created by huanghuibin on 2017/11/6.
 */
public class SubjectUtils {

    public static Principal getCurrentUser(){
        return (Principal) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentAccount(){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        return principal == null?null:principal.getAccount();
    }

    public static String getCurrentUserid(){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        return principal == null?null:principal.getId();
    }
}
