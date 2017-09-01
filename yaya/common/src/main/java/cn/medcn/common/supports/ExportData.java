package cn.medcn.common.supports;

import lombok.Data;

/**
 * 此类作为仅作为测试用
 * 实际情况中需按自己的需求来定义
 * Created by lixuan on 2017/7/19.
 */
@Data
public class ExportData {
    @ExcelField(columnIndex = 0, title = "姓名")
    private String username;

    @ExcelField(columnIndex = 1, title = "单位")
    private String unit;

    @ExcelField(columnIndex = 2, title = "科室")
    private String depart;

    @ExcelField(columnIndex = 3, title = "医院级别")
    private String hospitalLevel;

    @ExcelField(columnIndex = 4, title = "职称")
    private String title;

    @ExcelField(columnIndex = 5, title = "所属省份")
    private String province;

    @ExcelField(columnIndex = 6, title = "分组")
    private String group;

    @ExcelField(columnIndex = 7, title = "ppt页码数")
    private String pageNum;

    @ExcelField(columnIndex = 8, title = "ppt总时长")
    private String sourceSeconds;

    @ExcelField(columnIndex = 9, title = "学习时长")
    private String studySeconds;

    @ExcelField(columnIndex = 10, title = "学习总时长")
    private String totalStudySeconds;

    @ExcelField(columnIndex = 11, title = "完成率")
    private String finishRate;
}
