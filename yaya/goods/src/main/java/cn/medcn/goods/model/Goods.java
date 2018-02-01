package cn.medcn.goods.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**商品实体类
 * Created by LiuLP on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_goods")
public class Goods {

    @Id
    private Integer id;

    private String name;
    // 商品价格
    private Integer price;
    //商品提供者
    private String provider;
    //商品库存
    private Integer stock;
    //商品图片URL
    private String picture;
    //商品作者
    private String author;
    //商品描述
    private String descrip;
    //商品类型 0表示实物 1表示电子书 2表示
    private Integer gtype;
    //电子书地址
    private String bookUrl;
    //0表示下架 1表示上架
    private Integer status;

    private Date createTime;
    //商品返还象数
    private Integer refund;
    //限购数
    private Integer buyLimit;

    public enum Status{
        down(0,"下架"),
        up(1,"上架");

        private Integer type;
        private String label;
        public Integer getType() {
            return type;
        }
        public String getLabel() {
            return label;
        }
        Status(Integer type,String label){
            this.type = type;
            this.label = label;
        }
    }

    @Transient
    protected String statusName;

    public String getStatusName(){
        return Goods.Status.values()[this.status].getLabel();
    }
}
