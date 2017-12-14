package cn.medcn.csp.controller.api;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.JsonUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.dto.TxHeaderDTO;
import cn.medcn.csp.utils.TXLiveUtils;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.model.AudioCoursePlay;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.LiveService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static cn.medcn.csp.dto.TxHeaderDTO.*;

/**
 * 视频直播回调控制器
 * Created by lixuan on 2017/12/11.
 */
@Controller
@RequestMapping(value = "/api/live")
public class LiveCallbackController extends CspBaseController {

    private static Log log = LogFactory.getLog(LiveCallbackController.class);

    @Autowired
    protected LiveService liveService;

    @RequestMapping(value = "/callback/tx")
    @ResponseBody
    public String txCallback(HttpServletRequest request) {
        String requestBody = getRequestBody(request);
        TxHeaderDTO header = getTxHeader(requestBody);

        if (header != null) {
            try {
                TXLiveUtils.verify(header.getSign(), header.getT());
            } catch (SystemException e) {
                LogUtils.error(log, e.getMessage());
                return success();
            }

            switch (header.getTxEventType()) {
                case streamClose:
                    onStreamClose(requestBody);
                    break;

                case streamPush:
                    onStreamPush(requestBody);
                    break;

                case recordOver:
                    onRecordOver(requestBody);
                    break;

                case screenCapture:
                    onScreenCapture(requestBody);
                    break;

                default:
                    break;
            }
        }

        return success();
    }

    /**
     * 获取请求body内容 JSON格式
     * @param request
     * @return
     */
    protected String getRequestBody(HttpServletRequest request){

        String result = null;
        try {
            InputStream inputStream = request.getInputStream();
            InputStreamReader bis = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(bis);

            String line = reader.readLine();
            StringBuffer buffer = new StringBuffer();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取腾讯直播回调公共头信息
     *
     * @param body
     * @return
     */
    protected TxHeaderDTO getTxHeader(String body) {

        TxHeaderDTO header = null;

        if (CheckUtils.isNotEmpty(body)) {
            header = new TxHeaderDTO();
            header.setChannel_id((String) JsonUtils.getValue(body, TX_HEADER_CHANNEL_ID_KEY));
            header.setEvent_type((Integer) JsonUtils.getValue(body, TX_HEADER_EVENT_TYPE_KEY));
            header.setSign((String) JsonUtils.getValue(body, TX_HEADER_SIGN_KEY));
            header.setT((Integer) JsonUtils.getValue(body, TX_HEADER_T_KEY));
            header.setStream_id((String) JsonUtils.getValue(body, TX_HEADER_STREAM_ID_KEY));

            if (JsonUtils.getValue(body, TX_BODY_ERROR_CODE_KEY) != null) {
                header.setErrcode((Integer) JsonUtils.getValue(body, TX_BODY_ERROR_CODE_KEY));
            }

            header.setTxEventType(TxEventType.getCurrent(header.getEvent_type()));
        }

        return header;
    }


    /**
     * 处理断流时的回调
     *
     * @param requestBody
     */
    protected void onStreamClose(String requestBody) {
        LogUtils.debug(log, "接收到推流断开通知");
        TxHeaderDTO header = getTxHeader(requestBody);

        if (header != null) {
            Integer courseId = parseCourseId(header.getChannel_id());
            if (courseId != null) {
                Live live = liveService.findByCourseId(courseId);

                if (live != null) {
                    live.setLiveState(AudioCoursePlay.PlayState.pause.ordinal());
                    live.setCloseType(header.getErrcode());//设置断流的错误码
                    liveService.updateByPrimaryKey(live);
                }
            }

        }
    }

    /**
     * 处理推流时的回调
     *
     * @param requestBody
     */
    protected void onStreamPush(String requestBody) {

        LogUtils.debug(log, "接收到推流通知");
        TxHeaderDTO header = getTxHeader(requestBody);

        if (header != null) {
            Integer courseId = parseCourseId(header.getChannel_id());

            //收到推流通知的时候 发送推流指令
            LiveOrderDTO order = new LiveOrderDTO();
            order.setOrder(LiveOrderDTO.ORDER_LIVE_STREAM_PUSH);
            order.setCourseId(String.valueOf(courseId));
            liveService.publish(order);

            if (courseId != null) {
                Live live = liveService.findByCourseId(courseId);

                if (live != null) {
                    live.setLiveState(AudioCoursePlay.PlayState.playing.ordinal());
                    liveService.updateByPrimaryKey(live);
                }
            }
        }
    }


    protected Integer parseCourseId(String channelId) {
        if (CheckUtils.isNotEmpty(channelId)) {
            String[] array = channelId.split("_");
            if (!CheckUtils.isEmpty(array) && array.length > 1) {
                return Integer.valueOf(array[1]);
            }
        }
        return null;
    }

    /**
     * 处理直播录制完成的回调
     *
     * @param requestBody
     */
    protected void onRecordOver(String requestBody) {

        // todo 处理视频下载
    }

    /**
     * 处理直播截图完成的回调
     *
     * @param requestBody
     */
    protected void onScreenCapture(String requestBody) {
        // todo 暂时未做处理
    }


}
