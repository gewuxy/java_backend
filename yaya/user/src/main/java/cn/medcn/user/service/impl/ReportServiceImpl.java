package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.ReportRegisterDAO;
import cn.medcn.user.dto.ReportRegisterDetailDTO;
import cn.medcn.user.model.ReportRegister;
import cn.medcn.user.service.ReportService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/12/26.
 */
@Service
public class ReportServiceImpl extends BaseServiceImpl<ReportRegister> implements ReportService {

    @Autowired
    protected ReportRegisterDAO reportRegisterDAO;

    @Override
    public Mapper<ReportRegister> getBaseMapper() {
        return reportRegisterDAO;
    }

    @Override
    public void executeReportRegister() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = format.format(now);
        String startTime = nowStr + " 00:00:00";
        String endTime = nowStr + " 23:59:59";
        List<ReportRegisterDetailDTO> detailList = reportRegisterDAO.findTodayRegisterUsers(startTime, endTime);

        ReportRegister registerHome = new ReportRegister();
        registerHome.setRegisterTime(now);
        registerHome.setAbroad(false);

        ReportRegister registerAbroad = new ReportRegister();
        registerAbroad.setRegisterTime(now);
        registerAbroad.setAbroad(true);

        for (ReportRegisterDetailDTO detail : detailList) {
            if (detail.getAbroad()) {
                modifyDetailInfo(registerAbroad, detail.getRegisterFrom(), detail.getReportCount());
            } else {
                modifyDetailInfo(registerHome, detail.getRegisterFrom(), detail.getReportCount());
            }
        }

        reportRegisterDAO.insert(registerAbroad);
        reportRegisterDAO.insert(registerHome);
    }


    protected void modifyDetailInfo(ReportRegister report, Integer registerFrom, Integer reportCount){
        if (report != null) {
            switch (registerFrom) {
                case 1 :
                    report.setWeiXinCount(reportCount);
                    break;
                case 2 :
                    report.setWeiBoCount(reportCount);
                    break;
                case 3 :
                    report.setFacebookCount(reportCount);
                    break;
                case 4 :
                    report.setTwitterCount(reportCount);
                    break;
                case 5 :
                    report.setYaYaCount(reportCount);
                    break;
                case 6 :
                    report.setMobileCount(reportCount);
                    break;
                case 7 :
                    report.setEmailCount(reportCount);
                    break;
                default:
                    break;

            }
        }
    }
}
