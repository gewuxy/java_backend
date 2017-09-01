package cn.medcn.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by LiuLP on 2017/4/24.
 */

@Data
@NoArgsConstructor
public class OrderDTO {

    private Integer id;

    private String orderNo;

    private String receiver;

    private Date createTime;

    private String phone;

    private String zoneCode;
    //订单状态0表示待处理 1表示已接受订单 2表示已发货 3表示商品已接受
    private Integer status;
    //物流单号
    private String postNo;
    //物流单位
    private String postUnit;
    //商品名称
    private String name;
    //商品价格
    private Integer price;
    //收货省市
    private String province;
    //收获地址
    private String address;

}
