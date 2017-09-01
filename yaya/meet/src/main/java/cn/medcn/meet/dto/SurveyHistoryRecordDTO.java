package cn.medcn.meet.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/12.
 */
@Data
@NoArgsConstructor
public class SurveyHistoryRecordDTO implements Serializable {

    // 问卷历史记录ID
    private Integer id;

    // 用户ID
    private Integer userId;

    // 姓名
    private String nickname;

    // 医院
    private String unitName;

    // 科室
    private String subUnitName;

    // 提交问卷时间
    private Date submitTime;

}
