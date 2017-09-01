package cn.medcn.goods.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_xs_trade_detail")
public class TradeDetail implements Serializable{
    @Id
    private Integer id;

    private Integer userId;

    private Integer cost;

    private Date costTime;

    private String code;

    private String description;

    private Integer type = 0;

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
