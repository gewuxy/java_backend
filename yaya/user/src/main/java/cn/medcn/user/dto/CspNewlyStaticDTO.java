package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * csp新增用户统计
 * Created by LiuLP on 2017/12/19/019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CspNewlyStaticDTO {

    //时间粒度
    private Integer grain;

    //微博注册人数
    private Integer weiBoCount;

    //微信注册人数
    private Integer weiXinCount;

    private Integer emailCount;

    private Integer mobileCount;

    private Integer yaYaCount;

    private Integer facebookCount;

    private Integer witterCount;

    private Date startTime;

    private Date endTime;

    //x轴数据
    private String indexDate;

    //时间粒度，日，周，月
    public enum Grain{
        DAY,
        WEEK,
        MONTH,
        QUARTER,
        YEAR;
    }

}
