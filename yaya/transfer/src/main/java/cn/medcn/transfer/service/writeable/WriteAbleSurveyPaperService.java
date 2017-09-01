package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.SurveyPaper;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public interface WriteAbleSurveyPaperService extends WriteAbleBaseService<SurveyPaper> {
    /**
     * 增加问卷调查试卷
     * @param surveyPaper
     * @return
     */
    Integer addSurveyPaper(SurveyPaper surveyPaper) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
