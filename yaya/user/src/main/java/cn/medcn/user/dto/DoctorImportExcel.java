package cn.medcn.user.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**导入医生模板对象
 * Created by LiuLP on 2017/7/20/020.
 */
@Data
public class DoctorImportExcel {

    @ExcelField(columnIndex = 0,title = "医生姓名")
    private String linkman;

    @ExcelField(columnIndex = 1,title = "单位")
    private String hospital;

    @ExcelField(columnIndex = 2,title = "医院级别(请选)")
    private String hosLevel;

    @ExcelField(columnIndex = 3,title = "科室")
    private String department;

    @ExcelField(columnIndex = 4,title = "省份")
    private String province;

    @ExcelField(columnIndex = 5,title = "城市")
    private String city;

    @ExcelField(columnIndex = 6,title = "地区")
    private String zone;

    @ExcelField(columnIndex = 7,title = "手机号")
    private String mobile;

    @ExcelField(columnIndex = 8,title = "邮箱")
    private String username;

    @ExcelField(columnIndex = 9,title = "初始密码(选择默认值)")
    private String password;




}
