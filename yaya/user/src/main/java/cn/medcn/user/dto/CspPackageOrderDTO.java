package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 套餐订单dto
 * Created by LiuLP on 2017/12/21/021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CspPackageOrderDTO {

    //订单id
    private String id;

    private String nickname;

    //支付渠道
    private Integer platForm;

    private Date buyDate;

    private Integer packageId;

    //套餐有效时间，只有1个月和1年
    private Integer packageValidType;

    private float money;

    //交易狀態
    private  Integer status;

    private String remark;






}
