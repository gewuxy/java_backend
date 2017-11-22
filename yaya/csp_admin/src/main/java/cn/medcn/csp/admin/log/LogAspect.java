package cn.medcn.csp.admin.log;

import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.model.CspSysLog;
import cn.medcn.csp.admin.security.Principal;
import cn.medcn.csp.admin.service.CspSysLogService;
import cn.medcn.csp.admin.utils.SubjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;


/**
 * by create HuangHuibin 2017/11/7
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger =Logger.getLogger(LogAspect.class);

    @Autowired
    private CspSysLogService cspSysLogService;

    /**
     * 配置Log切面
     */
    @Pointcut("@annotation(cn.medcn.csp.admin.log.Log)")
    public void controllerPointcut() {

    }

    /**
     * 需要记录的action
     * @param joinPoint
     */
    @Before("controllerPointcut()")
    public void beforeController(JoinPoint joinPoint){
        Principal principal = SubjectUtils.getCurrentUser();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            String action = this.getParameters(request);
            String actionName = getControllerMethodDescription(joinPoint);
            if(principal!=null){
                CspSysLog log=new CspSysLog();
                log.setUserId(principal.getId());
                log.setAccount(principal.getAccount());
                log.setUserName(principal.getUsername());
                log.setLogDate(new Date());
                log.setAction(action.length() < 50 ? action : action.substring(0,50));
                log.setActionName(actionName);
                cspSysLogService.insert(log);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("-------------记录日志出错", e);
        }

    }

    /**
     * 异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        Principal principal = SubjectUtils.getCurrentUser();
        String params = this.getParameters(request);
        try {
            logger.info("=====异常通知开始=====");
            logger.info("异常代码:" + e.getClass().getName());
            logger.info("异常信息:" + e.getMessage());
            logger.info("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.info("方法描述:" + getControllerMethodDescription(joinPoint));
            logger.info("请求人:" +  principal.getAccount());
            logger.info("请求IP:" + ip);
            logger.info("请求参数:" + params);
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("-------异常", ex);
        }
    }

    @SuppressWarnings("rawtypes")
    public  String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(Log.class).name();
                    break;
                }
            }
        }
        return description;
    }

    @SuppressWarnings("unchecked")
    public  String   getParameters(HttpServletRequest request) {
        String requestQuery=request.getQueryString();
        StringBuffer buffer=new StringBuffer();
        if(StringUtils.isNotEmpty(requestQuery)){
            String paramStr = null;
            try {
                paramStr = URLDecoder.decode(requestQuery,"UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            buffer.append(request.getRequestURI()+"?"+paramStr);
        }else {
            Enumeration<String> paramNames= request.getParameterNames();
            buffer.append(request.getRequestURI()+"?");
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                buffer.append(paramName+"="+request.getParameter(paramName)+"&");
            }
        }
        return buffer.toString();
    }
}
