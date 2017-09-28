package cn.medcn.csp.controller.api;

import cn.medcn.article.model.Article;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/9/27.
 */
@Controller
@RequestMapping("/api/advert")
public class AdvertController extends BaseController {

    protected static final String CSP_ADVERT_CATEGORY_ID = "17092809462257868554";

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.csp.base}")
    private String appCSPBase;

    @Autowired
    protected ArticleService articleService;

    @RequestMapping("/advert")
    @ResponseBody
    public String advert() {
        Article article = articleService.findAppAdvert(CSP_ADVERT_CATEGORY_ID);
        Map<String, Object> map = new HashMap<String, Object>();
        if (article != null) {
            map.put("id", article.getId());
            map.put("countDown", Constants.CSP_ADVERT_COUNT_DOWN);
            map.put("imgUrl", appFileBase + article.getArticleImg());
            map.put("pageUrl", appCSPBase + "api/advert/view/" + article.getId() ) ;
        }
        return success(map);
    }

    /**
     * 广告视图跳转
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, Model model) {
        Article article = articleService.selectByPrimaryKey(id);
        model.addAttribute("article", article);
        return "/advert/view";
    }

}
