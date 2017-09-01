package cn.medcn.weixin.dto;

import lombok.Data;
/**
 *  微信认证签名参数
 *  Created by lixuan on 2017/7/21.
 */
@Data
public class SignatureDTO {
    //密码签名
    private String signature;
    //时间戳
    private String timestamp;
    //随机数
    private String nonce;
    //随机字符串
    private String echostr;
}
