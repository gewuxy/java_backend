package cn.medcn.csp.controller.api;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.dto.TxHeaderDTO;
import cn.medcn.csp.utils.TXLiveUtils;
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

import static cn.medcn.csp.CspConstants.TX_LIVE_API_TIME_OUT;
import static cn.medcn.csp.dto.TxHeaderDTO.*;

/**
 * 视频直播回调控制器
 * Created by lixuan on 2017/12/11.
 */
@Controller
@RequestMapping(value = "/api/live")
public class LiveCallbackController extends CspBaseController{

    private static Log log = LogFactory.getLog(LiveCallbackController.class);


    @Autowired
    protected LiveService liveService;

    @RequestMapping(value = "/callback/tx")
    @ResponseBody
    public String txCallback(HttpServletRequest request){

        TxHeaderDTO header = getTxHeader(request);

        try {
            TXLiveUtils.verify(header.getSign(), header.getT());
        } catch (SystemException e) {
            LogUtils.error(log, e.getMessage());
            return success();
        }

        switch (header.getTxEventType()) {
            case streamClose:
                onStreamClose(header, request);
                break;

            case streamPush:
                onStreamPush(header, request);
                break;

            case recordOver:
                onRecordOver(header, request);
                break;

            case screenCapture:
                onScreenCapture(header, request);
                break;

            default: break;
        }

        return success();
    }

    /**
     * 获取腾讯直播回调公共头信息
     * @param request
     * @return
     */
    protected TxHeaderDTO getTxHeader(HttpServletRequest request){
        TxHeaderDTO header = new TxHeaderDTO();
        header.setChannel_id(request.getParameter(TX_HEADER_CHANNEL_ID_KEY));
        header.setEvent_type(Integer.valueOf(request.getParameter(TX_HEADER_EVENT_TYPE_KEY)));
        header.setSign(request.getParameter(TX_HEADER_SIGN_KEY));
        header.setT(Long.valueOf(request.getParameter(TX_HEADER_T_KEY)));
        header.setStream_id(request.getParameter(TX_HEADER_STREAM_ID_KEY));

        header.setTxEventType(TxEventType.getCurrent(header.getEvent_type()));

        return header;
    }




    /**
     * 处理断流时的回调
     * @param header
     * @param request
     */
    protected void onStreamClose(TxHeaderDTO header, HttpServletRequest request){
        Live live = liveService.findByCourseId(Integer.valueOf(header.getChannel_id()));

        if (live != null) {
            live.setLiveState(AudioCoursePlay.PlayState.pause.ordinal());
            live.setCloseType(Integer.valueOf(request.getParameter("errcode")));//设置断流的错误码
            liveService.updateByPrimaryKey(live);
        }
    }

    /**
     * 处理推流时的回调
     * @param header
     * @param request
     */
    protected void onStreamPush(TxHeaderDTO header, HttpServletRequest request){
        Live live = liveService.findByCourseId(Integer.valueOf(header.getChannel_id()));

        if (live != null) {
            live.setLiveState(AudioCoursePlay.PlayState.playing.ordinal());
            liveService.updateByPrimaryKey(live);
        }
    }

    /**
     * 处理直播录制完成的回调
     * @param header
     * @param request
     */
    protected void onRecordOver(TxHeaderDTO header, HttpServletRequest request){
        // todo 处理视频下载
    }

    /**
     * 处理直播截图完成的回调
     * @param header
     * @param request
     */
    protected void onScreenCapture(TxHeaderDTO header, HttpServletRequest request){
        // todo 暂时未做处理
    }


}
