package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 考试数据分析 导出excel字段
 */
@Data
public class ExamExcelData {
    @ExcelField(columnIndex = 0,title = "题号")
    private String sort;

    @ExcelField(columnIndex = 1,title = "题目")
    private String questionTitle;

    @ExcelField(columnIndex = 2,title = "答对题目人数")
    private String rightNumber;

    @ExcelField(columnIndex = 3,title = "答错题目人数")
    private String wrongNumber;

    @ExcelField(columnIndex = 4,title = "错误率")
    private String errorRate;

}
