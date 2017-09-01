package cn.medcn.meet.dao;

import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.SurveyHistory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/26.
 */
public interface SurveyHistoryDAO extends Mapper<SurveyHistory>{

    List<SurveyRecordItemDTO> findUserSelAnswerByQuestionId(@Param("questionId") Integer questionId);

    /**
     * 根据会议ID 查询所有参加问卷调查的用户记录
     * @param params
     * @return
     */
    List<SurveyHistoryRecordDTO> findSurveyRecordByMeetId(Map<String,Object> params);

    /**
     * 根据会议ID 用户ID 查询用户答题记录
     * @param meetId
     * @param userId
     * @return
     */
    List<QuestionOptItemDTO> findOptItemListByUser(@Param("meetId")String meetId,@Param("userId")Integer userId);

    /**
     * 查询所有用户问卷记录
     * @param map
     * @return
     */
    List<AttendSurveyUserDataDTO> findUserSurveyHis(Map<String,Object> map);

}
