package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.common.utils.LetterUtils;
import cn.medcn.meet.dao.*;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.SurveyService;
import com.alibaba.fastjson.JSONArray;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
@Service
public class SurveyServiceImpl extends BaseServiceImpl<MeetSurvey> implements SurveyService {

    @Autowired
    private MeetSurveyDAO meetSurveyDAO;

    @Autowired
    private SurveyPaperDAO surveyPaperDAO;

    @Autowired
    private SurveyQuestionDAO surveyQuestionDAO;

    @Autowired
    private SurveyHistoryDAO surveyHistoryDAO;

    @Autowired
    private SurveyHistoryItemDAO surveyHistoryItemDAO;

    @Autowired
    private MeetDAO meetDAO;

    @Autowired
    private MeetAttendDAO meetAttendDAO;

    @Override
    public Mapper<MeetSurvey> getBaseMapper() {
        return meetSurveyDAO;
    }

    /**
     * 批量导入问卷调查
     *
     * @param paperId
     * @param questionList
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'survey_question_'+#paperId")
    public void executeImport(Integer paperId, List<SurveyQuestion> questionList) {
        SurveyQuestion condition = new SurveyQuestion();
        condition.setPaperId(paperId);
        surveyQuestionDAO.delete(condition);
        for(SurveyQuestion question:questionList){
            question.setPaperId(paperId);
            surveyQuestionDAO.insert(question);
        }
    }

    /**
     * 将从excel解析出来的对象转换成问卷对象列表
     *
     * @param objectList
     * @return
     */
    @Override
    public List<SurveyQuestion> parseSurveyQuestions(List<Object[]> objectList) {
        List<SurveyQuestion> questionList = Lists.newArrayList();
        int sort = 1;
        for(Object[] objArray:objectList){
            SurveyQuestion question = new SurveyQuestion();
            if(((String)objArray[0]).startsWith(ExamQuestion.QuestionType.SINGLE_CHOICE.getLabel())){
                question.setQtype(ExamQuestion.QuestionType.SINGLE_CHOICE.getType());
            }else if(((String)objArray[0]).startsWith(ExamQuestion.QuestionType.MULTIPLE_CHOICE.getLabel())){
                question.setQtype(ExamQuestion.QuestionType.MULTIPLE_CHOICE.getType());
            }else if(((String)objArray[0]).startsWith(ExamQuestion.QuestionType.FILL_BLANK.getLabel())){
                question.setQtype(ExamQuestion.QuestionType.FILL_BLANK.getType());
            }else if(((String)objArray[0]).startsWith(ExamQuestion.QuestionType.QUESTION_ANSERE.getLabel())){
                question.setQtype(ExamQuestion.QuestionType.QUESTION_ANSERE.getType());
            }else{
                question.setQtype(-1);
            }
            question.setTitle((String) objArray[1]);
            question.setSort(sort);
            JSONArray array = new JSONArray();
            for(int answerIndex = 2; answerIndex < objArray.length; answerIndex++){
                if (objArray[answerIndex]!=null && !"".equals(objArray[answerIndex])){
                    KeyValuePair pair = new KeyValuePair(LetterUtils.numberToLetter(answerIndex-1), (String) objArray[answerIndex]);
                    array.add(pair);
                }
            }
            if(!array.isEmpty()){
                question.setOptions(array.toJSONString());
            }

            questionList.add(question);
            sort++;
        }
        return questionList;
    }

    /**
     * 创建问卷 并返回id
     *
     * @param paper
     * @return
     */
    @Override
    public Integer addSurveyPaper(SurveyPaper paper) {
        surveyPaperDAO.insert(paper);
        return paper.getId();
    }

