package cn.medcn.csp.admin.controllor;

import cn.medcn.article.model.CspArticle;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.csp.admin.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 帮助与反馈 Created by jianliang
 */
@Controller
@RequestMapping(value = "/csp/feedback")
public class FeedBackController extends BaseController {

    @Autowired
    private FeedBackService feedBackService;

    @Value("${csp.file.upload.base}")
    protected String cspFileUploadBase;

    @RequestMapping(value = "/index")
    public String CSPmeetingServiceList(Pageable pageable, Model model) {
        MyPage<CspArticle> myPage = feedBackService.findCSPmeetingServiceListByPage(pageable);
        model.addAttribute("page", myPage);
        return "/feedback/feedbackIndex";
    }

    @RequestMapping(value = "/check")
    public String check(@RequestParam(value = "id", required = true) String id, Model model) {
        CspArticle cspArticle = feedBackService.selectByPrimaryKey(id);
        model.addAttribute("article", cspArticle);
        return "/feedback/feedbackInfo";
    }

    @RequestMapping(value = "/edit")
    public String edit(@RequestParam(value = "id", required = true) String id, Model model) {
        CspArticle cspArticle = feedBackService.selectByPrimaryKey(id);
        model.addAttribute("article", cspArticle);
        return "/feedback/feedbackInfoEdit";
    }

    @RequestMapping(value = "/update")
    public String update(CspArticle cspArticle,MultipartFile picture) {
        String originalFileName = picture.getOriginalFilename();
        if (picture != null && originalFileName != null && originalFileName.length() > 0) {
            String pic_path = cspFileUploadBase;
            File file = new File(pic_path);
            if(!file.exists()){
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
        }else{
            CspArticle article = feedBackService.selectByPrimaryKey(cspArticle.getId());
            article.setImgUrl(cspArticle.getImgUrl());
        }
            feedBackService.updateByPrimaryKeySelective(cspArticle);
            return "redirect:/csp/feedback/index";
    }
}
