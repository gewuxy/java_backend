package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.SurveyQuestion;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyQuestionService;

/**
 * Created by lixuan on 2017/6/20.
 */
public class WriteAbleSurveyQuestionServiceImpl extends WriteAbleBaseServiceImpl<SurveyQuestion> implements WriteAbleSurveyQuestionService {
    @Override
    public String getTable() {
        return "t_survey_question";
    }


}
