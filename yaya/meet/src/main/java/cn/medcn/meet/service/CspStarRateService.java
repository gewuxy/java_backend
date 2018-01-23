package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.StarRateInfoDTO;
import cn.medcn.meet.dto.StarRateResultDTO;
import cn.medcn.meet.model.CspStarRateHistory;
import cn.medcn.meet.model.CspStarRateOption;

import java.util.List;

/**
 * 星评服务接口
 * Created by lixuan on 2018/1/10.
 */
public interface CspStarRateService extends BaseService<CspStarRateOption> {

    /**
     * 根据课件ID获取星评项
     * @param courseId
     * @return
     */
    List<CspStarRateOption> findRateOptions(Integer courseId);

    /**
     * 获取星评平均分
     * @param courseId
     * @return
     */
    List<StarRateResultDTO> findRateResult(Integer courseId);

    /**
     * 获取最终星评结果
     * @param courseId
     * @return
     */
    StarRateInfoDTO findFinalRateResult(Integer courseId);

    /**
     * 用户给课件评分
     * @param history
     */
    void doScore(CspStarRateHistory history);

    /**
     * 获取课件及星评基本评分
     * @param courseId
     * @return
     */
    StarRateResultDTO getTotleRateResult(Integer courseId);

    /**
     * 获取星评详细
     * @param courseId
     * @return
     */
    List<StarRateResultDTO> getStarRateDetail(Integer courseId);

    /**
     * 判断当前用户是否已经评过分
     * @param courseId
     * @param ticket
     * @return
     */
    StarRateInfoDTO findRateHistory(Integer courseId, String ticket);
}
