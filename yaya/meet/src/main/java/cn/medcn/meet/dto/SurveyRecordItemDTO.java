package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/6/15.
 */
@Data
@NoArgsConstructor
public class SurveyRecordItemDTO implements Serializable {
    // 选项
    private String optkey;

    // 选项内容
    private String option;

    // 用户选择的答案选项
    private String selAnswer;

    // 选择的人数
    private Integer selCount;

    // 用户姓名
    private String nickname;


}
