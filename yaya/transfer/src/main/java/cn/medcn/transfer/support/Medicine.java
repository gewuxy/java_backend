package cn.medcn.transfer.support;

import java.lang.annotation.*;

/**
 * Created by lixuan on 2017/8/21.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Medicine {

    String propertyName() default "";

}
