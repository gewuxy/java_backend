package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/12/8.
 * csp用户套餐订单表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_package_order")
public class CspPackageOrder implements Serializable {
    @Id
    protected String id;
    // 用户id
    protected String userId;
    // 套餐id
    protected Integer packageId;
    //套餐数量
    protected Integer num;
    // 币种  0:RMB 1:USD
    protected Integer currencyType;
    // 应该支付金额
    protected Float shouldPay;
    // 实际支付金额
    protected Float payMoney;
    // 创建时间
    protected Date createTime;
    // 支付状态 0代表未到账，1代表已到账，2已退款
    protected Integer state;
    // 第三方交易ID
    protected String tradeId;
    // 第三方交易平台
    protected String platForm;
    // 备注
    protected String remark;

    public enum CurrencyType{
        RMB,
        USD;
    }



}
