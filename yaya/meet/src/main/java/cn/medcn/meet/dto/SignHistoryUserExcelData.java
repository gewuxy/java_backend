package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 用户签到记录 导出excel字段
 */
@Data
public class SignHistoryUserExcelData {
    @ExcelField(columnIndex = 0,title = "姓名")
    private String name;

    @ExcelField(columnIndex = 1,title = "医院")
    private String unitName;

    @ExcelField(columnIndex = 2,title = "科室")
    private String subUnitName;

    @ExcelField(columnIndex = 3,title = "签到时间")
    private String signTime;

    @ExcelField(columnIndex = 4,title = "是否签到成功")
    private String signState;
}
