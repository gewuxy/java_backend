package cn.medcn.goods.dto;

import cn.medcn.goods.model.Goods;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/5/5.
 */

@NoArgsConstructor
@Data
public class GoodInfoDTO {
    private Integer id;
    // 商品价格
    private Integer price;
    //商品图片URL
    private String picture;

    private String name;

    //商品描述
    private String description;

    //限购数
    private Integer buyLimit;

    public static GoodInfoDTO build(Goods goods){
        GoodInfoDTO goodInfoDTO = new GoodInfoDTO();
        if(goods != null){
            goodInfoDTO.setId(goods.getId());
            goodInfoDTO.setName(goods.getName());
            goodInfoDTO.setPicture(goods.getPicture());
            goodInfoDTO.setPrice(goods.getPrice());
            goodInfoDTO.setBuyLimit(goods.getBuyLimit());
            goodInfoDTO.setDescription(goods.getDescrip());
        }
        return goodInfoDTO;
    }
}
