package cn.medcn.meet.dao;

import cn.medcn.meet.dto.JpushMessageDTO;
import cn.medcn.meet.model.JpushMessage;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/7.
 */
public interface JpushMessageDAO extends Mapper<JpushMessage> {

    List<JpushMessageDTO> findMessage(Map<String, Object> params);

    List<JpushMessageDTO> findHistories(Map<String, Object> params);
}
