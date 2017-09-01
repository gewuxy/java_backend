package cn.medcn.user.dto;

import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;
import com.alipay.api.domain.Article;
import lombok.Data;
import lombok.NoArgsConstructor;

/**广告
 * Created by LiuLP on 2017/4/28.
 */

@NoArgsConstructor
@Data
public class BannerDTO {

   private String id;

   private String title;

  private  String pageUrl;

  private Integer sort;

  private String link;


//   public static BannerDTO build(Article banner){
//       BannerDTO bannerDTO = new BannerDTO();
//       if(banner != null){
//           bannerDTO.setId(banner.getId());
//           bannerDTO.setPageUrl(banner.getPageUrl());
//           bannerDTO.setSort(banner.getSort());
//       }
//       return bannerDTO;
//   }

}
