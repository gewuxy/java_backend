package cn.medcn.csp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/9/28.
 */
@Data
@NoArgsConstructor
public class CloseCallback extends ZeGoCallBack {

    protected int type;//关闭类型 0为正常关闭，非0为异常关闭（1为后台超时关闭，2为同一主播直播关闭之前没有关闭的流）

    protected String third_define_data;//第三方自定义数据 默认为空字符串 不超过255字节

}
