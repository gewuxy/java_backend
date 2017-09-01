package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**考试历史对象
 * Created by LiuLP on 2017/7/20/020.
 */
@Data
public class ExamHistoryExcelData{

    @ExcelField(columnIndex = 0,title = "会议类型")
    private String meetType;

    @ExcelField(columnIndex = 1,title = "答题名称")
    private String examName;

    @ExcelField(columnIndex = 2,title = "答题时间")
    private String time;

    @ExcelField(columnIndex = 3,title = "考试分数")
    private String score;

}
