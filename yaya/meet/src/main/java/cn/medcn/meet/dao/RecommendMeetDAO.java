package cn.medcn.meet.dao;

import cn.medcn.meet.model.Recommend;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author：jianliang
 * @Date: Create in 15:11 2017/11/27
 */
public interface RecommendMeetDAO extends Mapper<Recommend>{
    List<Recommend> recommendMeetList(Map<String, Object> params);
}
