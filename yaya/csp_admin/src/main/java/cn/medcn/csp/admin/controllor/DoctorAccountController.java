package cn.medcn.csp.admin.controllor;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.dto.UnitAccountSearchDTO;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.UnitAccountDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.ActiveCodeService;
import cn.medcn.user.service.AppUserService;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 丫丫医师医生管理控制器
 * Created by LiuLP on 2018/1/16.
 */
@Controller
@RequestMapping(value = "/yaya/doctor")
public class DoctorAccountController extends BaseController {

    @Autowired
    protected AppUserService appUserService;

    @Autowired
    protected ActiveCodeService activeCodeService;

    @Autowired
    protected SystemRegionService systemRegionService;

    @Autowired
    protected FileUploadService fileUploadService;

    @Value("${app.file.base}")
    protected String appFileBase;

    /**
     * YaYa医师医生列表+搜索
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions("yaya:doctor:list")
    public String list(Pageable pageable, String keyword, Model model){
        if (CheckUtils.isNotEmpty(keyword)) {
            pageable.put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        
        MyPage<AppUserDTO> page = appUserService.findDoctorAccounts(pageable);
        model.addAttribute("page", page);

        return "/yaya/doctor/list";
    }


    /**
     * 修改医生信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    @RequiresPermissions("yaya:doctor:edit")
    public String edit(Integer id, Model model){
        if (id != null) {
            Pageable pageable = new Pageable();
            pageable.put("id",id);
            MyPage<AppUserDTO> page = appUserService.findDoctorAccounts(pageable);
            model.addAttribute("user", page.getDataList().get(0));
        }

        List<SystemRegion> provinceList = systemRegionService.findRegionByPreid(0);
        model.addAttribute("provinceList", provinceList);

        model.addAttribute("appFileBase", appFileBase);
        return "/yaya/doctor/editForm";
    }

    /**
     * 增加/修改医生信息
     * @param appUserDTO
     * @return
     */
    @RequestMapping(value = "/save")
    @RequiresPermissions("yaya:doctor:edit")
    @Log(name = "增加/修改医生信息")
    public String save(AppUserDTO appUserDTO, Model model, RedirectAttributes redirectAttributes) throws SystemException {
        boolean isAdd = appUserDTO.getId() == null;
        String username = appUserDTO.getUsername();
        String mobile = appUserDTO.getMobile();
        if(StringUtils.isEmpty(username) && StringUtils.isEmpty(mobile)){
            model.addAttribute("error", "请填写邮箱或手机号");
            return "/yaya/doctor/editForm";
        }
        if(StringUtils.isEmpty(appUserDTO.getNickname())){
            model.addAttribute("error", "请填写昵称");
            return "/yaya/doctor/editForm";
        }

        String nickName = appUserDTO.getNickname();
        appUserDTO.setLinkman(nickName);
        AppUser appUser = AppUserDTO.rebuildToDoctor(appUserDTO);
        AppUser emailAccount = null;
        if(!StringUtils.isEmpty(username)){
            emailAccount = appUserService.findAppUserByLoginName(appUserDTO.getUsername());

        }
        AppUser mobileAccount = null;
        if(!StringUtils.isEmpty(mobile)){
            mobileAccount = appUserService.findAppUserByLoginName(appUserDTO.getMobile());
        }

        //添加新账号，需要检测账号是否已经存在
        if (isAdd) {
            //检测邮箱是否存在
                if (emailAccount != null) {
                    model.addAttribute("user", appUserDTO);
                    model.addAttribute("error", "邮箱[" + appUserDTO.getUsername() + "] 已经被使用.");
                    return "/yaya/doctor/editForm";
                }
            //检测手机是否存在
                if(mobileAccount != null){
                    model.addAttribute("user", appUserDTO);
                    model.addAttribute("error", "手机号[" + appUserDTO.getMobile() + "] 已经被使用.");
                    return "/yaya/doctor/editForm";
                }
                appUser.setPassword(Constants.RESET_PASSWORD);
                appUser.setRegistDate(new Date());
                appUser.setAuthed(true);
                appUser.setPubFlag(false);
                appUserService.executeRegist(appUser,null,null);
                addFlashMessage(redirectAttributes, "添加医生成功,初始密码为123456");
                return "redirect:/yaya/doctor/list";
        } else {
            //修改医生信息
                if (emailAccount != null && emailAccount.getId().intValue() != appUserDTO.getId()) {
                    model.addAttribute("user", appUserDTO);
                    model.addAttribute("error", "邮箱[" + emailAccount.getUsername() + "] 已经被使用.");
                    return "/yaya/doctor/editForm";
                }
                if(mobileAccount != null && mobileAccount.getId().intValue() != appUserDTO.getId()){
                    model.addAttribute("user", appUserDTO);
                    model.addAttribute("error", "手机号[" + mobileAccount.getMobile() + "] 已经被使用.");
                    return "/yaya/doctor/editForm";
                }
                    appUserService.updateDoctor(appUser);
                    addFlashMessage(redirectAttributes, "修改医生信息成功");
                    return "redirect:/yaya/doctor/list";
        }
    }


    /**
     * 上传医生头像
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload/avatar")
    @ResponseBody
    @RequiresPermissions("yaya:doctor:edit")
    @Log(name = "上传医生头像")
    public String uploadAvatar(@RequestParam MultipartFile file){
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.PORTRAIT.path);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success(result);
    }






    @RequestMapping(value = "/pwd/reset/{id}")
    @ResponseBody
    @RequiresPermissions("yaya:doctor:edit")
    public String resetPwd(@PathVariable Integer id){
        AppUser appUser = appUserService.selectByPrimaryKey(id);
        if (appUser != null) {
            int pwd = StringUtils.randomNum(6);
            appUser.setPassword(MD5Utils.MD5Encode(String.valueOf(pwd)));
            appUserService.updateByPrimaryKey(appUser);

            Map<String, Object> result = new HashMap<>();
            result.put("currentPwd", pwd);
            return success(result);
        }
        return success();
    }

}
