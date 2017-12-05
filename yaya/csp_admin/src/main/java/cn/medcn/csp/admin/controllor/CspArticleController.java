package cn.medcn.csp.admin.controllor;

import cn.medcn.article.model.CspArticle;
import cn.medcn.article.service.CspArticleService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
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

/**
 * 帮助与反馈 Created by jianliang
 */
@Controller
@RequestMapping(value = "/csp/article")
public class CspArticleController extends BaseController {

    @Autowired
    private CspArticleService cspArticleService;

    @Value("${app.file.upload.base}")
    protected String appFileUploadBase;

    /**
     *查看详情
     * @param pageable
     * @param model
     * @param listType Tab选项卡
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "查看详情")
    public String CSPmeetingServiceList(Pageable pageable, Model model, Integer listType) {
        model.addAttribute("listType", listType);
        MyPage<CspArticle> myPage = cspArticleService.findCSPmeetingServiceListByPage(pageable);
        model.addAttribute("page", myPage);
        return "/cspArticle/cspArticleList";
    }

    /**
     * 查看具体
     * @param id
     * @param model
     * @param listType
     * @return
     */
    @RequestMapping(value = "/check")
    @Log(name = "查看具体")
    public String check(@RequestParam(value = "id", required = true) String id, Model model, Integer listType) {
        model.addAttribute("listType", listType);
        CspArticle cspArticle = cspArticleService.selectByPrimaryKey(id);
        model.addAttribute("article", cspArticle);
        return "/cspArticle/cspArticleInfo";
    }

    /**
     * 进入编辑页面
     * @param id
     * @param model
     * @param listType
     * @return
     */
    @RequestMapping(value = "/edit")
    @Log(name = "进入编辑页面")
    public String edit(@RequestParam(value = "id", required = true) String id, Model model, Integer listType) {
        model.addAttribute("listType", listType);
        CspArticle cspArticle = cspArticleService.selectByPrimaryKey(id);
        model.addAttribute("article", cspArticle);
        return "/cspArticle/cspArticleInfoEdit";
    }

    /**
     * 修改页面
     * @param cspArticle
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改页面")
    public String update(CspArticle cspArticle,RedirectAttributes redirectAttributes) {
        if (cspArticle != null){
            cspArticleService.updateByPrimaryKeySelective(cspArticle);
            addFlashMessage(redirectAttributes,"更新成功");
        }else {
            addFlashMessage(redirectAttributes,"更新失败");
        }
        return "redirect:/csp/article/list";
    }

    /**
     * 图片上传
     * @param uploadFile
     * @return
     */
    @Log(name = "图片上传")
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String uploadImg(MultipartFile uploadFile){
        if (uploadFile == null) {
            return APIUtils.error("不能上传空文件");
        }
        String filename = uploadFile.getOriginalFilename();
        String suffix = uploadFile.getOriginalFilename().substring(filename.lastIndexOf("."));
        if (suffix.substring(1).equals("jpg") || suffix.substring(1).equals("png")){
        String saveFileName = StringUtils.nowStr()+suffix;
        String imgPath =appFileUploadBase + saveFileName;
        File saveFile = new File(imgPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            uploadFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return APIUtils.error("文件保存出错");
        }

        //return saveFileName;
            return APIUtils.success(imgPath);
        }else {
            return APIUtils.error("文件格式有误");

        }
    }
}
