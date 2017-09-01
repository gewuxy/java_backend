package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.PptReadOnly;
import cn.medcn.transfer.model.readonly.PptRecordReadOnly;
import cn.medcn.transfer.model.writeable.AudioCourse;
import cn.medcn.transfer.model.writeable.AudioCourseDetail;
import cn.medcn.transfer.model.writeable.AudioCourseDetailSupport;
import cn.medcn.transfer.model.writeable.VideoHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleAudioCourseDetailService;
import cn.medcn.transfer.service.writeable.WriteAbleAudioCourseService;
import cn.medcn.transfer.service.writeable.WriteAbleVideoHistoryService;
import cn.medcn.transfer.utils.CommonConnctionUtils;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.FileUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by lixuan on 2017/6/16.
 */
public class WriteAbleAudioCourseServiceImpl extends WriteAbleBaseServiceImpl<AudioCourse> implements WriteAbleAudioCourseService {
    @Override
    public String getTable() {
        return "t_audio_course";
    }

    private WriteAbleAudioCourseDetailService courseDetailService = new WriteAbleAudioCourseDetailServiceImpl();

    @Override
    public Integer transferCourse(AudioCourse course, List<PptReadOnly> pptList, String meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException {
        Long idLong = (Long) insert(course);
        Integer id = idLong.intValue();
        //处理ppt+语音明细
        transferDetails(pptList, id, meetId);
        return id;
    }


    private void transferDetails(List<PptReadOnly> pptList, Integer courseId, String meetId) throws IOException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<AudioCourseDetailSupport> supports = group(pptList);
        int sort = 1;
        for(AudioCourseDetailSupport support : supports){
            AudioCourseDetail detail = new AudioCourseDetail();
            detail.setCourseId(courseId);
            detail.setSort(sort);
            PptReadOnly pptReadOnly = support.findPPTByType(AudioCourseDetail.DETAIL_TYPE_IMG_KEY);
            if(pptReadOnly != null){//图片
                detail.setId(pptReadOnly.getMessageId().intValue());
                String fileName = FileUtils.savePPTImg(getAuidoBlobData(pptReadOnly.getMessageId()), courseId, ".jpg");
                detail.setImgUrl(fileName);
            }
            PptReadOnly pptReadOnlyMp3Audio = support.findPPTByType(AudioCourseDetail.DETAIL_TYPE_AUDIO_MP3_KEY);
            if(pptReadOnlyMp3Audio != null){
                if(pptReadOnly == null){
                    detail.setId(pptReadOnlyMp3Audio.getMessageId().intValue());
                }
                String fileName = FileUtils.savePPTAudio(getAuidoBlobData(pptReadOnlyMp3Audio.getMessageId()), courseId, ".mp3");
                detail.setAudioUrl(fileName);
            }
            PptReadOnly pptReadOnlyAacAudio = support.findPPTByType(AudioCourseDetail.DETAIL_TYPE_AUDIO_AAC_KEY);
            if(pptReadOnlyAacAudio != null){
                if(pptReadOnly == null && pptReadOnlyMp3Audio == null){
                    detail.setId(pptReadOnlyAacAudio.getMessageId().intValue());
                }
                String fileName = FileUtils.savePPTAudio(getAuidoBlobData(pptReadOnlyAacAudio.getMessageId()), courseId, ".aac");
                detail.setAudioUrl(fileName);
            }

            PptReadOnly pptReadOnlyVideo = support.findPPTByType(AudioCourseDetail.DETAIL_TYPE_VIDEO_KEY);
            if(pptReadOnlyVideo != null){
                if(pptReadOnly == null && pptReadOnlyMp3Audio == null && pptReadOnlyAacAudio == null){
                    detail.setId(pptReadOnlyVideo.getMessageId().intValue());
                }
                String fileName = FileUtils.savePPTVideo(getAuidoBlobData(pptReadOnlyVideo.getMessageId()), courseId, ".mp4");
                detail.setVideoUrl(fileName);
            }
            if(detail.getAudioUrl() != null || detail.getImgUrl() != null || detail.getVideoUrl() != null ){
                courseDetailService.insert(detail);
                sort ++;
            }

            //处理学习记录
            LogUtils.debug(this.getClass(), "开始处理PPT学习记录 ...");
            Long messageId = detail.getId() == null?0L:detail.getId().longValue();
            List<PptRecordReadOnly> recordReadOnlies = findPptRecordByMessageId(messageId);
            for(PptRecordReadOnly recordReadOnly : recordReadOnlies){
                recordReadOnly.setMessageId(messageId);
                addAudioHistory(recordReadOnly, meetId, courseId);
            }
            LogUtils.debug(this.getClass(), "处理PPT学习记录成功 !!!");
        }
    }


