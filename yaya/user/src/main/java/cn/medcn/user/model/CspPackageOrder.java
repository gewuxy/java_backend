package cn.medcn.user.model;

import cn.medcn.common.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    //购买套餐类型，0表示1个月，1表示1年
    protected Integer packageType;

    @Transient
    protected float totalMoney;

    public enum CurrencyType{
        RMB,
        USD;
    }

    public static String getPlatFormName(String platForm){
        if(StringUtils.isEmpty(platForm)){
            return "";
        }
        if(platForm.contains("alipay")){
            return "支付宝";
        }else if(platForm.contains("wx")){
            return "微信";
        }else if(platForm.contains("upacp")){
            return "银联";
        }else if(platForm.contains("paypal")){
            return "paypal";
        }else{
            return "";
        }
    }

}
