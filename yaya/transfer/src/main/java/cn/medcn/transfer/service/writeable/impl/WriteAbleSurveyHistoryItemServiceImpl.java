package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.SurveyHistoryItem;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleSurveyHistoryItemService;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleSurveyHistoryItemServiceImpl extends WriteAbleBaseServiceImpl<SurveyHistoryItem> implements WriteAbleSurveyHistoryItemService {
    @Override
    public String getTable() {
        return "t_survey_history_item";
    }
}
