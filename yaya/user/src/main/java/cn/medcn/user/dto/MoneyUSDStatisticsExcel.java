package cn.medcn.user.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * 用于到处统计数据
 * by create HuangHuibin 2018/1/4
 */
@Data
public class MoneyUSDStatisticsExcel {

    @ExcelField(columnIndex = 0,title = "时间")
    private String createTime;

    @ExcelField(columnIndex = 1,title = "paypal")
    private String paypal;

    @ExcelField(columnIndex = 2,title = "付款总额")
    private String sum;
}
