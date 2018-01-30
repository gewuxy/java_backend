package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;


/**
 * 投稿历史导出
 * by create HuangHuibin 2018/1/16
 */
@Data
public class ExamHistoryExcel {

    @ExcelField(columnIndex = 0,title = "课件名称")
    private String title;

    @ExcelField(columnIndex = 1,title = "分类")
    private String category;

    @ExcelField(columnIndex = 2,title = "讲者名称")
    private String name;

    @ExcelField(columnIndex = 3,title = "讲者手机")
    private String mobile;

    @ExcelField(columnIndex = 4,title = "讲者邮箱")
    private String email;

    @ExcelField(columnIndex = 5,title = "综合评分")
    private String score;

    @ExcelField(columnIndex = 6,title = "评分人数")
    private String num;

    @ExcelField(columnIndex = 7,title = "投稿时间")
    private String deliveryTime;
}
