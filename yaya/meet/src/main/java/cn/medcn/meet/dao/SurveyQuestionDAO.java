package cn.medcn.meet.dao;

import cn.medcn.meet.dto.SurveyRecordDTO;
import cn.medcn.meet.model.SurveyQuestion;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/26.
 */
public interface SurveyQuestionDAO extends Mapper<SurveyQuestion> {

    List<SurveyQuestion> findQuestionsByPaperId(@Param("paperId")Integer paperId);

    List<SurveyQuestion> findSurveyDatasByMeetId(Map<String,Object> params);

    List<SurveyRecordDTO> findSurveyByMeetId(Map<String,Object> params);

    List<Integer> findQuestionSort(@Param("meetId")String meetId);
}
