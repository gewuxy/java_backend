package cn.medcn.api.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.service.DataFileService;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;
import cn.medcn.user.service.AdvertService;
import cn.medcn.user.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixuan on 2017/5/12.
 */
@Controller
@RequestMapping(value="/view")
public class ArticleViewController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    protected MeetService meetService;

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    protected AdvertService advertService;

    @Autowired
    protected BannerService bannerService;



    @RequestMapping(value="/banner/{id}")
    public String view(@PathVariable String id, Model model){
        Banner article = bannerService.selectByPrimaryKey(id);
        model.addAttribute("banner", article);
        return "/banner/view";
    }


    @RequestMapping(value = "/article/{id}")
    public String viewArticle(@PathVariable String id, Model model){
        Article article = articleService.selectByPrimaryKey(id);
        model.addAttribute("article", article);
        return "/article/view";
    }


    /**
     * 作为分享会议，任何人都可以访问会议的详细信息。放置在ArticleViewController ,不被拦截器拦截
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/meet/info")
    public String meetView(String id, Model model){
        MeetInfoDTO meetInfoDTO = meetService.findFinalMeetInfo(id, null);
        if (meetInfoDTO.getStartTime() != null && meetInfoDTO.getEndTime() != null){
            model.addAttribute("meetDuration",
                    CalendarUtils.formatTime((meetInfoDTO.getEndTime().getTime() - meetInfoDTO.getStartTime().getTime())/1000));
        }
        model.addAttribute("meet", meetInfoDTO);
        model.addAttribute("view", "true");
        return "/weixin/meet/info";
    }

    @RequestMapping(value = "/advert/{id}")
    public String view(@PathVariable Integer id, Model model){
        Advert advert = advertService.selectByPrimaryKey(id);
        model.addAttribute("advert", advert);
        return "/advert/view";
    }
}
