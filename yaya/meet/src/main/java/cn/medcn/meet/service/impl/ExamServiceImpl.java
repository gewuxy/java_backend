package cn.medcn.meet.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.common.utils.LetterUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dao.*;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.ExamService;
import cn.medcn.meet.service.MeetService;
import com.alibaba.fastjson.JSONArray;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
@Service
public class ExamServiceImpl extends BaseServiceImpl<MeetExam> implements ExamService {

    @Autowired
    private MeetExamDAO meetExamDAO;

    @Autowired
    private ExamPaperDAO examPaperDAO;

    @Autowired
    private ExamQuestionDAO examQuestionDAO;

    @Autowired
    private ExamPaperQuestionDAO examPaperQuestiondDAO;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private ExamHistoryDAO examHistoryDAO;

    @Autowired
    private ExamHistoryItemDAO examHistoryItemDAO;

    @Autowired
    private MeetPropertyDAO meetPropertyDAO;

    @Autowired
    private MeetDAO meetDAO;

    @Autowired
    private MeetAttendDAO meetAttendDAO;

    @Override
    public Mapper<MeetExam> getBaseMapper() {
        return meetExamDAO;
    }

    /**
     * 获取会议考试信息(缓存)
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'meet_exam_'+#meetId")
    public ExamDTO findMeetExam(String meetId, Integer moduleId) {
        MeetExam examCondition = new MeetExam();
        //examCondition.setModuleId(moduleId);
        examCondition.setMeetId(meetId);
        MeetExam meetExam = meetExamDAO.selectOne(examCondition);
        MeetProperty property = meetPropertyDAO.findProperty(meetId);
        meetExam.setStartTime(property.getStartTime());
        meetExam.setEndTime(property.getEndTime());
        if (meetExam.getPaperId() != null) {
            ExamPaper paper = examPaperDAO.selectByPrimaryKey(meetExam.getPaperId());
            if (paper != null) {
                List<ExamQuestion> questionList = examQuestionDAO.findPaperQuestions(paper.getId());
                if (questionList.size() > 0) {
                    setQuestionPoint(questionList, paper);
                }
                paper.setQuestionList(questionList);
            }
            meetExam.setExamPaper(paper);
        }
        ExamDTO dto = ExamDTO.build(meetExam);
        return dto;
    }

    /**
     * 按照试卷的评分策略给每小题分配分数
     *
     * @param questions
     * @param paper
     */
    private void setQuestionPoint(List<ExamQuestion> questions, ExamPaper paper) {
        Map<Integer, Integer> scoreMap = findPaperQuestionScore(paper.getId());
        for (ExamQuestion question : questions) {
            question.setPoint(scoreMap.get(question.getId()));
        }
    }


    private Map<Integer, Integer> findPaperQuestionScore(Integer paperId) {
        Map<Integer, Integer> scoreMap = Maps.newHashMap();
        ExamPaperQuestion epqCondition = new ExamPaperQuestion();
        epqCondition.setPaperId(paperId);
        List<ExamPaperQuestion> pqlist = examPaperQuestiondDAO.select(epqCondition);
        for (ExamPaperQuestion epq : pqlist) {
            scoreMap.put(epq.getQuestionId(), epq.getPoint());
        }
        return scoreMap;
    }

    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'exam_paper_'+#paperId")
    public ExamPaper findExamPaper(Integer paperId) {
        ExamPaper paper = examPaperDAO.selectByPrimaryKey(paperId);
        if (paper != null) {
            paper.setQuestionList(examQuestionDAO.findPaperQuestions(paperId));
        }
        return paper;
    }

    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'exam_paper_'+#paperId")
    public void executeImportPaper(Integer paperId, List<ExamQuestion> questionList) {
        ExamPaper paper = examPaperDAO.selectByPrimaryKey(paperId);
        Integer totalPoint = 0;
        for (int i = 0; i < questionList.size(); i++) {
            totalPoint += questionList.get(i).getPoint();
            ExamQuestion question = questionList.get(i);
            question.setCategory(paper.getCategory());
            Integer qid = addQuestion(question);
            ExamPaperQuestion paperQuestion = new ExamPaperQuestion();
            paperQuestion.setSort(i + 1);
            paperQuestion.setPoint(question.getPoint());
            paperQuestion.setPaperId(paperId);
            paperQuestion.setQuestionId(qid);
            examPaperQuestiondDAO.insert(paperQuestion);
        }
        paper.setTotalPoint(totalPoint);
        examPaperDAO.updateByPrimaryKeySelective(paper);
    }

