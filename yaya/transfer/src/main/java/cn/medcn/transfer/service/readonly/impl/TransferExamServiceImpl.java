package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.*;
import cn.medcn.transfer.model.writeable.*;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferExamService;
import cn.medcn.transfer.service.writeable.*;
import cn.medcn.transfer.service.writeable.impl.*;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LetterUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/16.
 */
public class TransferExamServiceImpl extends ReadOnlyBaseServiceImpl<PaperReadOnly> implements TransferExamService {

    private static final String QUESTION_TABLE_NAME = "t_question_paper";

    private static final String QUESTION_OPTION_TABLE_NAME = "t_question_option";


    private WriteAbleMeetModuleService writeAbleMeetModuleService = new WriteAbleMeetModuleServiceImpl();

    private WriteAbleExamPaperService writeAbleExamPaperService = new WriteAbleExamPaperServiceImpl();

    private WriteAbleExamService writeAbleExamService = new WriteAbleExamServiceImpl();

    private WriteAbleSurveyPaperService writeAbleSurveyPaperService = new WriteAbleSurveyPaperServiceImpl();

    private WriteAbleSurveyService writeAbleSurveyService = new WriteAbleSurveyServiceImpl();

    private WriteAbleExamHistoryService writeAbleExamHistoryService = new WriteAbleExamHistoryServiceImpl();

    private WriteAbleSurveyHistoryService writeAbleSurveyHistoryService = new WriteAbleSurveyHistoryServiceImpl();

    @Override
    public String getTable() {
        return "t_paper";
    }

    @Override
    public String getIdKey() {
        return "pId";
    }

    /**
     * 根据试卷ID获取试题列表
     *
     * @param paperId
     * @return
     */
    @Override
    public List<QuestionReadOnly> findQuestion(Long paperId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        QuestionReadOnly condition = new QuestionReadOnly();
        condition.setP_id(paperId);
        List<QuestionReadOnly> list = (List<QuestionReadOnly>) DAOUtils.selectList(this.getConnection(), condition, QUESTION_TABLE_NAME);
        for(QuestionReadOnly question : list){
            question.setOptionList(findOptions(question.getQ_id()));
        }
        return list;
    }

    /**
     * 根据试题ID获取选项
     *
     * @param questionId
     * @return
     */
    @Override
    public List<QuestionOptionReadOnly> findOptions(Long questionId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        QuestionOptionReadOnly condition = new QuestionOptionReadOnly();
        condition.setQ_id(questionId);
        List<QuestionOptionReadOnly> list = (List<QuestionOptionReadOnly>) DAOUtils.selectList(this.getConnection(), condition, QUESTION_OPTION_TABLE_NAME);
        return list;
    }


    /**
     * 根据会议ID获取试卷信息
     *
     * @param meetId
     * @return
     */
    @Override
    public PaperReadOnly findExamPaper(Long meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PaperReadOnly condition = new PaperReadOnly();
        condition.setMeetingId(meetId);
        condition.setP_type(PaperReadOnly.TYPE_EXAM);
        PaperReadOnly paper = (PaperReadOnly) DAOUtils.selectOne(getConnection(), condition, this.getTable());
        if(paper != null){
            paper.setQuestionList(findQuestion(paper.getP_id()));
        }
        return paper;
    }


