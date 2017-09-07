package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/5/18.
 * 医生统计 属性分布数据分析
 */
@Data
@NoArgsConstructor
public class UserDataDetailDTO implements Serializable {

    // 属性编号 （地区：1, 医院：2, 职称：3, 科室：4）
    private Integer propNum;

    // 属性名称 （地区=广州、医院=等级、职称=医师、科室=儿科）
    private String propName;

    // 用户数
    private Integer userCount;

    // 占比
    private Float percent;


    public interface conditionNumber {
        Integer REGION = 1;     // 地区
        Integer HOSPITAL = 2;   // 医院
        Integer USER_TITLE = 3; // 职称
        Integer DEPARTMENT = 4; // 科室
        String  DEFAULT_PROVINCE = "全国";
    }

}
