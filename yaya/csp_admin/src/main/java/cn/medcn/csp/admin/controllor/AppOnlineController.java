package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.QRCodeUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.user.model.AppVersion;
import cn.medcn.user.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：jianliang
 * @Date: Create in 10:35 2017/11/10
 */
@Controller
@RequestMapping(value = "/csp/appManage")
public class AppOnlineController extends BaseController {

    @Autowired
    private AppVersionService appVersionService;

    @Value("${app.file.upload.base}")
    protected String appFileUploadBase;

    @Value("${app.file.base}")
    protected String appFileBase;

    /**
     * 查看APP上架列表
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "查看APP上架列表")
    public String showManageIndex(Pageable pageable, Model model) {
        MyPage<AppVersion> myPage = appVersionService.findappManageListByPage(pageable);
        model.addAttribute("page", myPage);
        return "/appManage/AppManageList";
    }

    /**
     * 查看APP上架详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/check")
    @Log(name = "查看APP详情")
    public String checkAppManage(@RequestParam(value = "id", required = true) Integer id, Model model) {
        AppVersion appVersion = appVersionService.selectByPrimaryKey(id);
        model.addAttribute("appVersion", appVersion);
        return "/appManage/AppManageInfo";
    }

    /**
     * 修改APP上架列表
     *
     * @param appVersion
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改APP列表")
    public String updateAppManage(AppVersion appVersion,RedirectAttributes redirectAttributes) {
        if (appVersion != null){
            Integer version = Integer.valueOf(appVersion.getVersionStr().replace(".", ""));
            appVersion.setVersion(version);
            appVersion.setUpdateTime(new Date());
            appVersionService.updateByPrimaryKeySelective(appVersion);
            addFlashMessage(redirectAttributes,"修改成功");
        }else {
            addErrorFlashMessage(redirectAttributes,"修改失败");
        }
        return "redirect:/csp/appManage/list";
    }

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(value = "/edit")
    @Log(name = "跳转页面")
    public String editAppManage() {
        return "/appManage/AppManageAddInfo";
    }

    /**
     * 添加APP
     *
     * @param appVersion
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/add")
    @Log(name = "添加APP")
    public String addAppManage(AppVersion appVersion,RedirectAttributes redirectAttributes) {
        if (appVersion != null){
            Integer version = Integer.valueOf(appVersion.getVersionStr().replace(".", ""));
            appVersion.setUpdateTime(new Date());
            appVersion.setVersion(version);
            appVersionService.insert(appVersion);
            addFlashMessage(redirectAttributes,"添加成功");
        }else {
            addErrorFlashMessage(redirectAttributes,"添加失败");
        }

        return "redirect:/csp/appManage/list";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @Log(name = "删除")
    public String deleteAppManage(@RequestParam(value = "id", required = true) Integer id) {
        appVersionService.deleteByPrimaryKey(id);
        return "redirect:/csp/appManage/list";
    }

    /**
     * 文件上传
     *
     * @param uploadFile
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "文件下载")
    public String uploadApp(MultipartFile uploadFile) {
        if (uploadFile == null) {
            return error("不能上传空文件");
        }
        String filename = uploadFile.getOriginalFilename();
        String suffix = uploadFile.getOriginalFilename().substring(filename.lastIndexOf(".")+1);
        if (suffix.equals("apk")){
            File saveFile = new File(appFileUploadBase+"apkfile/"+filename);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            try {
                uploadFile.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return error("文件保存出错");
            }
            Map<String,Object> map = new HashMap<String,Object>();
            String downUrl = "apkfile/"+filename;
            map.put("downUrl",downUrl);
            return success(map);
        }else {
            return error("文件格式错误");
        }

    }
}