    @Override
    public List<ExamQuestion> parseExamQuestions(List<Object[]> objects) {
        List<ExamQuestion> questionList = Lists.newArrayList();
        for (Object[] objArray : objects) {
            if (StringUtils.isEmpty(objArray[0]) || StringUtils.isEmpty(objArray[1])) {
                continue;
            }
            ExamQuestion question = new ExamQuestion();
            if (((String) objArray[0]).startsWith(ExamQuestion.QuestionType.SINGLE_CHOICE.getLabel())) {
                question.setQtype(ExamQuestion.QuestionType.SINGLE_CHOICE.getType());
            } else if (((String) objArray[0]).startsWith(ExamQuestion.QuestionType.MULTIPLE_CHOICE.getLabel())) {
                question.setQtype(ExamQuestion.QuestionType.MULTIPLE_CHOICE.getType());
            } else if (((String) objArray[0]).startsWith(ExamQuestion.QuestionType.FILL_BLANK.getLabel())) {
                question.setQtype(ExamQuestion.QuestionType.FILL_BLANK.getType());
            } else if (((String) objArray[0]).startsWith(ExamQuestion.QuestionType.QUESTION_ANSERE.getLabel())) {
                question.setQtype(ExamQuestion.QuestionType.QUESTION_ANSERE.getType());
            } else {
                question.setQtype(-1);
                System.out.println("试题导入信息有误");
            }
            question.setTitle((String) objArray[1]);
            question.setOwner(14);
            question.setCreateTime(new Date());
            question.setRightKey((String) objArray[2]);
            String pointStr = (String) objArray[3];
            if (!StringUtils.isEmpty(pointStr)) {
                question.setPoint(Integer.parseInt(pointStr));
            }
            JSONArray array = new JSONArray();

            for (int answerIndex = 4; answerIndex < objArray.length; answerIndex++) {
                if (objArray[answerIndex] != null && !"".equals(objArray[answerIndex])) {
                    KeyValuePair pair = new KeyValuePair(LetterUtils.numberToLetter(answerIndex - 3), (String) objArray[answerIndex]);
                    array.add(pair);
                }
            }
            if (!array.isEmpty()) {
                question.setOptions(array.toJSONString());
            }

            questionList.add(question);
        }
        return questionList;
    }

    @Override
    public Integer addPaper(ExamPaper examPaper) {
        examPaperDAO.insert(examPaper);
        return examPaper.getId();
    }

    /**
     * 添加试题
     *
     * @param question
     */
    @Override
    public int addQuestion(ExamQuestion question) {
        examQuestionDAO.insert(question);
        return question.getId();
    }

    /**
     * 根据ID返回试题信息
     *
     * @param paperId
     * @param questionId
     * @return
     */
    @Override
    public ExamQuestion findQuestion(Integer paperId, Integer questionId) {
        ExamQuestion question = examQuestionDAO.findQuestion(paperId, questionId);
        return question;
    }


    /**
     * 判断考试是否已经结束
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public boolean checkExamFinished(String meetId, Integer moduleId) {
        ExamHistory condition = new ExamHistory();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        int count = examHistoryDAO.selectCount(condition);
        return count > 0;
    }

    /**
     * 考试提交
     *
     * @param history
     */
    @Override
    public Integer saveHistory(ExamHistory history) throws SystemException{
        try {
            ExamHistory condition = new ExamHistory();
            condition.setMeetId(history.getMeetId());
            //condition.setModuleId(history.getModuleId());
            condition.setUserId(history.getUserId());
            ExamHistory oldhistory = examHistoryDAO.selectOne(condition);
            if (oldhistory != null) {
                history.setFinishTimes((oldhistory.getFinishTimes() == null ? 1 : oldhistory.getFinishTimes()) + 1);
                deleteHistory(oldhistory);
            }
            if (history.getFinishTimes() == null) {
                history.setFinishTimes(1);
            }
            examHistoryDAO.insert(history);
            for (ExamHistoryItem item : history.getItems()) {
                item.setHistoryId(history.getId());
                examHistoryItemDAO.insert(item);
            }
        } catch (Exception e) {
            //重新发到queue中
            //submitToCache(history);
            throw new SystemException("考试提交失败");
        }
        return history.getScore();
    }

