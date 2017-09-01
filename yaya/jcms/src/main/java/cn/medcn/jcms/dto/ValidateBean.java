package cn.medcn.jcms.dto;

import cn.medcn.common.supports.Validate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lixuan on 2017/7/20.
 */
@Data
@NoArgsConstructor
public class ValidateBean {

    private String id;
    @Validate(notNull = true, notBlank = true, maxLength = 10, minLength = 3, filedName = "名称")
    private String name;
    @Validate(min = "1", max = "100", filedName = "年龄")
    private int age;
    @Validate(notNull = true, min = "2017-07-20 00:00:00", filedName = "日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
