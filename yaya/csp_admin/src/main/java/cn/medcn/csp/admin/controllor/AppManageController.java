package cn.medcn.csp.admin.controllor;

import cn.medcn.article.model.CspArticle;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.JsonUtils;
import cn.medcn.common.utils.QRCodeUtils;
import cn.medcn.csp.admin.service.AppManageService;
import cn.medcn.user.model.AppVersion;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
public class AppManageController extends BaseController {

    @Autowired
    private AppManageService appManageService;

    @Value("${csp.file.upload.base}")
    protected String cspFileUploadBase;

    @Value("${app.ios.download.url}")
    protected String appIosDownloadUrl;

    @Value("${app.and.download.url}")
    protected String appAndDownloadUrl;

    /**
     * 查看APP上架列表
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String showManageIndex(Pageable pageable, Model model) {
        MyPage<AppVersion> myPage = appManageService.findappManageListByPage(pageable);
        model.addAttribute("page", myPage);
        return "/appManage/AppManageList";
    }

    /**
     * 查看APP详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/check")
    public String checkAppManage(@RequestParam(value = "id", required = true) Integer id, Model model) {
        AppVersion appVersion = appManageService.selectByPrimaryKey(id);
        model.addAttribute("imgIosName", appVersion.getVersion() + ".png");
        model.addAttribute("imgAndName", appVersion.getVersion() + ".png");
        model.addAttribute("appVersion", appVersion);
        return "/appManage/AppManageInfo";
    }

    /**
     * 修改APP列表
     *
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/update")
    public String updateAppManage(AppVersion appVersion) {
        appVersion.setUpdateTime(new Date());
        appManageService.updateByPrimaryKeySelective(appVersion);
        return "redirect:/csp/appManage/list";
    }

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(value = "/edit")
    public String editAppManage() {
        return "/appManage/AppManageAddInfo";
    }

    /**
     * 添加APP
     *
     * @param appVersion
     * @param //uploadFile
     * @param //model
     * @return
     */
    @RequestMapping(value = "/add")
    public String addAppManage(AppVersion appVersion) {
        QRCodeUtils.createQRCode(appIosDownloadUrl, cspFileUploadBase + appVersion.getVersion() + ".png");
        QRCodeUtils.createQRCode(appAndDownloadUrl, cspFileUploadBase + appVersion.getVersion() + ".png");
        appVersion.setUpdateTime(new Date());
        appManageService.insert(appVersion);
        return "redirect:/csp/appManage/list";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    public String deleteAppManage(@RequestParam(value = "id", required = true) Integer id) {
        appManageService.deleteByPrimaryKey(id);
        return "redirect:/csp/appManage/list";
    }

    /**
     * 文件下载
     *
     * @param uploadFile
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String uploadApp(MultipartFile uploadFile) {
        if (uploadFile == null) {
            return APIUtils.error("不能上传空文件");
        }
        String filename = uploadFile.getOriginalFilename();
        File saveFile = new File(cspFileUploadBase + filename);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            uploadFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return APIUtils.error("文件保存出错");
        }
        return filename;
    }
}
