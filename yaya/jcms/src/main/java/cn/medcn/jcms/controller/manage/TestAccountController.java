package cn.medcn.jcms.controller.manage;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.user.dto.TestAccountDTO;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/8/23.
 */
@Controller
@RequestMapping(value = "/mng/account/test")
public class TestAccountController extends BaseController {

    @Autowired
    protected AppUserService appUserService;

    @RequestMapping(value = "/list")
    public String list(Pageable pageable, String keyword, Model model) throws SystemException{
        checkUser();
        pageable.setPageSize(8);
        pageable.put("keyword", keyword);
        MyPage<TestAccountDTO> page = appUserService.findTestAccount(pageable);
        model.addAttribute("page", page);
        return "/manage/testAccountList";
    }


    protected void checkUser() throws SystemException{
        Principal principal = SubjectUtils.getCurrentUser();
        if (!principal.getUsername().endsWith("@medcn.cn")){
            throw new SystemException("您的账号无权进行此操作!!");
        }
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(Integer userId){
        try {
            checkUser();
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        appUserService.deleteDoctor(userId);
        return success();
    }
}
