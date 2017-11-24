package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    protected String id;

    protected String userId;

    protected Date buyTime;

    //购买是否到账，0代表未到账，1代表已到账 ，2代表已关闭订单
    protected Integer state;

    //生效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date effectTime;

    //订单号
    protected String tradeId;

    //流量值
    protected Integer flux;

    //支付渠道
    protected String platform;

    //用户名
    @Transient
    protected String username;

    @Transient
    protected Integer expense;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date expenseTime;//最后记录时间

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date expireTime;

    //流量值
    @Transient
    protected Integer fluxTotal;

    @Transient
    protected String meetName;

}
