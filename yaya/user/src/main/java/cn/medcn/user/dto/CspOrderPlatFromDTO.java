package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 各渠道数据
 *
 * by create HuangHuibin 2017/12/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CspOrderPlatFromDTO {

    //注册时间
    private Date createTime;

    //web端支付宝支付
    private float alipayWap;

    //web端微信支付
    private float wxPubQr;

    //web端银联支付
    private float upacpWap;

    //paypal端银联支付
    private float paypal;

    //单日总额
    private float money;

    //总额
    private float totalMoney;
}
