package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 问卷调查数据分析 导出excel字段
 */
@Data
public class SurveyExcelData {
    @ExcelField(columnIndex = 0,title = "序号")
    private String sort;

    @ExcelField(columnIndex = 1,title = "题目")
    private String questionTitle;

    @ExcelField(columnIndex = 2,title = "选项/主观题用户答案")
    private String questionOption;

    @ExcelField(columnIndex = 3,title = "选择人数/用户姓名")
    private String selectionCount;

    @ExcelField(columnIndex = 4,title = "选择率")
    private String selectance;

    public enum columnIndex{
        SORT(0,"序号"),
        QUESTION_TITLE(1,"题目");

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