    /**
     * 查询问卷调查试卷
     *
     * @param meetId
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public PaperReadOnly findSurveyPaper(Long meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PaperReadOnly condition = new PaperReadOnly();
        condition.setMeetingId(meetId);
        condition.setP_type(PaperReadOnly.TYPE_SURVEY);
        PaperReadOnly paper = (PaperReadOnly) DAOUtils.selectOne(getConnection(), condition, this.getTable());
        if(paper != null){
            paper.setQuestionList(findQuestion(paper.getP_id()));
        }
        return paper;
    }

    /**
     * 转换考试信息和问卷信息
     *
     * @param meetSourceReadOnly
     * @param meet
     */
    @Override
    public void transferExam(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 考试信息...");
        PaperReadOnly paper = findExamPaper(meetSourceReadOnly.getMeetingId());
        if(paper != null){
            MeetModule module = new MeetModule();
            module.setFunctionId(MeetModule.ModuleFunction.EXAM.getFunId());
            module.setMainFlag(false);
            module.setActive(true);
            module.setMeetId(meet.getId());
            module.setModuleName(MeetModule.ModuleFunction.EXAM.getFunName());
            Integer moduleId = writeAbleMeetModuleService.addMeetmodule(module);

            //处理试卷信息
            ExamPaper examPaper = ExamPaper.build(meetSourceReadOnly, paper, meet.getOwnerId());
            Integer paperId = writeAbleExamPaperService.addExamPaper(examPaper);

            //处理会议考试信息
            MeetExam exam = MeetExam.build(meetSourceReadOnly, paper, meet.getId(), moduleId);
            exam.setPaperId(paperId);
            writeAbleExamService.insert(exam);

            LogUtils.debug(this.getClass(), "处理["+meetSourceReadOnly.getMeetingName()+"] 的考试信息成功 ！！共"+paper.getQuestionList().size()+"个试题 !!");
            LogUtils.debug(this.getClass(), " ==========================================================");
            LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 考试记录 ...");

            List<ReportPaperReadOnly> list = findPaperReport(paperId.longValue());

            LogUtils.debug(this.getClass(), "开始转换["+meetSourceReadOnly.getMeetingName()+"]的考试记录 !!!");
            transferExamHistory(meetSourceReadOnly, meet);
        }
    }


    /**
     * 转换考试记录
     *
     * @param meetSourceReadOnly
     * @param meet
     */
    @Override
    public void transferExamHistory(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 考试记录信息...");
        PaperReadOnly paper = findExamPaper(meetSourceReadOnly.getMeetingId());
        if(paper != null && meet != null){
            LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 考试记录 ...");
            Integer examId = writeAbleExamHistoryService.findExamId(meet.getId());
            List<ReportPaperReadOnly> historyList = findPaperReport(paper.getP_id().longValue());
            if(!historyList.isEmpty()){
                for(ReportPaperReadOnly reportPaperReadOnly:historyList){
                    List<ReportAnswerPaperOnly> answerList = findAnswerByPaperId(paper.getP_id(), reportPaperReadOnly.getUserId());
                    reportPaperReadOnly.setAnswerList(answerList);
                    LogUtils.debug(this.getClass(), "考试答题记录明细长度 = "+answerList.size());
                    ExamHistory examHistory = ExamHistory.build(reportPaperReadOnly, meet.getId());
                    examHistory.setExamId(examId);
                    writeAbleExamHistoryService.addExamHistory(examHistory);
                }
            }
        }
    }

    private List<ReportPaperReadOnly> findPaperReport(Long paperId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_report_paper where p_id = ?";
        Object[] params = new Object[]{paperId};
        List<ReportPaperReadOnly> list = (List<ReportPaperReadOnly>) DAOUtils.selectList(getConnection(), sql, params, ReportPaperReadOnly.class);
        return list;
    }


    private List<ReportAnswerPaperOnly> findAnswerByPaperId(Long paperId,Long userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select a.a_id, a.q_id, a.user_id, a.sel_answer,q.q_answer as right_key from t_answer_paper a inner join t_question_paper q on a.q_id=q.q_id inner join t_question_paper p on a.q_id = p.q_id and p.p_id = ? and a.user_id = ?";
        Object[] params = new Object[]{paperId, userId};
        List<ReportAnswerPaperOnly> list = (List<ReportAnswerPaperOnly>) DAOUtils.selectList(getConnection(), sql, params, ReportAnswerPaperOnly.class);
        return list;
    }

    /**
     * 转换问卷信息
     *
     * @param meetSourceReadOnly
     * @param meet
     */
    @Override
    public void transferSurvey(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 问卷调查信息...");
        PaperReadOnly paper = findSurveyPaper(meetSourceReadOnly.getMeetingId());
        if(paper != null){
            MeetModule module = new MeetModule();
            module.setFunctionId(MeetModule.ModuleFunction.SURVEY.getFunId());
            module.setMainFlag(false);
            module.setActive(true);
            module.setMeetId(meet.getId());
            module.setModuleName(MeetModule.ModuleFunction.SURVEY.getFunName());
            Integer moduleId = writeAbleMeetModuleService.addMeetmodule(module);

            //处理问卷调查试卷信息
            SurveyPaper surveyPaper = SurveyPaper.build(meetSourceReadOnly, paper, meet.getOwnerId());
            Integer paperId = writeAbleSurveyPaperService.addSurveyPaper(surveyPaper);

            //处理会议考试信息
            MeetSurvey survey = MeetSurvey.build(meet.getId(), moduleId, paperId);
            writeAbleSurveyService.insert(survey);

            LogUtils.debug(this.getClass(), "处理["+meetSourceReadOnly.getMeetingName()+"] 的问卷调查信息成功 ！！共"+paper.getQuestionList().size()+"个试题 !!");

            transferSurveyHistory(meetSourceReadOnly, meet);
        }
    }

