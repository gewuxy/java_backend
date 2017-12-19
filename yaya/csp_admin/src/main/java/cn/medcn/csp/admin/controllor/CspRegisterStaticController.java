package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * csp用户注册统计
 * Created by LiuLP on 2017/12/18/018.
 */
@RequestMapping("/sys/register")
@Controller
public class CspRegisterStaticController extends BaseController{


    @Autowired
    private CspUserService cspUserService;

    /**
     * 获取国内数据
     * @return
     */
    @RequestMapping("/home")
    public String getData(){
            int homeCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.home.ordinal());
            int abroadCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.abroad.ordinal());

            return "";
    }


    /**
     * 获取海外数据
     * @return
     */
    @RequestMapping("/abroad")
    public String getAbroadData(){
        int homeCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.home.ordinal());
        int abroadCount = cspUserService.selectRegisterCount(CspUserInfo.AbroadType.abroad.ordinal());

        return "";
    }


}
