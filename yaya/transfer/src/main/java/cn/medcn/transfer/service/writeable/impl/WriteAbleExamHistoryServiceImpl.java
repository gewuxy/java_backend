package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.ExamHistory;
import cn.medcn.transfer.model.writeable.ExamHistoryItem;
import cn.medcn.transfer.model.writeable.MeetExam;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleExamHistoryItemService;
import cn.medcn.transfer.service.writeable.WriteAbleExamHistoryService;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleExamHistoryServiceImpl extends WriteAbleBaseServiceImpl<ExamHistory> implements WriteAbleExamHistoryService{
    @Override
    public String getTable() {
        return "t_exam_history";
    }

    private WriteAbleExamHistoryItemService writeAbleExamHistoryItemService = new WriteAbleExamHistoryItemServiceImpl();

    @Override
    public void addExamHistory(ExamHistory examHistory) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(examHistory);
        LogUtils.debug(this.getClass(), "保存考试记录成功, 开始保存考试记录明细 ...");
        if(examHistory.getItemList()!= null && examHistory.getItemList().size() > 0){
            for(ExamHistoryItem item : examHistory.getItemList()){
                writeAbleExamHistoryItemService.insert(item);
                LogUtils.debug(this.getClass(), "保存考试记录明细成功 !!!");
            }
        }

    }


    @Override
    public Integer findExamId(String meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        String sql = "select id from t_meet_exam where meet_id = ?";
        Object[] params = new Object[]{meetId};
        MeetExam meetExam = (MeetExam) DAOUtils.selectOne(getConnection(),sql, params, MeetExam.class);
        return meetExam == null ?0:meetExam.getId();
    }
}
