package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MeetSurvey;
import cn.medcn.transfer.model.writeable.SurveyHistory;
import cn.medcn.transfer.model.writeable.SurveyHistoryItem;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyHistoryItemService;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyHistoryService;
import cn.medcn.transfer.utils.DAOUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleSurveyHistoryServiceImpl extends WriteAbleBaseServiceImpl<SurveyHistory> implements WriteAbleSurveyHistoryService {
    @Override
    public String getTable() {
        return "t_survey_history";
    }

    private WriteAbleSurveyHistoryItemService writeAbleSurveyHistoryItemService = new WriteAbleSurveyHistoryItemServiceImpl();

    @Override
    public Integer addSurveyHistory(SurveyHistory surveyHistory) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Long id = (Long) insert(surveyHistory);
        if(surveyHistory.getItems() != null){
            for(SurveyHistoryItem item:surveyHistory.getItems()){
                item.setHistoryId(id.intValue());
                writeAbleSurveyHistoryItemService.insert(item);
            }
        }
        return id.intValue();
    }

    @Override
    public Integer findSurveyId(String meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        if(meetId == null || "".equals(meetId)){
            return 0;
        }
        String sql = "select id from t_meet_survey where meet_id = ?";
        Object[] params = {meetId};
        MeetSurvey survey = (MeetSurvey) DAOUtils.selectOne(getConnection(), sql, params, MeetSurvey.class);
        return survey == null ? 0: survey.getId();
    }
}
