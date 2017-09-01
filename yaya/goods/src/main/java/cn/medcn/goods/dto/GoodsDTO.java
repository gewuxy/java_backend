package cn.medcn.goods.dto;

import cn.medcn.goods.model.Goods;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/4/28.
 */
@NoArgsConstructor
@Data
public class GoodsDTO {
    private Integer id;
    // 商品价格
    private Integer price;
    //商品图片URL
    private String picture;

    private String name;

    public static GoodsDTO build(Goods goods){
        GoodsDTO goodsDTO = new GoodsDTO();
        if(goods != null){
            goodsDTO.setId(goods.getId());
            goodsDTO.setName(goods.getName());
            goodsDTO.setPicture(goods.getPicture());
            goodsDTO.setPrice(goods.getPrice());
        }
        return goodsDTO;
    }


}
