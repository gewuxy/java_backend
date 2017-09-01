package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/6/8.
 */
@Data
@NoArgsConstructor
public class ExamHistoryStateDTO implements Serializable {
    //得分
    private int score;
    //正确题数
    private int rightCount;
    //错误题数
    private int errorCount;
    //总题数
    private int totalCount;
}