    /**
     * 返回问卷调查对象
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'meet_survey_'+#meetId")
    public MeetSurvey findMeetSurvey(String meetId, Integer moduleId) {
        MeetSurvey condition = new MeetSurvey();
        //condition.setModuleId(moduleId);
        condition.setMeetId(meetId);
        MeetSurvey survey = meetSurveyDAO.selectOne(condition);
        if(survey != null && survey.getPaperId() != null){
            survey.setSurveyPaper(findSurveyPaper(survey.getPaperId()));
        }
        return survey;
    }


    /**
     * 获取会议问卷模块简单信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public MeetSurvey findMeetSurveySimple(String meetId, Integer moduleId) {
        MeetSurvey condition = new MeetSurvey();
        //condition.setModuleId(moduleId);
        condition.setMeetId(meetId);
        MeetSurvey survey = meetSurveyDAO.selectOne(condition);
        return survey;
    }

    /**
     * 返回问卷对象
     *
     * @param paperId
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'survey_question_'+#paperId")
    public SurveyPaper findSurveyPaper(Integer paperId) {
        SurveyPaper paper = surveyPaperDAO.selectByPrimaryKey(paperId);
        if(paper != null){
            List<SurveyQuestion> questions = findQuestions(paperId);
            paper.setQuestionList(questions);
        }
        return paper;
    }

    /**
     * 问卷提交
     *
     * @param history
     */
    @Override
    public void executeSubmit(SurveyHistory history) {
        history.initItems();
        history.setSubmitTime(new Date());
        SurveyHistory cond = new SurveyHistory();
        cond.setPaperId(history.getPaperId());
        cond.setMeetId(history.getMeetId());
        cond.setUserId(history.getUserId());
        SurveyHistory existHistory = surveyHistoryDAO.selectOne(cond);
        Integer historyId;
        if (existHistory == null) {
            surveyHistoryDAO.insert(history);
            historyId = history.getId();
        } else {
            historyId = existHistory.getId();
            //删除已经存在的答题明细
            SurveyHistoryItem deleteCond = new SurveyHistoryItem();
            deleteCond.setHistoryId(historyId);
            surveyHistoryItemDAO.delete(deleteCond);
        }
        Map<Integer, SurveyHistoryItem> itemMap = Maps.newHashMap();
        if(history.getItems() != null){
            for(SurveyHistoryItem item:history.getItems()){
                itemMap.put(item.getQuestionId(), item);
            }
        }
        List<SurveyQuestion> questions = findQuestions(history.getPaperId());
        if(questions!=null){
            for(SurveyQuestion question:questions){
                SurveyHistoryItem item = itemMap.get(question.getId());
                if(item==null){
                    item = new SurveyHistoryItem();
                    item.setAnswer("");
                    item.setQuestionId(question.getId());
                }
                item.setHistoryId(historyId);
                surveyHistoryItemDAO.insert(item);
            }
        }

    }

    /**
     * 根据问卷ID获取试题列表
     *
     * @param paperId
     * @return
     */
    @Override
    public List<SurveyQuestion> findQuestions(Integer paperId) {
        SurveyQuestion condition = new SurveyQuestion();
        condition.setPaperId(paperId);
        List<SurveyQuestion> list = surveyQuestionDAO.select(condition);
        return list;
    }

    /**
     * 检测用户是否已经提交过
     *
     * @param condition
     * @return
     */
    @Override
    public SurveyHistory findSurveyHistory(SurveyHistory condition) {
        SurveyHistory history = surveyHistoryDAO.selectOne(condition);
        return history;
    }