    private void addAudioHistory(PptRecordReadOnly recordReadOnly, String meetId, Integer courseId){
        String sql = "insert into t_audio_history (`id`, `meet_id`, `detail_id`, `usedtime`,`start_time`, `end_time`, `user_id`, `finished`,`course_id`)";
        sql+= " values (?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[9];
        params[0] = recordReadOnly.getId();
        params[1] = meetId;
        params[2] = recordReadOnly.getMessageId();
        params[3] = recordReadOnly.getDuration();
        params[4] = recordReadOnly.getStartTime();
        params[5] = recordReadOnly.getEndTime();
        params[6] = recordReadOnly.getUserId();
        params[7] = false;
        params[8] = courseId;

        DAOUtils.execute(this.getConnection(), sql, params);
    }


    private List<PptRecordReadOnly> findPptRecordByMessageId(Long messageId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select max(start_time) as start_time, max(end_time) as end_time,  user_id, sum(duration) as duration from t_meetingimg_record where message_id =? group by user_id";
        Object[] params = new Object[]{messageId};
        List<PptRecordReadOnly> list = (List<PptRecordReadOnly>) DAOUtils.selectList(CommonConnctionUtils.getOldConnection(), sql, params, PptRecordReadOnly.class);
        return list;
    }


    private List<AudioCourseDetailSupport> group(List<PptReadOnly> list){
        List<AudioCourseDetailSupport> supports = new ArrayList<>();
        if(list != null && list.size()>0){
            for(PptReadOnly ppt : list){
                if(AudioCourseDetail.DETAIL_TYPE_TEXT_KEY.equals(ppt.getMessageType())){//类型为明文时不处理
                    //do nothing
                }else {
                    AudioCourseDetailSupport support =findByGroupId(supports, ppt.getMsgGroupid());
                    if(support == null){//不存在分组信息
                        support = new AudioCourseDetailSupport();
                        support.setMsgGroupId(ppt.getMsgGroupid());
                        support.setPptList(new ArrayList<PptReadOnly>());
                        support.getPptList().add(ppt);
                        supports.add(support);
                    }else{
                        support.getPptList().add(ppt);
                    }
                }
            }
        }
        return supports;
    }


    private AudioCourseDetailSupport findByGroupId(List<AudioCourseDetailSupport> supports,String groupId){
        if(groupId == null || "".equals(groupId)){
            return null;
        }
        AudioCourseDetailSupport support = null;
        for(AudioCourseDetailSupport sup : supports){
            if(groupId.equals(sup.getMsgGroupId())){
                support = sup;
                break;
            }
        }
        return support;
    }


    private Blob getAuidoBlobData(Long messageId) {
        String sql = "select message_content from t_meeting_message where message_id = ?";
        Object[] params = new Object[]{messageId};
        Blob blob = DAOUtils.getBlobData(CommonConnctionUtils.getOldConnection(), sql, params);
        return blob;
    }

    /**
     * 将meeting_message按msgGroupId分组
     * 每一组对应当前的一个AudioCourseDetail
     * @param pptList
     * @return
     */
    private List<List<PptReadOnly>> groupPPTList(List<PptReadOnly> pptList){
        List<List<PptReadOnly>> map = new ArrayList<>();
        if(pptList != null && pptList.size() > 0){
            String tempMsgGroupId = null;
            List<PptReadOnly> tempList = new ArrayList<>();
            for(PptReadOnly ppt : pptList){
                if(ppt.getMsgGroupid() == null || "".equals(ppt.getMsgGroupid())){
                    tempList.add(ppt);
                    map.add(tempList);
                    tempList = new ArrayList<>();
                }else{
                    if(ppt.getMsgGroupid().equals(tempMsgGroupId)){
                        tempList.add(ppt);
                        map.add(tempList);
                    }else{
                        map.add(tempList);
                        tempList = new ArrayList<>();
                    }
                    tempMsgGroupId = ppt.getMsgGroupid();
                }
            }
        }
        return map;
    }

}
