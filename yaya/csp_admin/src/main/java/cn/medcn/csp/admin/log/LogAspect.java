package cn.medcn.csp.admin.log;

import cn.medcn.csp.admin.security.Principal;
import cn.medcn.csp.admin.service.CspSysLogService;
import cn.medcn.csp.admin.utils.SubjectUtils;
import cn.medcn.csp.admin.model.CspSysLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public void beforeController(){
        logger.info("-------------记录操作日志");
        Principal principal = SubjectUtils.getCurrentUser();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();        try {
            if(principal!=null){
                CspSysLog log=new CspSysLog();
                log.setUserId(principal.getId());
                log.setAccount(principal.getAccount());
                log.setUserName(principal.getAccount());
                log.setLogDate(new Date());
                log.setAction(request.getServletPath());
                cspSysLogService.insert(log);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("-------------记录日志出错", e);
        }
    }
}
