package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.MeetSurvey;
import cn.medcn.meet.model.SurveyHistory;
import cn.medcn.meet.model.SurveyPaper;
import cn.medcn.meet.model.SurveyQuestion;
import com.github.pagehelper.Page;
import cn.medcn.meet.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface SurveyService extends BaseService<MeetSurvey>{
    /**
     * 批量导入问卷调查
     * @param paperId
     * @param questionList
     */
    void executeImport(Integer paperId, List<SurveyQuestion> questionList);

    /**
     * 将从excel解析出来的对象转换成问卷对象列表
     * @param objectList
     * @return
     */
    List<SurveyQuestion> parseSurveyQuestions(List<Object[]> objectList);

    /**
     * 创建问卷 并返回id
     * @param paper
     * @return
     */
    Integer addSurveyPaper(SurveyPaper paper);

    /**
     * 返回问卷调查对象
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetSurvey findMeetSurvey(String meetId, Integer moduleId);

    /**
     * 获取会议问卷模块简单信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetSurvey findMeetSurveySimple(String meetId, Integer moduleId);

    /**
     * 返回问卷对象
     * @param paperId
     * @return
     */
    SurveyPaper findSurveyPaper(Integer paperId);

    /**
     * 问卷提交
     * @param history
     */
    void executeSubmit(SurveyHistory history);

    /**
     * 根据问卷ID获取试题列表
     * @param paperId
     * @return
     */
    List<SurveyQuestion> findQuestions(Integer paperId);

    /**
     * 检测用户是否已经提交过
     * @param history
     * @return
     */
    SurveyHistory findSurveyHistory(SurveyHistory history);

    MyPage<SurveyHistoryDTO> getSurveyHistory(Pageable pageable);

    /**
     * 根据会议id和moduleId创建或者修改问卷
     * @param meetId
     * @param moduleId
     * @param userId
     * @return
     */
    Integer insertOrUpdateSurveyPaper(String meetId, Integer moduleId, Integer userId);

    /**
     * 删除问卷试题
     * @param paperId
     * @param questionId
     */
    void deleteQuestion(Integer paperId,Integer questionId);

    /***
     * 添加问卷试题
     * @param question
     */
    void addQuestion(SurveyQuestion question);

    /**
     * 修改问卷试题
     * @param question
     */
    void updateQuestion(SurveyQuestion question);

    /**
     * 查询问卷已有题数
     * @param paperId
     * @return
     */
    Integer countQuestions(Integer paperId);

    /**
     * 查找问卷试题
     * @param id
     * @return
     */
    SurveyQuestion findQuestion(Integer id);


    /**
     * 根据会议ID 分页查询问卷数据
     * @param pageable
     * @return
     */
    MyPage<SurveyQuestion> findSurveyDatas(Pageable pageable);


    /**
     * 查询用户 问卷数据
     * @param questionId
     * @return
     */
    Map<String,Object> findSurveyHis(Integer questionId);

    /**
     * 根据会议ID 查询问卷数据
     * @param map
     * @return
     */
    List<SurveyRecordDTO> findSurveyToExcel(Map<String,Object> map);

    /**
     * 根据会议ID 查询所有参加问卷调查的用户记录
     * @param pageable
     * @return
     */
    MyPage<SurveyHistoryRecordDTO> findSurveyRecord(Pageable pageable);

    /**
     * 导出 所有参加问卷调查的用户记录
     * @param map
     * @return
     */
    List<SurveyHistoryRecordDTO> findSurveyRecordExcel(Map<String,Object> map);

    String findAnswer(Integer questionId, Integer surveyId);


    /**
     * 根据题目ID 查询所有用户选择的选项记录
     * @return
     */
    List<SurveyRecordItemDTO> findSurveyRecordByQid(Integer questionId);

    /**
     * 查询一共多少道题目
     * @param meetId
     * @return
     */
    List<Integer> findQuestionSort(String meetId);

    /**
     *  根据会议ID查询 是否限制了参加问卷分组用户
     * @param meetId
     * @return
     */
    MeetLimitDTO findGroupIdIsLimit(String meetId);

    /**
     * 根据分组Id 公众号ID 查询分组下用户基本信息
     * @param map
     * @return
     */
    List<AttendSurveyUserDataDTO> findAttendUserDataByGroupId(Map<String,Object> map);

    /**
     * 根据会议ID 用户ID 查询用户答题记录
     * @param meetId
     * @param userId
     * @return
     */
    List<QuestionOptItemDTO> findOptItemListByUser(String meetId,Integer userId);

    /**
     * 查询所有用户问卷记录
     * @param map
     * @return
     */
    List<AttendSurveyUserDataDTO> findUserSurveyHis(Map<String,Object> map);
}
