package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.SurveyPaper;
import cn.medcn.transfer.model.writeable.SurveyQuestion;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyPaperService;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyQuestionService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleSurveyPaperServiceImpl extends WriteAbleBaseServiceImpl<SurveyPaper> implements WriteAbleSurveyPaperService {
    @Override
    public String getTable() {
        return "t_survey_paper";
    }


    private WriteAbleSurveyQuestionService writeAbleSurveyQuestionService = new WriteAbleSurveyQuestionServiceImpl();

    /**
     * 增加问卷调查试卷
     *
     * @param surveyPaper
     * @return
     */
    @Override
    public Integer addSurveyPaper(SurveyPaper surveyPaper) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Integer id = ((Long) insert(surveyPaper)).intValue();
        if(surveyPaper.getQuestionList() != null){
            int sort = 1;
            for(SurveyQuestion question : surveyPaper.getQuestionList()){
                question.setPaperId(id);
                question.setSort(sort);
                writeAbleSurveyQuestionService.insert(question);
                sort ++;
            }
        }
        return id;
    }
}
