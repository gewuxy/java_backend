package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PaperReadOnly;
import cn.medcn.transfer.model.readonly.QuestionOptionReadOnly;
import cn.medcn.transfer.model.readonly.QuestionReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/14.
 */
public interface TransferExamService extends ReadOnlyBaseService<PaperReadOnly>{

    /**
     * 根据试卷ID获取试题列表
     * @param paperId
     * @return
     */
    List<QuestionReadOnly> findQuestion(Long paperId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 根据试题ID获取选项
     * @param questionId
     * @return
     */
    List<QuestionOptionReadOnly> findOptions(Long questionId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 根据会议ID获取试卷信息
     * @param meetId
     * @return
     */
    PaperReadOnly findExamPaper(Long meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 查询问卷调查试卷
     * @param meetId
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    PaperReadOnly findSurveyPaper(Long meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;;

    /**
     * 转换考试信息
     * @param meetSourceReadOnly
     * @param meet
     */
    void transferExam(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * 转换考试记录
     * @param meetId
     * @param meet
     */
    void transferExamHistory(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
    /**
     * 转换问卷信息
     * @param meetSourceReadOnly
     * @param meet
     */
    void transferSurvey(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 转换问卷调查记录
     * @param meetSourceReadOnly
     * @param meet
     */
    void transferSurveyHistory(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
