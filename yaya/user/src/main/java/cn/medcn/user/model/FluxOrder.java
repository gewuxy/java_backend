package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/** 流量购买记录
 * Created by LiuLP on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_user_flux_purchase")
public class FluxOrder implements Serializable{
    @Id
    protected Integer id;

    protected String userId;

    protected Date buyTime;

    //购买是否到账，0代表未到账，1代表已到账
    protected Integer state;

    //生效时间
    protected Date effectTime;

    //订单号
    protected String tradeId;

    //流量值
    protected Integer flux;

    //支付渠道
    protected String platform;

}
