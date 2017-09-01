package cn.medcn.user.dto;

import cn.medcn.user.model.Advert;
import lombok.Data;
import lombok.NoArgsConstructor;

/**广告
 * Created by LiuLP on 2017/4/28.
 */

@NoArgsConstructor
@Data
public class AdvertDTO {

   private Integer id;

   //广告页内容
   private String pageUrl;

   public static AdvertDTO build(Advert advert){
       AdvertDTO advertDTO = new AdvertDTO();
       if(advert != null){
           advertDTO.setId(advert.getId());
           advertDTO.setPageUrl(advert.getPageUrl());
       }
       return advertDTO;
   }

}
