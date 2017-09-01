package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetAudio;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMeetAudioService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleMeetAudioServiceImpl extends WriteAbleBaseServiceImpl<MeetAudio> implements WriteAbleMeetAudioService {
    @Override
    public String getTable() {
        return "t_meet_audio";
    }



}
