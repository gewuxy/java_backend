package cn.medcn.meet.dao;

import cn.medcn.meet.model.AudioHistory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lixuan on 2017/5/2.
 */
public interface AudioHistoryDAO extends Mapper<AudioHistory> {
    // 查询用户观看的ppt页数
    Integer findUserViewPPTCount(@Param("meetId") String meetId,@Param("userId") Integer userId);
}