    /**
     * 小题打分
     *
     * @param qtype
     * @param point
     * @param rightKey
     * @param answer
     * @return
     */
    private int scoreQuestion(Integer qtype, Integer point, String rightKey, String answer) {
        int returnPoint = 0;
        if (qtype == ExamQuestion.QuestionType.SINGLE_CHOICE.getType()) {//单选
            if (!StringUtils.isEmpty(rightKey)) {
                returnPoint = answer.toLowerCase().trim().equals(rightKey.toLowerCase().trim()) ? point : 0;
            }
        } else if (qtype == ExamQuestion.QuestionType.MULTIPLE_CHOICE.getType()) {
            if (rightKey != null) {
                returnPoint = LetterUtils.sortLetters(rightKey.trim()).equals(LetterUtils.sortLetters(answer.trim())) ? point : 0;
            }
        } else if (qtype == ExamQuestion.QuestionType.FILL_BLANK.getType()) {
            if (rightKey != null) {
                returnPoint = rightKey.toLowerCase().trim().equals(answer.toLowerCase().trim()) ? point : 0;
            }
        }
        return returnPoint;
    }

    /**
     * 获取试卷每个小题分数的数组
     *
     * @param scorePolicy
     * @param totalPoint
     * @param questionCount
     * @return
     */
    private Integer[] getQuestionScoreArray(String scorePolicy, Integer totalPoint, Integer questionCount) {
        Integer[] scoreArray = new Integer[questionCount];
        if (StringUtils.isEmpty(scorePolicy)) {//没有设置评分策略的情况
            if (totalPoint % questionCount == 0) {//能整除的情况
                for (int i = 0; i < questionCount; i++) {
                    scoreArray[i] = totalPoint / questionCount;
                }
            } else {//不能整除的情况
                int score = totalPoint / questionCount;
                int lastScore = score + totalPoint % questionCount;
                for (int i = 0; i < questionCount - 1; i++) {
                    scoreArray[i] = score;
                }
                scoreArray[questionCount - 1] = lastScore;
            }
        } else if (!scorePolicy.contains("+")) {//每个小题相同分数
            if (scorePolicy.contains("x") || scorePolicy.contains("X")) {
                String[] policyArray = scorePolicy.toLowerCase().split("x");
                int score = Integer.parseInt(policyArray[0]);
                for (int i = 0; i < questionCount; i++) {
                    scoreArray[i] = score;
                }
            }
        } else {
            String[] policyArray = scorePolicy.split("\\+");
            int scoreIndex = 0;
            for (String subPolicy : policyArray) {
                String[] subPolicyArr = subPolicy.toLowerCase().split("x");
                for (int i = 0; i < Integer.parseInt(subPolicyArr[1]); i++) {
                    if (scoreIndex < scoreArray.length) {
                        scoreArray[scoreIndex] = Integer.parseInt(subPolicyArr[0]);
                        scoreIndex++;
                    }
                }
            }
        }
        return scoreArray;
    }

    /**
     * 试卷提交到缓存队列中
     *
     * @param history
     */
    @Override
    public void submitToCache(ExamHistory history) {
        redisCacheUtils.pushToQueue(EXAM_HISTORY_QUEUE_KEY, history);
    }


    @Override
    public ExamHistory popFromQueue() {
        ExamHistory history = (ExamHistory) redisCacheUtils.bRPopFromQueue(EXAM_HISTORY_QUEUE_KEY);
        return history;
    }

    @Override
    public void deleteHistory(ExamHistory history) {
        if (history != null) {
            ExamHistoryItem deleteCondition = new ExamHistoryItem();
            deleteCondition.setHistoryId(history.getId());
            examHistoryDAO.deleteByPrimaryKey(history.getId());
            examHistoryItemDAO.delete(deleteCondition);
        }
    }

    /**
     * 根据条件查找考试记录
     *
     * @param condition
     * @return
     */
    @Override
    public ExamHistory findHistory(ExamHistory condition) {
        ExamHistory history = examHistoryDAO.selectOne(condition);
        return history;
    }

