package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**会议历史对象
 * Created by LiuLP on 2017/7/20/020.
 */
@Data
public class MeetHistoryExcelData{

    @ExcelField(columnIndex = 0,title = "会议类型")
    private String meetType;

    @ExcelField(columnIndex = 1,title = "会议名称")
    private String meetName;

    @ExcelField(columnIndex = 2,title = "开场时间")
    private String publishTime;

    @ExcelField(columnIndex = 3,title = "学习时长")
    private String learnTime;

}
