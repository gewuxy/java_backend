package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Liuchangling on 2017/7/14.
 */
@Data
@NoArgsConstructor
public class QuestionOptItemDTO {

    // 用户ID
    private Integer id;
    // 题目ID
    private Integer questionId;
    // 题号
    private Integer sort;
    // 用户选择答案
    private String selAnswer;


}
