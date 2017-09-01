package cn.medcn.meet.dao;

import cn.medcn.meet.model.MeetLearningRecord;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Liuchangling on 2017/7/31.
 */
public interface MeetLearningRecordDAO extends Mapper<MeetLearningRecord> {

    List<MeetLearningRecord> findLearningRecordList(@Param("meetId") String meetId, @Param("userId") Integer userId);
}
