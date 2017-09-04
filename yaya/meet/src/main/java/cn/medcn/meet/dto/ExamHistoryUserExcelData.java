package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import cn.medcn.common.utils.CheckUtils;
import lombok.Data;

import java.util.List;

/**
 * Created by Liuchangling on 2017/7/21.
 * 参与考试的用户情况 导出excel字段
 */
@Data
public class ExamHistoryUserExcelData {
    @ExcelField(columnIndex = 0,title = "姓名")
    private String name;

    @ExcelField(columnIndex = 1,title = "是否参与")
    private String participation;

    @ExcelField(columnIndex = 2,title = "单位")
    private String unitName;

    @ExcelField(columnIndex = 3,title = "科室")
    private String subUnitName;

    @ExcelField(columnIndex = 4,title = "医院级别")
    private String hosLevel;

    @ExcelField(columnIndex = 5,title = "职称")
    private String title;

    @ExcelField(columnIndex = 6,title = "所在省市区")
    private String province;

    @ExcelField(columnIndex = 7,title = "所在分组")
    private String groupName;

    @ExcelField(columnIndex = 8,title = "考试次数")
    private String examTimes;

    @ExcelField(columnIndex = 9,title = "提交考题时间")
    private String submitTime;

    @ExcelField(columnIndex = 10,title = "成绩")
    private String score;

    //    考试次数	第一题	第二题	第三题	...第N题	主观题：XXXXXX	提交问卷时间	成绩
    @ExcelField(columnIndex = 11, title = "第%d题")
    private List answerList;



}
