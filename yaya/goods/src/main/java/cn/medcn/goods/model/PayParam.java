package cn.medcn.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/4/25.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayParam {

    //商品id
    private Integer goodsId;
    //商品价格
    private Integer price;
    //收货人
    private String receiver;

    private String phone;
    //省份
    private String province;
    //详细地址
    private String address;

    //限购数
    private Integer buyLimit;

}
