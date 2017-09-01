package cn.medcn.jcms.utils;

import cn.medcn.jcms.security.Principal;
import org.apache.shiro.SecurityUtils;

/**
 * Created by lixuan on 2017/5/3.
 */
public class SubjectUtils {

    public static Principal getCurrentUser(){
        return (Principal) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentUsername(){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        return principal == null?null:principal.getUsername();
    }

    public static Integer getCurrentUserid(){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        return principal == null?null:principal.getId();
    }
}
