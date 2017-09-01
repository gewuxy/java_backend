package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.ExamHistoryItem;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleExamHistoryItemService;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleExamHistoryItemServiceImpl extends WriteAbleBaseServiceImpl<ExamHistoryItem> implements WriteAbleExamHistoryItemService{
    @Override
    public String getTable() {
        return "t_exam_history_item";
    }
}