    private List<ReportSurveyItemReadOnly> findReportSurvey(Long paperId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select r_id,p_id, q_id, o_id, user_id from t_report_survey where p_id = ?";
        Object[] params = new Object[]{paperId};
        List<ReportSurveyItemReadOnly> list = (List<ReportSurveyItemReadOnly>) DAOUtils.selectList(getConnection(), sql, params, ReportSurveyItemReadOnly.class);
        return list;
    }


    /**
     * 转换问卷调查记录
     *
     * @param meetSourceReadOnly
     * @param meet
     */
    @Override
    public void transferSurveyHistory(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        LogUtils.debug(this.getClass(), "开始处理问卷作答记录 ...");
        PaperReadOnly paper = findSurveyPaper(meetSourceReadOnly.getMeetingId());
        if(paper != null && meet != null){
            List<ReportSurveyItemReadOnly> reports = findReportSurvey(paper.getP_id().longValue());
            if(!reports.isEmpty()){
                Map<Long, List<ReportSurveyItemReadOnly>> userAnswerMap = new HashMap<>();
                for(ReportSurveyItemReadOnly reportSurveyReadOnly : reports){
                    if(userAnswerMap.get(reportSurveyReadOnly.getUserId()) == null){
                        List<ReportSurveyItemReadOnly> items = new ArrayList<>();
                        items.add(reportSurveyReadOnly);
                        userAnswerMap.put(reportSurveyReadOnly.getUserId(), items);
                    }else{
                        userAnswerMap.get(reportSurveyReadOnly.getUserId()).add(reportSurveyReadOnly);
                    }
                }
                transferSurveyHistorys(userAnswerMap, meet.getId(), paper.getP_id().intValue());
            }
        }
        LogUtils.debug(this.getClass(), "开始处理问卷作答记录 ...");
    }

    private void transferSurveyHistorys(Map<Long, List<ReportSurveyItemReadOnly>> historyMap, String meetId, Integer paperId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        if(historyMap != null){
            for(Long key : historyMap.keySet()){
                LogUtils.debug(this.getClass(), "开始处理用户["+key+"]问卷调查记录明细...");
                SurveyHistory history = SurveyHistory.build(key.intValue(),meetId,paperId);
                Integer surveyId = writeAbleSurveyHistoryService.findSurveyId(meetId);
                history.setSurveyId(surveyId);
                List<ReportSurveyItemReadOnly> items = historyMap.get(key);
                if(items != null){
                    List<SurveyHistoryItem> historyItemList = new ArrayList<>();
                    for(ReportSurveyItemReadOnly reportSurveyItemReadOnly : items){
                        List<QuestionOptionReadOnly> optionList = findQuestionOptions(reportSurveyItemReadOnly.getQ_id());
                        SurveyHistoryItem item = SurveyHistoryItem.build(reportSurveyItemReadOnly);
                        if(!optionList.isEmpty()){
                            Integer questionSort = 1;
                            for(QuestionOptionReadOnly option : optionList){
                                if(option.getO_id().longValue() == reportSurveyItemReadOnly.getO_id().longValue()){
                                    item.setAnswer(LetterUtils.numberToLetter(questionSort));
                                    break;
                                }
                                questionSort++;
                            }
                        }
                        historyItemList.add(item);
                    }
                    history.setItems(historyItemList);
                }
                writeAbleSurveyHistoryService.addSurveyHistory(history);
                LogUtils.debug(this.getClass(), "成功处理用户["+key+"]问卷调查记录明细 !!!");
            }
        }
    }


    private List<QuestionOptionReadOnly> findQuestionOptions(Long questionId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_question_option where q_id = ? order by o_id";
        Object[] params = new Object[]{questionId};
        List<QuestionOptionReadOnly> optionReadOnlies = (List<QuestionOptionReadOnly>) DAOUtils.selectList(getConnection(),sql ,params,QuestionOptionReadOnly.class);
        return optionReadOnlies;
    }
}
