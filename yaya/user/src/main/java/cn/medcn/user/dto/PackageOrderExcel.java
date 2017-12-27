package cn.medcn.user.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * csp套餐订单导出格式
 * Created by LiuLP on 2017/12/22/022.
 */
@Data
public class PackageOrderExcel {

    @ExcelField(columnIndex = 0,title = "订单号")
    private String tradeId;

    @ExcelField(columnIndex = 1,title = "用户昵称")
    private String nickname;

    @ExcelField(columnIndex = 2,title = "付款渠道")
    private String platForm;

    @ExcelField(columnIndex = 3,title = "购买日期")
    private String createTime;

    @ExcelField(columnIndex = 4,title = "购买套餐")
    private String packageId;

    @ExcelField(columnIndex = 5,title = "购买天数")
    private String packageType;

    @ExcelField(columnIndex = 6,title = "付款金额")
    private String money;

    @ExcelField(columnIndex = 7,title = "状态")
    private String status;

    @ExcelField(columnIndex = 8,title = "备注")
    private String remark;



}
