package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/6/13.
 */
@Data
@NoArgsConstructor
public class SurveyHistoryUserDTO implements Serializable{

    // 题目序号
    private Integer sort;

    // 用户ID
    private Integer userId;

    // 题目ID
    private Integer questionId;

    // 选择答案
    private String answer;
}
