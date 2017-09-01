package cn.medcn.meet.dao;

import cn.medcn.meet.dto.MeetSignHistoryDTO;
import cn.medcn.meet.model.MeetSign;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetSignDAO extends Mapper<MeetSign> {

    List<MeetSignHistoryDTO> findSignRecordByMeetId(Map<String,Object> params);
}
