package cn.medcn.csp.admin.log;

import cn.medcn.csp.admin.security.Principal;
import cn.medcn.csp.admin.service.CspSysLogService;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.csp.admin.model.CspSysLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * by create HuangHuibin 2017/11/7
 */
@Aspect
public class LogAspect {

    private static final Logger logger =Logger.getLogger(LogAspect.class);

    @Autowired
    private CspSysLogService cspSysLogService;

    /**
     * 扫描需要加日志的类
     */
    @Pointcut("execution(* cn.medcn.csp.admin.controllor..*.*(..))")
    public void controllerPointcut() {

    }

    /**
     * 进入controller前先记录日志
     */
    @Before("controllerPointcut()")
    @AfterThrowing
    public void beforeController(){
        Principal principal = SubjectUtils.getCurrentUser();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();        try {
            if(principal!=null){
                CspSysLog log=new CspSysLog();
                log.setUserId(principal.getId());
                log.setAccount(principal.getAccount());
                log.setUserName(principal.getUsername());
                log.setLogDate(new Date());
                log.setAction(request.getServletPath());
                cspSysLogService.insert(log);
            }
        } catch (Exception e) {
            logger.error("-------------记录日志出错", e);
        }
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String params = "";
        try {
            logger.info("=====异常通知开始=====");
            logger.info("异常代码:" + e.getClass().getName());
            logger.info("异常信息:" + e.getMessage());
            logger.info("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.info("请求人:" +   SubjectUtils.getCurrentAccount() );
            logger.info("请求IP:" + ip);
            logger.info("请求参数:" + params);
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("-------异常", ex);
        }
    }
}
