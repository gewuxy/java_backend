package cn.medcn.user.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * csp用户注册数量导出格式
 * Created by LiuLP on 2017/12/22/022.
 */
@Data
public class CspUserRegisterExcel {

    @ExcelField(columnIndex = 0,title = "时间")
    private String time;

    @ExcelField(columnIndex = 1,title = "合计(人)")
    private Integer sum;

    @ExcelField(columnIndex = 2,title = "手机")
    private Integer mobile;

    @ExcelField(columnIndex = 3,title = "邮箱")
    private Integer email;

    @ExcelField(columnIndex = 4,title = "微信")
    private Integer weiXin;

    @ExcelField(columnIndex = 5,title = "微博")
    private Integer weiBo;

    @ExcelField(columnIndex = 6,title = "YaYa数字平台")
    private Integer yaYa;

    @ExcelField(columnIndex = 7,title = "facebook")
    private Integer facebook;

    @ExcelField(columnIndex = 8,title = "twitter")
    private Integer twitter;



}
