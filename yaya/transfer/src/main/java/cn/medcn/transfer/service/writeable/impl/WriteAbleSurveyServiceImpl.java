package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetSurvey;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleSurveyServiceImpl extends WriteAbleBaseServiceImpl<MeetSurvey> implements WriteAbleSurveyService {

    @Override
    public String getTable() {
        return "t_meet_survey";
    }
}
