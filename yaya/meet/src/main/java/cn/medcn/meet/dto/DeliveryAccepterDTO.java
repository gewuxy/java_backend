package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/28.
 */
@Data
@NoArgsConstructor
public class DeliveryAccepterDTO implements Serializable {

    protected Integer id;//单位号ID

    protected String nickname;//单位号名称

    protected String avatar;//单位号头像

    protected String info;//单位号详情
}
