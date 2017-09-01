package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 用户观看PPT明细统计 excel字段
 */
@Data
public class SeePPTDetailExcelData {
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

    @ExcelField(columnIndex = 7,title = "ppt页码")
    private String pptSort;

    @ExcelField(columnIndex = 8,title = "ppt时长（秒）")
    private String pptDuration;

    @ExcelField(columnIndex = 9,title = "观看时长（秒）")
    private String watchTime;

    @ExcelField(columnIndex = 10,title = "观看总时长（时：分：秒）")
    private String watchTotalTime;

    @ExcelField(columnIndex = 11,title = "完成率")
    private String finishRate;

    public enum columnIndex{
        NAME(0,"姓名"),
        UNIT_NAME(1,"单位"),
        SUB_UNIT_NAME(2,"科室"),
        HOS_LEVEL(3,"医院级别"),
        TITLE(4,"职称"),
        PROVINE(5,"所在省市"),
        GROUP_NAME(6,"分组"),
        WATCH_TOTAL_TIME(10,"观看总时长（时：分：秒）"),
        FINISH_RATE(11,"完成率");

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
