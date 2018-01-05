package cn.medcn.user.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * by create HuangHuibin 2018/1/4
 */
@Data
public class PackageRenewExcel {

    @ExcelField(columnIndex = 0,title = "时间")
    private String createTime;

    @ExcelField(columnIndex = 1,title = "高级版")
    private String  preEdition;

    @ExcelField(columnIndex = 2,title = "专业版")
    private String proEdition;
}
