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

    //订单tradeId
    private String id;

    private String nickname;

    //支付渠道
    private String platForm;


    private Integer packageId;


    private float money;

    //交易狀態
    private  Integer status;

    private String remark;

    //购买时间
    private Date createTime;

    //购买套餐类型，0表示月，1表示年
    private Integer packageType;

    private Integer abroad;

    //购买的数值,购买num个月或者num年
    private Integer num;






}
