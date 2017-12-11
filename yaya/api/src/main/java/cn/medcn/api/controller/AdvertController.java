package cn.medcn.api.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.Constants;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.TailorMadeUtils;
import cn.medcn.user.dto.AdvertDTO;
import cn.medcn.user.dto.BannerDTO;
import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;
import cn.medcn.user.service.AdvertService;
import cn.medcn.user.service.BannerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private AdvertService advertService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    protected BannerService bannerService;

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

        Integer masterId = TailorMadeUtils.get();
        if (masterId == null) {
            masterId = Constants.DEFAULT_ATTENTION_PUBLIC_ACCOUNT;
        }
        Advert cond = new Advert();
        cond.setPubUserId(masterId);
        cond.setActive(true);
        Advert advert = advertService.selectOne(cond);


        Map<String, Object> map = Maps.newHashMap();
        if (advert != null) {
            if (CheckUtils.isNotEmpty(advert.getImageUrl())) {
                map.put("imageUrl", appFileBase + advert.getImageUrl());
            }

            if (CheckUtils.isNotEmpty(advert.getPageUrl())) {
                map.put("pageUrl", advert.getPageUrl());
            } else {
                if (CheckUtils.isNotEmpty(advert.getContent())) {
                    map.put("pageUrl", appYayaBase + "view/advert/" + advert.getId());
                }
            }

            map.put("skipTime", advert.getSkipTime());
            map.put("version", advert.getVersion());
        }
        return APIUtils.success(map);
    }


    /**
     * @return
     */
    @RequestMapping("/banner")
    @ResponseBody
    public String banner() {
        Pageable pageable = new Pageable(1, 5);
        pageable.put("active", true);
        MyPage<Banner> page = bannerService.findBannerList(pageable);
        List<BannerDTO> bannerDTOList = Lists.newArrayList();

        for (Banner banner : page.getDataList()) {
            BannerDTO bannerDTO = new BannerDTO();
            bannerDTO.setId(banner.getId());
            bannerDTO.setTitle(banner.getTitle());
            bannerDTO.setPageUrl(appFileBase + banner.getImageUrl());
            bannerDTO.setLink(appYayaBase + "/view/banner/" + banner.getId());
            bannerDTOList.add(bannerDTO);
        }

        return APIUtils.success(bannerDTOList);
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
