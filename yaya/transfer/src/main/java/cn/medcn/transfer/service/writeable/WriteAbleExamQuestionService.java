package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.ExamQuestion;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public interface WriteAbleExamQuestionService extends WriteAbleBaseService<ExamQuestion> {

    Integer addQuestion(ExamQuestion examQuestion) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
