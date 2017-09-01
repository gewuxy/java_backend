package cn.medcn.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
public class CreditPayDTO implements Serializable{
    /**支付者ID*/
    private Integer payer;
    /**接收者ID*/
    private Integer accepter;
    /**支付象数*/
    private Integer credits;
    /**支付者明细描述*/
    private String payerDescrib;
    /**接收者明细描述*/
    private String accepterDescrib;
}
