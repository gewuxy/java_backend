package cn.medcn.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/25.
 */
@Data
@NoArgsConstructor
public class RechargeDTO implements Serializable{

    private Integer userId;

    private Integer credits;

    private String describe;

    public String getDescribe(){
        if(StringUtils.isEmpty(describe)){
            return "您成功充值了"+this.credits+"个象数";
        }
        return describe;
    }
}
