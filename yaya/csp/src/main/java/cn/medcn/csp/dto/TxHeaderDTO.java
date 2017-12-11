package cn.medcn.csp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 腾讯视频直播回调公共头
 * Created by lixuan on 2017/12/11.
 */
@Data
@NoArgsConstructor
public class TxHeaderDTO implements Serializable {

    public static final String TX_HEADER_T_KEY = "t";

    public static final String TX_HEADER_SIGN_KEY = "sign";

    public static final String TX_HEADER_EVENT_TYPE_KEY = "event_type";

    public static final String TX_HEADER_STREAM_ID_KEY = "stream_id";

    public static final String TX_HEADER_CHANNEL_ID_KEY = "channel_id";

    //时间戳 十进制
    protected long t;
    //签名
    protected String sign;
    //事件类型
    protected int event_type;
    //流ID 与channel_id一致
    protected String stream_id;
    //频道ID 与stream_id一致
    protected String channel_id;

    protected TxEventType txEventType;


    /**
     * 腾讯视频直播回调事件类型
     * Created by lixuan on 2017/12/11.
     */
    public enum TxEventType {

        streamClose(0, "断流"),
        streamPush(1, "推流"),
        recordOver(100, "录制完成"),
        screenCapture(200, "截图成功");

        public int eventType;//事件类型

        public String label;//事件中文描述

        TxEventType (int eventType, String label){
            this.eventType = eventType;
            this.label = label;
        }

        public static TxEventType getCurrent(int eventType) {
            TxEventType instance = null;

            for (TxEventType et : TxEventType.values()) {
                if (et.eventType == eventType) {
                    instance = et;
                    break;
                }
            }
            return instance;
        }
    }
}
