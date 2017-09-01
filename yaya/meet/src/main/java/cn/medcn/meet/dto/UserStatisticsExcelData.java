package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 用户统计 导出excel字段
 */

@Data
public class UserStatisticsExcelData {
    @ExcelField(columnIndex = 0,title = "姓名")
    private String name;

    @ExcelField(columnIndex = 1,title = "单位")
    private String unitName;

    @ExcelField(columnIndex = 2,title = "科室")
    private String subUnitName;

    @ExcelField(columnIndex = 3,title = "医院级别")
    private String hosLevel;

    @ExcelField(columnIndex = 4,title = "职称")
    private String title;

    @ExcelField(columnIndex = 5,title = "所在省市")
    private String province;

    @ExcelField(columnIndex = 6,title = "分组")
    private String groupName;

    @ExcelField(columnIndex = 7,title = "进入会议时间")
    private String startTime;

    @ExcelField(columnIndex = 8,title = "退出会议时间")
    private String endTime;

    @ExcelField(columnIndex = 9,title = "总学时（时：分：秒）")
    private String totalHours;

    @ExcelField(columnIndex = 10,title = "CME卡号")
    private String cmeId;

    @ExcelField(columnIndex = 11,title = "学习记录")
    private String learnRecord;

    public enum columnIndex{
        NAME(0,"姓名"),
        UNIT_NAME(1,"单位"),
        SUB_UNIT_NAME(2,"科室"),
        HOS_LEVEL(3,"医院级别"),
        TITLE(4,"职称"),
        PROVINCE(5,"所在省市"),
        GROUP_NAME(6,"分组"),
        START_TIME(7,"进入会议时间"),
        END_TIME(8,"退出会议时间"),
        TOTAL_HOURS(9,"总学时（时：分：秒）"),
        CME_ID(10,"CME卡号"),
        LEARN_RECORD(11,"学习记录");

        private Integer columnIndex;
        private String columnName;

        public Integer getColumnIndex() {
            return columnIndex;
        }

        public String getColumnName() {
            return columnName;
        }

        columnIndex(Integer columnIndex,String columnName){
            this.columnIndex = columnIndex;
            this.columnName = columnName;
        }
    }

}
