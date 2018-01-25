package cn.medcn.meet.dao;

import cn.medcn.meet.model.CspStarRateHistoryDetail;
import com.github.abel533.mapper.Mapper;

/**
 * Created by lixuan on 2018/1/10.
 */
public interface CspStarRateHistoryDetailDAO extends Mapper<CspStarRateHistoryDetail> {
    int deleteDetailByCourseId(Integer courseId);
}
