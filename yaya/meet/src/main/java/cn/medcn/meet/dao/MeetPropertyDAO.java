package cn.medcn.meet.dao;

import cn.medcn.meet.model.MeetProperty;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface MeetPropertyDAO extends Mapper<MeetProperty> {

    MeetProperty findProperty(@Param("meetId") String meetId);
}
