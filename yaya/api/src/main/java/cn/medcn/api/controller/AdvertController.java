package cn.medcn.api.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.user.dto.AdvertDTO;
import cn.medcn.user.dto.BannerDTO;
import cn.medcn.user.model.Advert;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * app广告
 * Created by LiuLP on 2017/4/20.
 */
@Controller
@RequestMapping("/api")
public class AdvertController {
    private static final String YAYA_ADVERT_CATEGORY_ID = "17051215391669122582";

    private static final String YAYA_BANNER_CATEGORY_ID = "17051215380482710996";

    /*@Autowired
    private AdvertService advertService;*/

    @Autowired
    private ArticleService articleService;

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.yaya.base}")
    private String appYayaBase;

    /**
     * 进入app时的广告
     *
     * @return
     */
    @RequestMapping("/advert")
    @ResponseBody
    public String advert() {
        Article article = articleService.findAppAdvert(YAYA_ADVERT_CATEGORY_ID);
        Map<String, String> map = Maps.newHashMap();
        if(article != null){
            map.put("pageUrl",appFileBase+article.getArticleImg());
            map.put("id", article.getId());
        }
        return APIUtils.success(map);
    }

    /**
     * @return
     */
    @RequestMapping("/banner")
    @ResponseBody
    public String banner() {
        Pageable pageable = new Pageable(1,5);

        MyPage<Article> page = articleService.findAppBanners(pageable, YAYA_BANNER_CATEGORY_ID) ;

        return APIUtils.success(getBannerDTO(page.getDataList()));
    }


    public List<BannerDTO> getBannerDTO(List<Article> bannerList) {

        List<BannerDTO> bannerDTOList = Lists.newArrayList();
        if (bannerList != null) {
            for (Article banner : bannerList) {
                BannerDTO bannerDTO = new BannerDTO();
                bannerDTO.setId(banner.getId());
                bannerDTO.setTitle(banner.getTitle());
                bannerDTO.setPageUrl(appFileBase+banner.getArticleImg());
                bannerDTO.setLink(appYayaBase+"/view/banner/"+banner.getId());
                bannerDTOList.add(bannerDTO);
            }
        }
        return bannerDTOList;
    }



    public List<AdvertDTO> getAdvertDTO(List<Advert> advertList) {

        if (advertList != null) {
            List advertDTOList = new ArrayList<>();
            for (Advert advert : advertList) {
               AdvertDTO advertDTO = AdvertDTO.build(advert);
               advertDTOList.add(advertDTO);
            }
            return advertDTOList;
        }
        return null;
    }
}
