package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.ReportRegister;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户统计服务
 * Created by lixuan on 2017/12/26.
 */
public interface ReportService extends BaseService<ReportRegister> {

    void executeReportRegister();

    List<Map<String,Object>> packageDistStats(Integer grain, Date date, Date date1);
}
