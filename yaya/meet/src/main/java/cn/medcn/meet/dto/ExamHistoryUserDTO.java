package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Liuchangling on 2017/6/13.
 */
@Data
@NoArgsConstructor
public class ExamHistoryUserDTO {

    // 姓名
    private String nickname;

    // 考试分数
    private Integer getScore;

    // 题号
    private Integer sort;

    // 题目
    private String title;

    // 每道题分值
    private Integer score;

    // 正确答案
    private String rightkey;

    // 选择的答案
    private String answer;


}
