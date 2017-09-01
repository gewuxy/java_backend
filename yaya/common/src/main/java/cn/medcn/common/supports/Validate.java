package cn.medcn.common.supports;

import java.lang.annotation.*;

/**
 * 数据校验注解 一般用于插入数据或者修改数据
 * Created by lixuan on 2017/7/20.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {

    String filedName() default "属性";
    //不能为空
    boolean notNull() default false;

    //不能为空字符串
    boolean notBlank() default false;

    //最小值 Number和Date生效
    //date格式 yyyy-MM-dd HH:mm:ss
    String min() default "0";

    //最大值 Number和Date生效
    //date格式 yyyy-MM-dd HH:mm:ss
    String max() default Long.MAX_VALUE+"";

    //最小长度 字符串生效
    int minLength() default 0;

    //最大长度 字符串生效
    int maxLength() default Integer.MAX_VALUE;

    //需要符合正则表达式 字符串生效
    String pattern() default "";
}
