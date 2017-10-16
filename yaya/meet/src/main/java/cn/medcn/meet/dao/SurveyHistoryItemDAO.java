package cn.medcn.meet.dao;

import cn.medcn.meet.dto.SubjQuesAnswerDTO;
import cn.medcn.meet.model.SurveyHistoryItem;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
public interface SurveyHistoryItemDAO extends Mapper<SurveyHistoryItem> {

    List<SubjQuesAnswerDTO> findSubjQuesAnswerByQuestionId(@Param("questionId") Integer questionId);
}
