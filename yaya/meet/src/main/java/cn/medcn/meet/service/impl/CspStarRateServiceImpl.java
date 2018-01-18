package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.AudioCourseDAO;
import cn.medcn.meet.dao.CspStarRateHistoryDAO;
import cn.medcn.meet.dao.CspStarRateHistoryDetailDAO;
import cn.medcn.meet.dao.CspStarRateOptionDAO;
import cn.medcn.meet.dto.StarRateResultDTO;
import cn.medcn.meet.model.CspStarRateHistory;
import cn.medcn.meet.model.CspStarRateHistoryDetail;
import cn.medcn.meet.model.CspStarRateOption;
import cn.medcn.meet.service.CspStarRateService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2018/1/10.
 */
@Service
public class CspStarRateServiceImpl extends BaseServiceImpl<CspStarRateOption> implements CspStarRateService {

    @Autowired
    protected CspStarRateOptionDAO cspStarRateOptionDAO;

    @Autowired
    protected CspStarRateHistoryDAO cspStarRateHistoryDAO;

    @Autowired
    protected CspStarRateHistoryDetailDAO cspStarRateHistoryDetailDAO;

    @Autowired
    protected AudioCourseDAO audioCourseDAO;

    @Override
    public Mapper<CspStarRateOption> getBaseMapper() {
        return cspStarRateOptionDAO;
    }


    /**
     * 根据课件ID获取星评项
     *
     * @param courseId
     * @return
     */
    @Override
    public List<CspStarRateOption> findRateOptions(Integer courseId) {
        CspStarRateOption cond = new CspStarRateOption();
        cond.setCourseId(courseId);
        List<CspStarRateOption> options = cspStarRateOptionDAO.select(cond);
        return options;
    }

    /**
     * 获取星评平均分
     *
     * @param courseId
     * @return
     */
    @Override
    public List<StarRateResultDTO> findRateResult(Integer courseId) {
        List<StarRateResultDTO> result = null;

        List<CspStarRateOption> options = findRateOptions(courseId);
        if (CheckUtils.isEmpty(options)) {//没有分项评分的情况
            StarRateResultDTO results = cspStarRateHistoryDAO.findRateResultExcludeDetails(courseId);
            result.add(results);
        } else {
            result = cspStarRateHistoryDAO.findRateResultHasDetails(courseId);
        }
        return result;
    }

    /**
     * 用户给课件评分
     *
     * @param history
     */
    @Override
    public void doScore(CspStarRateHistory history) {
        if (history != null) {
            history.setRateTime(new Date());
            if (!CheckUtils.isEmpty(history.getDetails())) {
                //计算综合得分
                int totalScore = 0;
                for (CspStarRateHistoryDetail detail : history.getDetails()) {
                    totalScore += detail.getScore();
                }
                history.setScore(totalScore * 1.0f / history.getDetails().size());

                cspStarRateHistoryDAO.insert(history);

                for (CspStarRateHistoryDetail detail : history.getDetails()) {
                    detail.setHistoryId(history.getId());
                    cspStarRateHistoryDetailDAO.insert(detail);
                }
            } else {
                cspStarRateHistoryDAO.insert(history);
            }

        }
    }

    /**
     * 获取课件及星评基本评分
     * @param courseId
     * @return
     */
    @Override
    public StarRateResultDTO getTotleRateResult(Integer courseId) {
        return cspStarRateHistoryDAO.findRateResultExcludeDetails(courseId);
    }

    /**
     * 获取星评详细
     * @param courseId
     * @return
     */
    @Override
    public List<StarRateResultDTO> getStarRateDetail(Integer courseId) {
            return cspStarRateHistoryDAO.findRateResultHasDetails(courseId);
    }
}
