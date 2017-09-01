package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.ExamPaper;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public interface WriteAbleExamPaperService extends WriteAbleBaseService<ExamPaper> {


    Integer addExamPaper(ExamPaper paper) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
