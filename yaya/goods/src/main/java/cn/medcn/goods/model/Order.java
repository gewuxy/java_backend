package cn.medcn.goods.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**订单实体类
 * Created by LiuLP on 2017/4/21.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_goods_order")
public class Order {
    @Id
    private Integer id;

    private Integer goodsId;

    private Integer userId;

    private String orderNo;

    private String receiver;

    private Date createTime;

    private String phone;

    private String province;

    private String address;

    private String zoneCode;

    //订单状态0表示待处理 1表示已接受订单 2表示已发货 3表示商品已接受
    private Integer status = 0;

    private enum Status{
        PENDING(0,"待处理"),
        ACCEPT(1 ,"已接受"),
        DELIVERED(2, "已发货"),
        SIGNED(3,"已签收");


        private Integer type;

        private String label;

        public Integer getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        Status(Integer type, String label){
            this.type = type;
            this.label = label;
        }
    }
    //物流单号
    private String postNo;
    //物流单位
    private String postUnit;
    //配送方式
    private String postType;
    //话费的象数值
    private Integer cost;




}
