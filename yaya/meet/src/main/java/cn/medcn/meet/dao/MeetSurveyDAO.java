package cn.medcn.meet.dao;

import cn.medcn.meet.dto.AttendSurveyUserDataDTO;
import cn.medcn.meet.dto.MeetSurveyDetailDTO;
import cn.medcn.meet.dto.SurveyHistoryDTO;
import cn.medcn.meet.model.MeetSurvey;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetSurveyDAO extends Mapper<MeetSurvey> {
    List<SurveyHistoryDTO> getSurveyHistory(Map<String, Object> params);

    MeetSurveyDetailDTO findUserInfo(Map<String, Object> map);

    List<AttendSurveyUserDataDTO> findGroupUserData(Map<String, Object> map);
}
