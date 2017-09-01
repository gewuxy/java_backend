package cn.medcn.meet.dao;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.ExamHistory;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/26.
 */
public interface ExamHistoryDAO extends Mapper<ExamHistory> {
    /**
     * 根据会议ID 获取试卷ID
     * @param meetId
     * @return
     */
    Integer findPaperIdById(String meetId);

    /**
     * 查询所有题目 一共有多少人答
     * @param params
     * @return
     */
    List<ExamHistoryDataDTO> findTotalCountByQuestion(Map<String,Object> params);

    // 查询每道题答对的人数
    List<ExamHistoryDataDTO> findRightCountByQuestion(Map<String,Object> params);

    // 查询每道题的正确答案及用户选择的答案记录
    List<ExamHistoryUserDTO> findExamAnswerRecord(Map<String,Object> params);

    /**
     * 查询用户考试成绩
     * @param params
     * @return
     */
    List<ExamHistoryRecordDTO> findExamHisRecord(Map<String,Object> params);

    /**
     * 查询用户考试成绩 导出Excel
     * @param params
     * @return
     */
    List<ExamHistoryUserDTO> findExamHistoryByUserid(Map<String,Object> params);

    /**
     * 查询指定分组用户考试记录
     * @param params
     * @return
     */
    List<AttendExamHistoryDTO> findExamHisRecordByGroupId(Map<String,Object> params);

    /**
     * 查询没有指定分组用户考试记录
     * @param params
     * @return
     */
    List<AttendExamHistoryDTO> findExamHisRecordByMeetId(Map<String,Object> params);

    /**
     * 查询用户答题记录
     * @param meetId
     * @param userId
     * @return
     */
    List<QuestionOptItemDTO> findOptItemByUser(@Param("meetId")String meetId, @Param("userId")Integer userId);

    ExamHistory findExamHistory(@Param("examId")Integer examId, @Param("userId")Integer userId);

    /**
     * 查询用户考试分数
     * @param meetId
     * @param userId
     * @return
     */
    Integer findUserExamScore(@Param("meetId")String meetId, @Param("userId")Integer userId);
}
