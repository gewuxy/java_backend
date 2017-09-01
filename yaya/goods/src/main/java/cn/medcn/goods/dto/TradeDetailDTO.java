package cn.medcn.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by LiuLP on 2017/4/25.
 */
@NoArgsConstructor
@Data
public class TradeDetailDTO {

    //象数
    private String cost;

    private Date costTime;
    //交易明细
    private String description;

    //对方用户名称
    private String oppositeName;

    //交易类型
    private Integer type = 0;

    protected String payType;

    public String getPayType(){
        return PayType.values()[type].getLabel();
    }

    public enum PayType{
        PAY(0,"支付"),
        RECHARGE(1 ,"充值"),
        ACCEPT(2, "获得");

        private Integer type;

        private String label;

        public Integer getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        PayType(Integer type, String label){
            this.type = type;
            this.label = label;
        }
    }


}
