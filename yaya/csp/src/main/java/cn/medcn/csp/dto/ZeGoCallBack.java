package cn.medcn.csp.dto;

import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.PropertyUtils;
import cn.medcn.csp.utils.ZeGoUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuan on 2017/9/28.
 */
@Data
@NoArgsConstructor
public class ZeGoCallBack implements Serializable {

    public static final String secret = PropertyUtils.readKeyValue("ZeGo.properties", "ZeGo.signature.secret");
    // =============公共属性=================
    protected int id;//Server端参数 流ID 自增数字 流唯一标识

    protected String channel_id;//频道ID 对应客户端ChannelID 不超过255字节

    protected String stream_alias;//流名 对应客户端StreamID 不超过255字节

    protected int timestamp;//服务器当前时间 Uinx时间戳

    protected String nonce;//随机数

    protected String signature;//检验串 见检验说明
    //==========流创建时属性=============
    protected String[] rtmp_url;//RTMP拉流地址 不超过1024字节

    protected String[] hls_url;//HLS拉流地址 不超过1024字节

    protected String[] hdl_url;//HDL拉流地址 不超过1024字节

    protected String[] pic_url;//截图地址 不超过255字节

    protected int create_time;//创建时间 Uinx时间戳

    protected int live_id;//Server端参数 直播ID 自增数字 直播唯一标识

    protected String title;//流标题 不超过255字节

    protected String publish_id;//发布者ID 客户自己系统生成的ID 不超过255字节

    protected String publish_name;//发布者名字 不超过255字节
    //===============流回放属性================
    protected int online_nums;//	Int	在线人数

    protected int player_count;//	Int	历史观看总人数

    protected String replay_url;//	String	回看地址 不超过1024字节

    protected int begin_time;//	Int	开始时间 Uinx时间戳

    protected int end_time;//	Int	结束时间 Uinx时间戳
    //====================流关闭属性=======================
    protected int type;//关闭类型 0为正常关闭，非0为异常关闭（1为后台超时关闭，2为同一主播直播关闭之前没有关闭的流）

    protected String third_define_data;//第三方自定义数据 默认为空字符串 不超过255字节
    /**
     * 即构签名认证
     * @return
     */
    public final void signature(){
        String mySignature = MD5Utils.signature(secret, this.nonce, String.valueOf(this.timestamp), MD5Utils.ENCRYPT_MODE_SHA1);
        if (!mySignature.equals(signature)) {
            throw new IllegalArgumentException("Invalid signature");
        }
    }


    public static void main(String[] args) {
        String url = "http://10.0.0.250:8081/api/user/updateInfo";
        Map<String, Object> params = new HashMap<>();
        params.put("info", "阿斯顿发打发地方阿斯顿发");
        params.put("nickName", "阿斯顿发到付");

        Map<String, Object> headers = new HashMap<>();
        headers.put("token", "e51a16c8bc9c4dcb9e849d5f78dbf636");

        HttpUtils.post(url, params, headers);
    }

}
