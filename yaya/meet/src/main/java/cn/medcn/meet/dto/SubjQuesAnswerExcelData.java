package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/10/13.
 * 问卷调查 主观题 用户作答数据记录
 */
@Data
public class SubjQuesAnswerExcelData {

    @ExcelField(columnIndex = 0,title = "题目")
    protected String titleName;

    @ExcelField(columnIndex = 1,title = "姓名")
    protected String name;

    @ExcelField(columnIndex = 2,title = "答案")
    protected String answer;

    public enum ColumnIndex {
        TITLE(0);

        private Integer index;

        public Integer getIndex() {
            return index;
        }
        ColumnIndex(Integer index){
            this.index = index;
        }

    }

    public enum ExcelName{
        // 导出的excel文件命名
        excel_name("主观题用户答题记录");
        private String title;
        public String getTitle(){
            return title;
        }
        ExcelName(String title) {
            this.title = title;
        }
    }
}
