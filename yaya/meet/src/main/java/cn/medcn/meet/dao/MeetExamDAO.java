package cn.medcn.meet.dao;

import cn.medcn.meet.dto.ExamHistoryDTO;
import cn.medcn.meet.dto.MeetExamDetailDTO;
import cn.medcn.meet.model.MeetExam;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetExamDAO extends Mapper<MeetExam> {
    List<ExamHistoryDTO> getExamHistory(Map<String, Object> params);


    MeetExamDetailDTO getUserInfo(Map<String, Object> map);
}
