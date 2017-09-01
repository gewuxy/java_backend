package cn.medcn.meet.dao;

import cn.medcn.meet.model.MeetNotify;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/8/9.
 */
public interface MeetNotifyDAO extends Mapper<MeetNotify> {

    /**
     * 查询出需要发送的通知
     * @param notifyTime
     * @return
     */
    List<MeetNotify> findLegalNotifies(@Param("notifyTime")Long notifyTime);
}
