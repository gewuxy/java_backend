package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetExam;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleExamService;

/**
 * Created by lixuan on 2017/6/16.
 */
public class WriteAbleExamServiceImpl extends WriteAbleBaseServiceImpl<MeetExam> implements WriteAbleExamService {
    @Override
    public String getTable() {
        return "t_meet_exam";
    }



}