    /**
     * 问卷历史(医生详情)
     * @param pageable
     * @return
     */
    @Override
    public MyPage<SurveyHistoryDTO> getSurveyHistory(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<SurveyHistoryDTO> page = (Page<SurveyHistoryDTO>)meetSurveyDAO.getSurveyHistory(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 根据会议id和moduleId创建或者修改问卷
     *
     * @param meetId
     * @param moduleId
     * @param userId
     * @return
     */
    @Override
    public Integer insertOrUpdateSurveyPaper(String meetId, Integer moduleId, Integer userId) {
        MeetSurvey survey = findMeetSurvey(meetId, moduleId);
        if(survey.getPaperId() == null){//未关联问卷
            Meet meet = meetDAO.selectByPrimaryKey(meetId);
            SurveyPaper paper = new SurveyPaper();
            paper.setPaperName(meet.getMeetName());
            paper.setOwner(userId);
            paper.setCreateTime(new Date());
            paper.setCategory(meet.getMeetType());
            Integer paperId = addSurveyPaper(paper);
            survey.setPaperId(paperId);
            meetSurveyDAO.updateByPrimaryKeySelective(survey);
            return paperId;
        }else{
            return survey.getPaperId();
        }
    }


    /**
     * 删除问卷试题
     *
     * @param paperId
     * @param questionId
     */
    @Override
    @CacheEvict(value=DEFAULT_CACHE, key = "'survey_question_'+#paperId")
    public void deleteQuestion(Integer paperId, Integer questionId) {
        surveyQuestionDAO.deleteByPrimaryKey(questionId);
    }

    /**
     * 添加问卷试题

     * @param question
     */
    @Override
    @CacheEvict(value=DEFAULT_CACHE, key = "'survey_question_'+#question.paperId")
    public void addQuestion(SurveyQuestion question) {
        surveyQuestionDAO.insert(question);
    }

    /**
     * 修改问卷试题
     *
     * @param question
     */
    @Override
    @CacheEvict(value=DEFAULT_CACHE, key = "'survey_question_'+#question.paperId")
    public void updateQuestion(SurveyQuestion question) {
        surveyQuestionDAO.updateByPrimaryKeySelective(question);
    }

    /**
     * 查询问卷已有题数
     *
     * @param paperId
     * @return
     */
    @Override
    public Integer countQuestions(Integer paperId) {
        SurveyQuestion condition = new SurveyQuestion();
        condition.setPaperId(paperId);
        Integer count = surveyQuestionDAO.selectCount(condition);
        return count;
    }

    /**
     * 查找问卷试题
     *
     * @param id
     * @return
     */
    @Override
    public SurveyQuestion findQuestion(Integer id) {
        SurveyQuestion question = surveyQuestionDAO.selectByPrimaryKey(id);
        return question;
    }

    /**
     * 根据会议ID 分页查询问卷数据
     * @param pageable
     * @return
     */
    @Override
    public MyPage<SurveyQuestion> findSurveyDatas(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<SurveyQuestion> page = (Page)surveyQuestionDAO.findSurveyDatasByMeetId(pageable.getParams());
        return MyPage.page2Mypage(page);

    }

    /**
     * 导出问卷数据分析
     * @param map
     * @return
     */
    @Override
    public List<SurveyRecordDTO> findSurveyToExcel(Map<String, Object> map) {
        List<SurveyRecordDTO> slist = surveyQuestionDAO.findSurveyByMeetId(map);
        return slist;
    }


    /**
     * 根据会议ID 查询所有参加问卷调查的用户记录
     * @return
     */
    @Override
    public MyPage<SurveyHistoryRecordDTO> findSurveyRecord(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<SurveyHistoryRecordDTO> page = (Page) surveyHistoryDAO.findSurveyRecordByMeetId(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 查找用户选择的答案
     * @param questionId
     * @param surveyId
     * @return
     */
    @Override
    public String findAnswer(Integer questionId, Integer surveyId) {
        SurveyHistoryItem item = new SurveyHistoryItem();
        item.setQuestionId(questionId);
        item.setHistoryId(surveyId);
        item = surveyHistoryItemDAO.selectOne(item);
        return item.getAnswer();
    }

    /**
     * 根据题目ID 查询所有用户选择的答案记录
     * @param questionId
     * @return
     */
    @Override
    public List<SurveyRecordItemDTO> findSurveyRecordByQid(Integer questionId) {
        List<SurveyRecordItemDTO> svList = surveyHistoryDAO.findUserSelAnswerByQuestionId(questionId);
        return svList;
    }

    /**
     * 查询题目数
     * @param meetId
     * @return
     */
    @Override
    public List<Integer> findQuestionSort(String meetId) {
        List<Integer> sortList = surveyQuestionDAO.findQuestionSort(meetId);
        return sortList;
    }

    /**
     *  根据会议ID查询 是否限制了参加问卷分组用户
     * @param meetId
     * @return
     */
    @Override
    public MeetLimitDTO findGroupIdIsLimit(String meetId){
        MeetLimitDTO meetLimitDTO = meetAttendDAO.findGroupIdLimitByMeetId(meetId);
        return meetLimitDTO;
    }

    /**
     * 根据分组Id 公众号ID 查询分组下用户基本信息
     * @param map
     * @return
     */
    @Override
    public List<AttendSurveyUserDataDTO> findAttendUserDataByGroupId(Map<String,Object> map){
        return meetSurveyDAO.findGroupUserData(map);
    }

    /**
     * 根据会议ID 用户ID 查询用户答题记录
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public List<QuestionOptItemDTO> findOptItemListByUser(String meetId,Integer userId){
        List<QuestionOptItemDTO> optItemDTOList = surveyHistoryDAO.findOptItemListByUser(meetId,userId);
        return optItemDTOList;
    }

    /**
     * 查询所有用户问卷记录
     * @param map
     * @return
     */
    @Override
   public List<AttendSurveyUserDataDTO> findUserSurveyHis(Map<String,Object> map){
        List<AttendSurveyUserDataDTO> surveyDTOList = null;
        String meetId = map.get("meetId").toString();
        // 查询是否有指定分组用户参加问卷
        MeetLimitDTO meetLimitDTO = findGroupIdIsLimit(meetId);
        if (meetLimitDTO != null ) {
            if (meetLimitDTO.getGroupId() != null
                    && !"".equals(meetLimitDTO.getGroupId())
                    && meetLimitDTO.getGroupId() != 0) {
                map.put("groupId", meetLimitDTO.getGroupId());
                // 指定了分组 查询分组用户的 问卷数据
                surveyDTOList = findAttendUserDataByGroupId(map);
            }
        } else {
            // 没有指定分组，查询所有用参加问卷的数据
            surveyDTOList = surveyHistoryDAO.findUserSurveyHis(map);
        }

        if (!CheckUtils.isEmpty(surveyDTOList)) {
            for (AttendSurveyUserDataDTO userDataDTO : surveyDTOList) {
                // 判断提交问卷时间为空，则该用户没有参加问卷
                if (userDataDTO.getSubmitTime() == null
                        || "".equals(userDataDTO.getSubmitTime())){
                    userDataDTO.setAttend(false);
                }else {
                    userDataDTO.setAttend(true);
                }
            }
        }
        return surveyDTOList;
    }

    /**
     * 查询主观题用户答题记录
     * @param questionId
     * @return
     */
    public List<SubjQuesAnswerDTO> findSubjQuesAnswerByQuestionId(Integer questionId) {
        return surveyHistoryItemDAO.findSubjQuesAnswerByQuestionId(questionId);
    }
}
