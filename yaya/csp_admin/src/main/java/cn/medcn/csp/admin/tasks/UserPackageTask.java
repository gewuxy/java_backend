package cn.medcn.csp.admin.tasks;

import cn.medcn.user.model.ReportPackage;
import cn.medcn.user.service.CspUserPackageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户套餐统计
 * by create HuangHuibin 2018/1/3
 */
public class UserPackageTask {

    @Autowired
    protected CspUserPackageService cspUserPackageService;

    public void execute(){
       List<Map<String,Object>> info = cspUserPackageService.getTodayPackageInfo();
       if(info != null){
           ReportPackage packages = new ReportPackage();
           packages.setStaCount(Integer.parseInt(info.get(0).get("num").toString()));
           packages.setPreCount(Integer.parseInt(info.get(1).get("num").toString()));
           packages.setProCount(Integer.parseInt(info.get(2).get("num").toString()));
           packages.setUserCount(Integer.parseInt(info.get(0).get("user").toString()));
           packages.setRegisterTime(new Date());
           cspUserPackageService.insertReportPackage(packages);
       }
    }
}
