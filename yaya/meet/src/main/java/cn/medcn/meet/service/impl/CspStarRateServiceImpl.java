package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.AudioCourseDAO;
import cn.medcn.meet.dao.CspStarRateHistoryDAO;
import cn.medcn.meet.dao.CspStarRateHistoryDetailDAO;
import cn.medcn.meet.dao.CspStarRateOptionDAO;
import cn.medcn.meet.dto.StarRateResultDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.CspStarRateHistory;
import cn.medcn.meet.model.CspStarRateHistoryDetail;
import cn.medcn.meet.model.CspStarRateOption;
import cn.medcn.meet.service.CspStarRateService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        List<StarRateResultDTO> result = new ArrayList<>();
        if (course != null) {
            if (course.getStarRateType() == null
                    || course.getStarRateType().intValue() == AudioCourse.StarRateType.close.ordinal()) {//未开启星评的情况
                return result;
            } else if (course.getStarRateType().intValue() == AudioCourse.StarRateType.simple.ordinal()) {//简单评分的情况
                return cspStarRateHistoryDAO.findRateResultExcludeDetails(courseId);
            } else {//包含星评明细的情况
                return cspStarRateHistoryDAO.findRateResultHasDetails(courseId);
            }
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
            cspStarRateHistoryDAO.insert(history);

            if (!CheckUtils.isEmpty(history.getDetails())) {
                for (CspStarRateHistoryDetail detail : history.getDetails()) {
                    detail.setHistoryId(history.getId());
                    cspStarRateHistoryDetailDAO.insert(detail);
                }
            }
        }
    }
}
