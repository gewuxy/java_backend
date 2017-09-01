package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.ExamQuestion;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleExamQuestionService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleExamQuestionServiceImpl extends WriteAbleBaseServiceImpl<ExamQuestion> implements WriteAbleExamQuestionService {
    @Override
    public String getTable() {
        return "t_exam_question";
    }


    @Override
    public Integer addQuestion(ExamQuestion examQuestion) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Long idLong = (Long) insert(examQuestion);
        Integer id = idLong.intValue();
        examQuestion.setId(id);
        return id;
    }
}
