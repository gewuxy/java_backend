package cn.medcn.meet.dao;

import cn.medcn.meet.model.MeetRewardHistory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/7/31.
 */

public interface MeetRewardHistoryDAO extends Mapper<MeetRewardHistory> {

    /**
     * 查询用户是否有获取会议象数或学分奖励的记录
     * @param params
     * @return
     */
    List<MeetRewardHistory> findUserGetRewardHistory(Map<String,Object> params);

    /**
     * 查询已经获得奖励的用户数
     * @param params
     * @return
     */
    Integer findGetRewardUserCount(Map<String,Object> params);
}