    /**
     * 计算考试得分
     *
     * @param history
     * @return
     */
    @Override
    public ExamHistory score(ExamHistory history) {

        ExamPaper paper = findExamPaper(history.getPaperId());
        //如果试卷没有设置总分 则默认为100分
        Integer totalPoint = paper.getTotalPoint() == null || paper.getTotalPoint() == 0 ? 100 : paper.getTotalPoint();
        Map<Integer, Integer> scoreMap = findPaperQuestionScore(paper.getId());
//        Integer[] scoreArray = getQuestionScoreArray(scorePolicy, totalPoint, paper.getQuestionList().size());
        history.setFinished(true);
        history.setTotalPoint(totalPoint);
        history.setSubmitTime(new Date());
        //examHistoryDAO.insert(history);
        Map<Integer, String> answerMap = Maps.newHashMap();
        if (history.getItems() != null) {
            for (ExamHistoryItem item : history.getItems()) {
                answerMap.put(item.getQuestionId(), item.getAnswer());
            }
        }
        history.getItems().clear();
        Integer totalScore = 0;
        ExamHistoryStateDTO historyState = new ExamHistoryStateDTO();
        for (int i = 0; i < paper.getQuestionList().size(); i++) {
            ExamQuestion question = paper.getQuestionList().get(i);
            Integer score = scoreMap.get(question.getId());
            if (score == null) {
                score = 0;
            }
            if (answerMap.get(question.getId()) != null) {
                ExamHistoryItem item = new ExamHistoryItem();
                item.setRightKey(question.getRightKey());
                item.setAnswer(StringUtils.isEmpty(answerMap.get(question.getId())) ? "" : answerMap.get(question.getId()));
                item.setPoint(score);
                item.setQuestionId(question.getId());
                item.setScore(scoreQuestion(question.getQtype(), item.getPoint(), question.getRightKey(), item.getAnswer()));
                history.getItems().add(item);
                if (item.getScore() > 0) {
                    historyState.setRightCount(historyState.getRightCount() + 1);
                } else {
                    historyState.setErrorCount(historyState.getErrorCount() + 1);
                }
                totalScore += item.getScore();
            } else {
                historyState.setErrorCount(historyState.getErrorCount() + 1);
            }
            //examHistoryItemDAO.insert(item);
        }
        historyState.setTotalCount(historyState.getRightCount() + historyState.getErrorCount());
        historyState.setScore(totalScore);
        history.setHistoryState(historyState);
        history.setScore(totalScore);
        return history;
    }

