package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.ExamHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/22.
 */
public interface WriteAbleExamHistoryService extends WriteAbleBaseService<ExamHistory> {

    void addExamHistory(ExamHistory examHistory) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Integer findExamId(String meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
