package cn.medcn.csp.dto;

import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.PropertyUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/28.
 */
@Data
@NoArgsConstructor
public class ZeGoCallBack implements Serializable {

    public static final String appKey = PropertyUtils.readKeyValue("ZeGo.properties", "ZeGo.appKey");

    protected int id;//Server端参数 流ID 自增数字 流唯一标识

    protected String channel_id;//频道ID 对应客户端ChannelID 不超过255字节

    protected String stream_alias;//流名 对应客户端StreamID 不超过255字节

    protected int timestamp;//服务器当前时间 Uinx时间戳

    protected String nonce;//随机数

    protected String signature;//检验串 见检验说明

    /**
     * 即构签名认证
     * @return
     */
    public final void signature(){
        String mySignature = MD5Utils.signature(appKey, this.nonce, String.valueOf(this.timestamp));
        if (!mySignature.equals(signature)) {
            throw new IllegalArgumentException("Invalid signature");
        }
    }
}
