package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

import java.util.List;

/**
 * Created by Liuchangling on 2017/7/21.
 * 参与问卷用户情况统计 导出excel字段
 */
@Data
public class SurveyHistoryUserExcelData {
    //  姓名	是否参与	单位	科室	医院级别	职称	所在省市	分组	第一题	第二题	第三题	...第N题	主观题：XXXXXX	提交问卷时间
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

    @ExcelField(columnIndex = 6,title = "所在省市")
    private String province;

    @ExcelField(columnIndex = 7,title = "分组")
    private String groupName;

    @ExcelField(columnIndex = 8,title = "提交问卷时间")
    private String submitTime;

    @ExcelField(columnIndex = 9,title = "第%d题")
    private List answerList;


}
