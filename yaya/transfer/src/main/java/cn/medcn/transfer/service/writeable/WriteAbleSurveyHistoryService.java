package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.SurveyHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/22.
 */
public interface WriteAbleSurveyHistoryService extends WriteAbleBaseService<SurveyHistory> {

    Integer addSurveyHistory(SurveyHistory surveyHistory) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Integer findSurveyId(String meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