    /**
     * 答题历史(医生详情)
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<ExamHistoryDTO> getExamHistory(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<ExamHistoryDTO> page = (Page<ExamHistoryDTO>) meetExamDAO.getExamHistory(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 查询考试信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public MeetExam findExam(String meetId, Integer moduleId) {
        MeetExam condition = new MeetExam();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        MeetExam exam = meetExamDAO.selectOne(condition);
        return exam;
    }

    /**
     * 修改会议考试记录
     *
     * @param exam
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'meet_exam_'+#exam.meetId")
    public void updateMeetExam(MeetExam exam) {
        meetExamDAO.updateByPrimaryKeySelective(exam);
    }


    /**
     * 删除试卷中的试题
     *
     * @param paperId
     * @param questionId
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'exam_paper_'+#paperId")
    public void deleteQuestion(Integer paperId, Integer questionId) {
        examQuestionDAO.deleteByPrimaryKey(questionId);
        ExamPaperQuestion condition = new ExamPaperQuestion();
        condition.setQuestionId(questionId);
        condition.setPaperId(paperId);
        examPaperQuestiondDAO.delete(condition);
        //重置试题顺序
        List<ExamPaperQuestion> list = examPaperQuestiondDAO.findPaperQuestion(paperId);
        for (int i = 0; i < list.size(); i++) {
            ExamPaperQuestion question = list.get(i);
            if (question.getSort() != i + 1) {
                question.setSort(i + 1);
                examPaperQuestiondDAO.updateByPrimaryKeySelective(question);
            }
        }
        updatePaperPoint(paperId);
    }

    /**
     * 添加试题
     *
     * @param paperId
     * @param question
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'exam_paper_'+#paperId")
    public void addQuestion(Integer paperId, ExamQuestion question) {
        ExamPaper paper = examPaperDAO.selectByPrimaryKey(paperId);
        question.setCategory(paper.getCategory());
        question.setOwner(paper.getOwner());
        question.setCreateTime(new Date());
        examQuestionDAO.insert(question);
        ExamPaperQuestion condition = new ExamPaperQuestion();
        condition.setPaperId(paperId);
        int count = examPaperQuestiondDAO.selectCount(condition);
        ExamPaperQuestion epq = new ExamPaperQuestion();
        epq.setPaperId(paperId);
        epq.setQuestionId(question.getId());
        epq.setPoint(question.getPoint());
        epq.setSort(count + 1);
        examPaperQuestiondDAO.insert(epq);
        updatePaperPoint(paperId);
    }


    private void updatePaperPoint(Integer paperId) {
        Integer totalPoint = examPaperQuestiondDAO.findPaperTotalPoint(paperId);
        ExamPaper paper = examPaperDAO.selectByPrimaryKey(paperId);
        paper.setTotalPoint(totalPoint);
        examPaperDAO.updateByPrimaryKeySelective(paper);
    }

    /**
     * 修改试题信息
     *
     * @param paperId
     * @param question
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'exam_paper_'+#paperId")
    public void updateQuestion(Integer paperId, ExamQuestion question) {
        examQuestionDAO.updateByPrimaryKeySelective(question);
        ExamPaperQuestion condition = new ExamPaperQuestion();
        condition.setPaperId(paperId);
        condition.setQuestionId(question.getId());
        ExamPaperQuestion epq = examPaperQuestiondDAO.selectOne(condition);
        epq.setPoint(question.getPoint());
        examPaperQuestiondDAO.updateByPrimaryKeySelective(epq);
        updatePaperPoint(paperId);
    }

    /**
     * 根据id获取考试信息
     *
     * @param examId
     * @return
     */
    @Override
    public MeetExam findMeetExamById(Integer examId) {
        MeetExam exam = meetExamDAO.selectByPrimaryKey(examId);
        return exam;
    }

    /**
     * 根据会议ID 获取试卷ID
     *
     * @param meetId
     * @return
     */
    @Override
    public Integer findPaperIdById(String meetId) {
        return examHistoryDAO.findPaperIdById(meetId);
    }

