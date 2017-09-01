package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;


/**
 * Created by Liuchangling on 2017/8/10.
 * 参会用户项目完成率统计
 */
@Data
public class AttendMeetUserExcelData {

    @ExcelField(columnIndex = 0,title = "参与日期")
    private String attendTime;

    @ExcelField(columnIndex = 1,title = "学习记录")
    private String learnRecord;

    @ExcelField(columnIndex = 2,title = "姓名")
    private String nickName;

    @ExcelField(columnIndex = 3,title = "医院")
    private String unitName;

    @ExcelField(columnIndex = 4,title = "科室")
    private String subUnitName;

    @ExcelField(columnIndex = 5,title = "分组")
    private String groupName;

    @ExcelField(columnIndex = 6,title = "手机")
    private String mobile;

    @ExcelField(columnIndex = 7,title = "邮箱")
    private String email;

    @ExcelField(columnIndex = 8,title = "CME卡号")
    private String cmeId;

    @ExcelField(columnIndex = 9,title = "内容统计")
    private String pptPercent;

    @ExcelField(columnIndex = 10,title = "观看PPT时长")
    private String viewPPTTime;

    @ExcelField(columnIndex = 11,title = "考试统计")
    private String examPercent;

    @ExcelField(columnIndex = 12,title = "提交考试时间")
    private String examTime;

    @ExcelField(columnIndex = 13,title = "考试分数")
    private String score;

    @ExcelField(columnIndex = 14,title = "问卷统计")
    private String surveyPercent;

    @ExcelField(columnIndex = 15,title = "完成时间")
    private String surveyTime;

    @ExcelField(columnIndex = 16,title = "签到统计")
    private String signPercent;

    @ExcelField(columnIndex = 17,title = "签到时间")
    private String signTime;

    @ExcelField(columnIndex = 18,title = "是否签到成功")
    private String signFlag;

    // 观看视频百分比
    @ExcelField(columnIndex = 19,title = "观看视频统计")
    private String watchVideoPercent;

    // 观看视频时长
    @ExcelField(columnIndex = 20,title = "观看视频时长")
    private String watchVideoTime;

    @ExcelField(columnIndex = 21,title = "视频总时长")
    private String videoTotalTime;
}
