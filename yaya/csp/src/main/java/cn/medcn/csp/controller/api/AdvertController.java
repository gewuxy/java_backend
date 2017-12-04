package cn.medcn.csp.controller.api;

import cn.medcn.article.model.Article;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.model.Advert;
import cn.medcn.user.service.AdvertService;
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

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.csp.base}")
    private String appCSPBase;

    @Autowired
    protected ArticleService articleService;

    @Autowired
    protected AdvertService advertService;

    @RequestMapping("/advert")
    @ResponseBody
    public String advert() {
        Integer abroad = LocalUtils.isAbroad() ? 1 : 0 ;
        Advert advert = advertService.findCspAdvert(abroad);
        Map<String, Object> map = new HashMap<String, Object>();
        if (advert != null) {
            map.put("id", advert.getId());
            map.put("countDown", advert.getSkipTime());
            map.put("imgUrl", appFileBase + advert.getImageUrl());
            if (CheckUtils.isNotEmpty(advert.getPageUrl())) {
                map.put("pageUrl", advert.getPageUrl());
            } else {
                if (CheckUtils.isNotEmpty(advert.getContent())) {
                    map.put("pageUrl", appCSPBase + "api/advert/view/" + advert.getId());
                }
            }
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
        Advert advert = advertService.selectByPrimaryKey(Integer.valueOf(id));
        model.addAttribute("article", advert);
        return localeView("/advert/view");
    }

}
