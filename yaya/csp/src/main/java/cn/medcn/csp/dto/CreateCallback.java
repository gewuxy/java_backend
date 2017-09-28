package cn.medcn.csp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/9/28.
 */
@Data
@NoArgsConstructor
public class CreateCallback extends ZeGoCallBack{

    protected int live_id;//Server端参数 直播ID 自增数字 直播唯一标识

    protected String title;//标题 不超过255字节

    protected String publish_id;//发布者ID 客户自己系统生成的ID 不超过255字节

    protected String publish_name;//发布者名字 不超过255字节

    protected String[] rtmp_url;//RTMP拉流地址 不超过1024字节

    protected String[] hls_url;//HLS拉流地址 不超过1024字节

    protected String[] hdl_url;//HDL拉流地址 不超过1024字节

    protected String[] pic_url;//截图地址 不超过255字节

    protected int create_time;//创建时间 Uinx时间戳
}
