package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.AudioCourseDetail;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleAudioCourseDetailService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleAudioCourseDetailServiceImpl extends WriteAbleBaseServiceImpl<AudioCourseDetail> implements WriteAbleAudioCourseDetailService {
    @Override
    public String getTable() {
        return "t_audio_course_detail";
    }
}
