package cn.medcn.meet.dao;

import cn.medcn.meet.dto.MeetMessageDTO;
import cn.medcn.meet.model.MeetMessage;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetMessageDAO extends Mapper<MeetMessage> {


    List<MeetMessageDTO> findMeetMessageDTO(Map<String, Object> params);
}
