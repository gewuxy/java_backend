package cn.medcn.csp.admin.controllor;

import cn.medcn.article.model.CspArticle;
import cn.medcn.article.service.CspArticleService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.admin.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${csp.file.upload.base}")
    protected String cspFileUploadBase;

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
     * @param picture
     * @param model
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改页面")
    public String update(CspArticle cspArticle, MultipartFile picture, Model model) {
        String originalFileName = picture.getOriginalFilename();
        if (picture != null && originalFileName != null && originalFileName.length() > 0) {
            String pic_path = "D:"+cspFileUploadBase;
            File file = new File(pic_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String newFileName = System.currentTimeMillis()
                    + originalFileName.substring(originalFileName
                    .lastIndexOf("."));
            File newFile = new File(pic_path + newFileName);
            try {
                picture.transferTo(newFile);
                String imgPath = pic_path + newFileName;
                cspArticle.setImgUrl(imgPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            CspArticle article = cspArticleService.selectByPrimaryKey(cspArticle.getId());
            article.setImgUrl(cspArticle.getImgUrl());
        }
        cspArticleService.updateByPrimaryKeySelective(cspArticle);
        return "redirect:/csp/article/list";
    }
}
