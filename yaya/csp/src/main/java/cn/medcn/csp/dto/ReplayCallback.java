package cn.medcn.csp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/9/28.
 */
@Data
@NoArgsConstructor
public class ReplayCallback extends ZeGoCallBack {

    protected int live_id;//Server端参数 直播ID 自增数字 直播唯一标识

    protected String title;//流标题 不超过255字节

    protected String publish_id;//发布者ID 客户自己系统生成的ID 不超过255字节

    protected String publish_name;//发布者名字 不超过255字节

    protected int online_nums;//	Int	在线人数

    protected int player_count;//	Int	历史观看总人数

    protected String replay_url;//	String	回看地址 不超过1024字节

    protected int begin_time;//	Int	开始时间 Uinx时间戳

    protected int end_time;//	Int	结束时间 Uinx时间戳
}
