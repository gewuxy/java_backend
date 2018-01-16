package cn.medcn.csp.admin.controllor;

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
import cn.medcn.user.dto.UnitAccountDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.ActiveCodeService;
import cn.medcn.user.service.AppUserService;
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
 * 丫丫医师单位号管理控制器
 * Created by lixuan on 2018/1/16.
 */
@Controller
@RequestMapping(value = "/yaya/unit")
public class UnitAccountController extends BaseController {

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
     * YaYa医师单位号列表+搜索
     * @param pageable
     * @param dto
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions("yaya:unit:list")
    public String list(Pageable pageable, UnitAccountSearchDTO dto, Model model){
        if (CheckUtils.isNotEmpty(dto.getKeyword())) {
            pageable.put(UnitAccountSearchDTO.FIELD_KEYWORD, dto.getKeyword());
            model.addAttribute(UnitAccountSearchDTO.FIELD_KEYWORD, dto.getKeyword());
        }

        if (dto.getRcd() != null) {
            pageable.put(UnitAccountSearchDTO.FIELD_RCD, dto.getRcd());
            model.addAttribute(UnitAccountSearchDTO.FIELD_RCD, dto.getRcd());
        }

        if (dto.getTestFlag() != null) {
            pageable.put(UnitAccountSearchDTO.FIELD_TEST_FLAG, dto.getTestFlag());
            model.addAttribute(UnitAccountSearchDTO.FIELD_TEST_FLAG, dto.getTestFlag());
        }

        MyPage<UnitAccountDTO> page = appUserService.findUnitAccounts(pageable);
        model.addAttribute("page", page);

        return "/yaya/unit/list";
    }


    @RequestMapping(value = "/edit")
    @RequiresPermissions("yaya:unit:edit")
    public String edit(Integer id, Model model){
        if (id != null) {
            AppUser appUser = appUserService.selectByPrimaryKey(id);

            int activeStore = activeCodeService.getActiveStore(id);
            model.addAttribute("activeStore", activeStore);
            model.addAttribute("user", appUser);
        }

        List<SystemRegion> provinceList = systemRegionService.findRegionByPreid(0);
        model.addAttribute("provinceList", provinceList);

        model.addAttribute("appFileBase", appFileBase);
        return "/yaya/unit/editForm";
    }

    /**
     * 增加/修改单位号
     * @param appUser
     * @return
     */
    @RequestMapping(value = "/save")
    @RequiresPermissions("yaya:unit:edit")
    @Log(name = "增加/修改单位号")
    public String save(AppUser appUser, Integer activeStore, Model model, RedirectAttributes redirectAttributes){
        boolean isAdd = appUser.getId() == null;
        if (isAdd) {
            //需要检测账号是否已经存在
            AppUser existedUser = appUserService.findAppUserByLoginName(appUser.getUsername());
            if (existedUser != null) {
                model.addAttribute("user", appUser);
                model.addAttribute("error", "账号[" + appUser.getUsername() + "] 已经被使用.");
                return "/yaya/unit/editForm";
            } else {
                appUserService.executeRegisterUnitAccount(appUser, activeStore);
                addFlashMessage(redirectAttributes, "添加单位号成功");
                return "redirect:/yaya/unit/list";
            }
        } else {
            appUserService.updateUnitAccount(appUser, activeStore);
            addFlashMessage(redirectAttributes, "修改单位号信息成功");
            return "redirect:/yaya/unit/list";
        }
    }


    /**
     * 将单位号设为推荐或者取消推荐
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/rcd/{id}")
    @RequiresPermissions("yaya:unit:edit")
    @Log(name = "推荐/取消推荐单位号")
    public String rcd(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        AppUser appUser = appUserService.selectByPrimaryKey(id);
        if (appUser == null) {
            addFlashMessage(redirectAttributes, "您操作的用户[id = " +id+ "] 不存在");
        } else {
            appUser.setTuijian(!appUser.getTuijian());
            appUserService.updateByPrimaryKey(appUser);
            addFlashMessage(redirectAttributes, "操作成功");
        }
        return "redirect:/yaya/unit/list";
    }


    /**
     * 上传单位号头像
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload/avatar")
    @ResponseBody
    @RequiresPermissions("yaya:unit:edit")
    @Log(name = "上传单位号头像")
    public String uploadAvatar(@RequestParam MultipartFile file){
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.PORTRAIT.path);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success(result);
    }


    @RequestMapping(value="/regions")
    @ResponseBody
    public String subRegions(String name){
        if(name == null){
            return success();
        }
        List<SystemRegion> options = systemRegionService.findRegionByPreName(name);
        return success(options);
    }


    @RequestMapping(value = "/pwd/reset/{id}")
    @ResponseBody
    @RequiresPermissions("yaya:unit:edit")
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
