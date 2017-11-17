package cn.medcn.csp.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author：jianliang
 * @Date: Creat in 11:22 2017/11/15
 */
@NoArgsConstructor
@Data
public class FluxOrderDTO{

    protected String id;

    protected String userId;

    protected Date buyTime;

    //购买是否到账，0代表未到账，1代表已到账
    protected Integer state;

    //生效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date effectTime;

    //订单号
    protected String tradeId;

    //流量值
    protected Integer flux;

    //支付渠道
    protected String platform;

    //用户名
    protected String username;

    protected Integer expense;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date expenseTime;//最后记录时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date expireTime;

    //流量值
    protected Integer fluxTotal;

    protected String meetName;


    /*public String getId() {
        return id;
    }*/
}
