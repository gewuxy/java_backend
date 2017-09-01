package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/6/12.
 */
@Data
@NoArgsConstructor
public class ExamHistoryDataDTO implements Serializable{
    // 题号
    private Integer sort;

    // 题目
    private String title;

    // 答题总人数
    private Integer totalCount;

    // 答对次数
    private Integer rightCount;

    // 答错次数
    private Integer errorCount;

    // 答错概率
    private String errorPercent;
}
