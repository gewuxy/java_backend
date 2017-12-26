package cn.medcn.csp.tasks;

import cn.medcn.user.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lixuan on 2017/12/26.
 */
public class RegisterReportTask {

    @Autowired
    protected ReportService reportService;

    public void execute(){
        reportService.executeReportRegister();
    }
}
