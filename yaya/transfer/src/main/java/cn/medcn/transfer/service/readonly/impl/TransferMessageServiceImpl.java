package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PptReadOnly;
import cn.medcn.transfer.model.writeable.AudioCourse;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.model.writeable.MeetAudio;
import cn.medcn.transfer.model.writeable.MeetModule;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferMessageService;
import cn.medcn.transfer.service.writeable.*;
import cn.medcn.transfer.service.writeable.impl.*;
import cn.medcn.transfer.utils.CommonConnctionUtils;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/5/22.
 */
public class TransferMessageServiceImpl extends ReadOnlyBaseServiceImpl<PptReadOnly> implements TransferMessageService {

    @Override
    public String getTable() {
        return "t_meeting_message";
    }

    @Override
    public String getIdKey() {
        return "messageId";
    }

    private WriteAbleAudioCourseService writeAbleAudioCourseService = new WriteAbleAudioCourseServiceImpl();

    private WriteAbleMeetModuleService writeAbleMeetModuleService = new WriteAbleMeetModuleServiceImpl();

    private WriteAbleMeetAudioService writeAbleMeetAudioService = new WriteAbleMeetAudioServiceImpl();

    @Override
    public Blob getAuidoBlobData(Long messageId) {
        String sql = "select message_content from t_meeting_message where message_id = ?";
        Object[] params = new Object[]{messageId};
        Blob blob = DAOUtils.getBlobData(CommonConnctionUtils.getOldConnection(), sql, params);
        return blob;
    }


    @Override
    public PptReadOnly findMeetMessage(Long messageId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PptReadOnly condition = new PptReadOnly();
        condition.setMessageId(Long.valueOf(messageId));
        PptReadOnly meetMessage = findOne(condition);
        return meetMessage;
    }

    /**
     * 根据会议ID获取所有的
     *
     * @param meetId
     * @return
     */
    @Override
    public List<PptReadOnly> findByMeetId(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select msg.message_id, msg.send_time, msg.message_type,msg.message_time, msg.msg_groupid " +
                "from t_meeting_message msg INNER JOIN t_meeting_group g on g.group_id = msg.group_id " +
                "INNER JOIN t_meeting m on m.meeting_id = g.meeting_id " +
                "where m.meeting_id = ?";
        Object[] params = new Object[]{meetId};
        List<PptReadOnly> list = (List<PptReadOnly>) DAOUtils.selectList(getConnection(), sql, params, PptReadOnly.class);
        return list;
    }

    @Override
    public void transfer(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] ppt语音 ...");
        List<PptReadOnly> audioList = findByMeetId(meetSourceReadOnly.getMeetingId());
        if(audioList == null || audioList.size() == 0){
            LogUtils.debug(this.getClass(), "会议["+meetSourceReadOnly.getMeetingName()+"] 未找到PPT+语音信息 ...");
        }else{
            //执行转换操作
            //首先创建模块
            MeetModule module = new MeetModule();
            module.setModuleName(MeetModule.ModuleFunction.PPT.getFunName());
            module.setMeetId(meet.getId());
            module.setMainFlag(true);
            module.setActive(true);
            module.setFunctionId(MeetModule.ModuleFunction.PPT.getFunId());
            Integer moduleId = writeAbleMeetModuleService.addMeetmodule(module);
            LogUtils.debug(this.getClass(), "创建PPT模块成功 ...");

            //保存语音PPT课程
            Integer onwerId = meet.getOwnerId();
            AudioCourse course = AudioCourse.build(meetSourceReadOnly, audioList, onwerId);
            course.setCategory("不限".equals(meetSourceReadOnly.getKind())?"其他":meetSourceReadOnly.getKind());
            course.setTitle(meetSourceReadOnly.getMeetingName());
            Integer courseId = writeAbleAudioCourseService.transferCourse(course, audioList, meet.getId());
            LogUtils.debug(this.getClass(), "保存PPT语音课程加明细成功 ....");

            //创建MeetAudio
            MeetAudio audio = new MeetAudio();
            audio.setMeetId(meet.getId());
            audio.setModuleId(moduleId);
            audio.setCourseId(courseId);
            writeAbleMeetAudioService.insert(audio);
            LogUtils.debug(this.getClass(), "创建会议PPT成功 ...");
        }
    }



}
