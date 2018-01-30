package cn.medcn.meet.dao;

import cn.medcn.meet.dto.StarRateResultDTO;
import cn.medcn.meet.model.CspStarRateHistory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2018/1/10.
 */
public interface CspStarRateHistoryDAO extends Mapper<CspStarRateHistory> {

    /**
     * 包含星评明细的评分结果查询
     * @param courseId
     * @return
     */
    List<StarRateResultDTO> findRateResultHasDetails(@Param("courseId") Integer courseId);

    /**
     * 只含有综合评分的星评结果查询
     * @param courseId
     * @return
     */
    StarRateResultDTO findRateResultExcludeDetails(@Param("courseId") Integer courseId);

    int deleteHistoryByCourseId(Integer courseId);
}
