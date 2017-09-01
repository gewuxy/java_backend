package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**问卷历史对象
 * Created by LiuLP on 2017/7/20/020.
 */
@Data
public class SurveyHistoryExcelData {

    @ExcelField(columnIndex = 0,title = "会议类型")
    private String meetType;

    @ExcelField(columnIndex = 1,title = "问卷名称")
    private String surveyName;

    @ExcelField(columnIndex = 2,title = "问卷时间")
    private String time;



}
