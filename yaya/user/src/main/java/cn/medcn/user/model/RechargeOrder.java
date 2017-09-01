package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by LiuLP on 2017/4/27.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_xs_recharge_order")
public class RechargeOrder {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer userId;

    private String orderNo;

    private Date createTime;

    //订单状态  0 表示未付款 1表示已付款
    private Integer status;

    private Float price;

    private Date updateTime;

    //支付宝交易凭证号
    private String tradeNo;

    public void modifyPayed(){
        this.status = 1;
    }
}
