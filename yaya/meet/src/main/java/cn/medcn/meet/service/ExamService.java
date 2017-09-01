package cn.medcn.meet.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/21.
 */
public interface ExamService extends BaseService<MeetExam>{

    String EXAM_CACHE_KEY = "exam_cache_";

    String EXAM_HISTORY_QUEUE_KEY = "exam_history_queue";

    /**
     * 获取会议考试信息(缓存)
     * @param meetId
     * @param moduleId
     * @return
     */
    ExamDTO findMeetExam(String meetId, Integer moduleId);

    /**
     * 查询考试信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetExam findExam(String meetId, Integer moduleId);

    /***
     * 导入试卷
     * @param paperId
     * @param questionList
     */
    void executeImportPaper(Integer paperId, List<ExamQuestion> questionList);

    List<ExamQuestion> parseExamQuestions(List<Object[]> objects);

    ExamPaper findExamPaper(Integer paperId);

    Integer addPaper(ExamPaper examPaper);

    /**
     * 添加试题
     * @param question
     */
    int addQuestion(ExamQuestion question);

    /**
     * 根据ID返回试题信息
     * @param paperId
     * @param questionId
     * @return
     */
    ExamQuestion findQuestion(Integer paperId, Integer questionId);

    /**
     * 判断考试是否已经结束
     * @param meetId
     * @param moduleId
     * @return
     */
    boolean checkExamFinished(String meetId, Integer moduleId);

    /**
     * 考试提交
     * @param history
     */
    Integer saveHistory(ExamHistory history) throws SystemException;

    /**
     * 试卷提交到缓存队列中
     * @param history
     */
    void submitToCache(ExamHistory history);


    ExamHistory popFromQueue();

    void deleteHistory(ExamHistory history);

    /**
     * 根据条件查找考试记录
     * @param condition
     * @return
     */
    ExamHistory findHistory(ExamHistory condition);

    /**
     * 计算考试得分
     * @param history
     * @return
     */
    ExamHistory score(ExamHistory history);

    MyPage<ExamHistoryDTO> getExamHistory(Pageable pageable);

    /**
     * 修改会议考试记录
     * @param exam
     */
    void updateMeetExam(MeetExam exam);

    /**
     * 删除试卷中的试题
     * @param paperId
     * @param questionId
     */
    void deleteQuestion(Integer paperId, Integer questionId);

    /**
     * 添加试题
     * @param paperId
     * @param question
     */
    void addQuestion(Integer paperId, ExamQuestion question);

    /**
     * 修改试题信息
     * @param paperId
     * @param question
     */
    void updateQuestion(Integer paperId, ExamQuestion question);

    /**
     * 根据id获取考试信息
     * @param examId
     * @return
     */
    MeetExam findMeetExamById(Integer examId);

    /**
     * 根据会议ID 获取试卷ID
     * @param meetId
     * @return
     */
    Integer findPaperIdById(String meetId);

    /**
     * 查询所有题目 一共有多少人答
     * @param pageable
     * @return
     */
    MyPage<ExamHistoryDataDTO> findTotalCountByQuestion(Pageable pageable);

    // 查询每道题答对的人数
    Map<String,Object> findRightCountByQuestion(Map<String,Object> params);

    // 查询每道题的正确答案及用户选择的答案记录
    Map<String,Object> findExamAnswerRecord(Map<String,Object> params);

    /**
     * 查询考题数据正确率 错误率 导出excel
     * @param params
     * @return
     */
    List<ExamHistoryDataDTO> findExamDataToExcel(Map<String,Object> params);

    /**
     * 查询用户考试成绩
     * @param pageable
     * @return
     */
    MyPage<ExamHistoryRecordDTO> findExamHisRecord(Pageable pageable);

    /**
     * 查询用户考试成绩 导出Excel
     * @param params
     * @return
     */
    List<ExamHistoryRecordDTO> findExamHisRecordExcel(Map<String,Object> params);

    /**
     * 查询用户的考试成绩  导出Excel
     * @param params
     * @return
     */
    List<ExamHistoryUserDTO> findExamHistoryExcelByUserid(Map<String,Object> params);

    /**
     * 查找用户选择的答案
     * @param questionId
     * @param historyId
     * @return
     */
    String findAnswer(Integer questionId, Integer historyId);

    /**
     * 查询题目数量
     * @param meetId
     * @return
     */
    List<Integer> findSortList(String meetId);

    /**
     *  根据会议ID查询 是否限制了参加考试 分组用户
     * @param meetId
     * @return
     */
    MeetLimitDTO findGroupIdIsLimit(String meetId);

    /**
     * 查询 参加考试用户信息
     * @param params
     * @return
     */
    List<AttendExamHistoryDTO> findExamHisUserData(Map<String,Object> params);

    /**
     * 查询参加考试用户记录
     * @param params
     * @return
     */
    List<AttendExamHistoryDTO> findExamHis(Map<String,Object> params);

    /**
     * 查询用户考试答题记录
     * @param meetId
     * @param userId
     * @return
     */
    List<QuestionOptItemDTO> findOptItemListByUser(String meetId,Integer userId);

    /**
     * 查询用户考试分数
     * @param meetId
     * @param userId
     * @return
     */
    Integer findUserExamScore(String meetId, Integer userId);


    List<ExamPaperQuestion> findQuestionListByPaperId(Integer paperId);

    Integer insertExamPaperQuestion(ExamPaperQuestion examPaperQuestion);
}
