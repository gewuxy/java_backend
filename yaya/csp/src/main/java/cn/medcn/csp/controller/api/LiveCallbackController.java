package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

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

    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Value("${app.file.base}")
    protected String appFileBase;

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

        TxHeaderDTO header = getTxHeader(requestBody);
        if (header != null) {
            String downLoadUrl = (String) JsonUtils.getValue(requestBody, TX_VIDEO_DOWNLOAD_URL_KEY);

            String fileName = StringUtils.nowStr() + "." + FileTypeSuffix.VIDEO_SUFFIX_MP4.suffix;

            Integer courseId = parseCourseId(header.getChannel_id());

            boolean deleteFlag = true;

            if (courseId != null) {
                //下载视频
                FileUtils.downloadNetWorkFile(downLoadUrl, fileUploadBase + FilePath.COURSE.path + "/" + courseId + "/replay/", fileName);

                String videoReplayBasePath = FilePath.COURSE.path + "/" + courseId + "/replay/";
                String savedFilePath = videoReplayBasePath + fileName;

                Live live = liveService.findByCourseId(courseId);

                if (live != null) {
                    String previousURL = live.getReplayUrl();
                    if (CheckUtils.isEmpty(previousURL)) {//不存在点播地址的情况下
                        live.setReplayUrl(savedFilePath);
                    } else {
                        //已经存在的情况下 需要整合两段视频
                        File existedFile = new File(fileUploadBase + live.getReplayUrl());
                        if (existedFile.exists()) {
                            String newSaveFileName = StringUtils.nowStr() + "." + FileTypeSuffix.VIDEO_SUFFIX_MP4.suffix;
                            String mergePath = fileUploadBase + videoReplayBasePath + newSaveFileName;
                            FFMpegUtils.concatMp4(mergePath,
                                    deleteFlag,
                                    fileUploadBase + live.getReplayUrl(), fileUploadBase + videoReplayBasePath + fileName);
                            live.setReplayUrl(videoReplayBasePath + newSaveFileName);

                        } else {
                            live.setReplayUrl(savedFilePath);
                        }
                    }
                    liveService.updateByPrimaryKey(live);
                }
            }
        }
    }

    /**
     * 处理直播截图完成的回调
     *
     * @param requestBody
     */
    protected void onScreenCapture(String requestBody) {
        // do nothing
    }


}