    @Override
    public MyPage<ExamHistoryDataDTO> findTotalCountByQuestion(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<ExamHistoryDataDTO> page = (Page) examHistoryDAO.findTotalCountByQuestion(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public Map<String, Object> findRightCountByQuestion(Map<String, Object> params) {
        List<ExamHistoryDataDTO> rightList = examHistoryDAO.findRightCountByQuestion(params);
        Map<String, Object> rightMap = null;
        if (rightList != null && rightList.size() != 0) {
            Integer rightCount = 0;
            Integer sort = 0;
            rightMap = new HashMap<>();
            for (ExamHistoryDataDTO his : rightList) {
                sort = his.getSort();
                rightCount = his.getRightCount();
                rightMap.put(sort.toString(), rightCount);
            }

        }
        return rightMap;
    }

    /**
     * 查询每道题的正确答案及用户选择的答案记录
     *
     * @param params
     * @return
     */
    public Map<String, Object> findExamAnswerRecord(Map<String, Object> params) {
        List<ExamHistoryUserDTO> historyList = examHistoryDAO.findExamAnswerRecord(params);
        Map<String, Object> rightMap = new HashMap<String, Object>();
        if (!CheckUtils.isEmpty(historyList)) {
            String sort = null;
            for (ExamHistoryUserDTO his : historyList) {
                sort = his.getSort().toString();
                if (!CheckUtils.isEmpty(his.getAnswer())
                        && (his.getAnswer().equals(his.getRightkey()))) {
                    if (rightMap.containsKey(sort)) {
                        rightMap.put(sort.toString(), (Integer) rightMap.get(sort) + 1);
                    } else {
                        rightMap.put(sort.toString(), 1);
                    }
                }
            }
        }
        return rightMap;
    }

    /**
     * 查询考题数据正确率 错误率 导出excel
     *
     * @param params
     * @return
     */
    @Override
    public List<ExamHistoryDataDTO> findExamDataToExcel(Map<String, Object> params) {
        List<ExamHistoryDataDTO> hisList = examHistoryDAO.findTotalCountByQuestion(params);
        return hisList;
    }

    /**
     * 查询用户考试成绩
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<ExamHistoryRecordDTO> findExamHisRecord(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<ExamHistoryRecordDTO> page = (Page) examHistoryDAO.findExamHisRecord(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 查询用户考试成绩 导出Excel
     *
     * @param params
     * @return
     */
    @Override
    public List<ExamHistoryRecordDTO> findExamHisRecordExcel(Map<String, Object> params) {
        List<ExamHistoryRecordDTO> exList = examHistoryDAO.findExamHisRecord(params);
        return exList;
    }

    @Override
    public List<ExamHistoryUserDTO> findExamHistoryExcelByUserid(Map<String, Object> params) {
        List<ExamHistoryUserDTO> userDTOlist = examHistoryDAO.findExamHistoryByUserid(params);
        return userDTOlist;
    }

    /**
     * 查找用户选择的答案
     *
     * @param questionId
     * @param historyId
     * @return
     */
    @Override
    public String findAnswer(Integer questionId, Integer historyId) {
        ExamHistoryItem item = new ExamHistoryItem();
        item.setQuestionId(questionId);
        item.setHistoryId(historyId);
        item = examHistoryItemDAO.selectOne(item);
        return item == null ? null : item.getAnswer();
    }

    /**
     * 查询题目数量
     *
     * @param meetId
     * @return
     */
    @Override
    public List<Integer> findSortList(String meetId) {
        List<Integer> list = examQuestionDAO.findQuestionSort(meetId);
        return list;
    }

    /**
     * 根据会议ID查询 是否限制了参加问卷分组用户
     *
     * @param meetId
     * @return
     */
    @Override
    public MeetLimitDTO findGroupIdIsLimit(String meetId) {
        MeetLimitDTO meetLimitDTO = meetAttendDAO.findGroupIdLimitByMeetId(meetId);
        return meetLimitDTO == null ? null : meetLimitDTO;
    }

    /**
     * 查询指定分组用户考试记录
     *
     * @param params
     * @return
     */
    @Override
    public List<AttendExamHistoryDTO> findExamHisUserData(Map<String, Object> params) {
        List<AttendExamHistoryDTO> list = examHistoryDAO.findExamHisRecordByGroupId(params);
        for (AttendExamHistoryDTO examHistoryDTO : list) {
            if (examHistoryDTO.getFinishTimes() == null
                    || examHistoryDTO.getFinishTimes().intValue() == 0) {
                examHistoryDTO.setAttend(false);
            } else {
                examHistoryDTO.setAttend(true);
            }
        }
        return list;
    }

    /**
     * 查询没有指定分组用户考试记录
     *
     * @param params
     * @return
     */
    @Override
    public List<AttendExamHistoryDTO> findExamHis(Map<String, Object> params) {
        List<AttendExamHistoryDTO> list = examHistoryDAO.findExamHisRecordByMeetId(params);
        for (AttendExamHistoryDTO examHistoryDTO : list) {
            if (examHistoryDTO.getFinishTimes().intValue() == 0) {
                examHistoryDTO.setAttend(false);
            } else {
                examHistoryDTO.setAttend(true);
            }
        }
        return list;
    }

    /**
     * 查询用户答题记录
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public List<QuestionOptItemDTO> findOptItemListByUser(String meetId, Integer userId) {
        return examHistoryDAO.findOptItemByUser(meetId, userId);
    }

    /**
     * 查询用户考试分数
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public Integer findUserExamScore(@Param("meetId") String meetId, @Param("userId") Integer userId) {
        Integer score = examHistoryDAO.findUserExamScore(meetId, userId);
        return score == null ? 0 : score;
    }

    public List<ExamPaperQuestion> findQuestionListByPaperId(Integer paperId){
        List<ExamPaperQuestion> questionList = examPaperQuestiondDAO.findPaperQuestion(paperId);
        return questionList;
    }

    public Integer insertExamPaperQuestion(ExamPaperQuestion examPaperQuestion){
        return examPaperQuestiondDAO.insert(examPaperQuestion);
    }
}
