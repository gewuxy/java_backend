package cn.medcn.csp.admin.log;

import java.lang.annotation.*;

/**
 * by create HuangHuibin 2017/11/22
 */
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String name() default "";
}