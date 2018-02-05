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
import java.util.HashMap;
import java.util.Map;

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

    @Value("${app.file.base}")
    protected String appFileBase;

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
        String imgUrl = cspArticle.getImgUrl();
        model.addAttribute("imgURL",appFileBase+imgUrl);
        model.addAttribute("article", cspArticle);
        return "/cspArticle/cspArticleInfoEdit";
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
            addErrorFlashMessage(redirectAttributes,"更新失败");
        }
        return "redirect:/csp/article/list";
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "图片上传")
    public String uploadImg(MultipartFile file){
        if (file == null) {
            return error("不能上传空文件");
        }
        String filename = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(filename.lastIndexOf("."));
        String saveFileName = StringUtils.nowStr()+suffix;
        if (suffix.substring(1).equals("jpg") || suffix.substring(1).equals("png")||suffix.substring(1).equals("JPG") || suffix.substring(1).equals("PNG")){
            String imgPath = appFileUploadBase+"article/"+saveFileName;
            String imgURL = "article/"+saveFileName;
            File saveFile = new File(imgPath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return APIUtils.error("文件保存出错");
            }
            Map<String,String> map = new HashMap();
            map.put("imgURL",imgURL);
            map.put("src", appFileBase + "article/"+saveFileName);
            map.put("title", saveFileName);
            return success(map);
        }else {
            return error("文件格式错误");
        }

    }
}
