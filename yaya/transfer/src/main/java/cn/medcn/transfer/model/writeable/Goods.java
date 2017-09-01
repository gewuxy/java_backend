package cn.medcn.transfer.model.writeable;


import cn.medcn.transfer.model.readonly.GoodsReadOnly;

import java.util.Date;

/**商品实体类
 * Created by LiuLP on 2017/4/21.
 */

public class Goods {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Integer getGtype() {
        return gtype;
    }

    public void setGtype(Integer gtype) {
        this.gtype = gtype;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Integer buyLimit) {
        this.buyLimit = buyLimit;
    }

    public static Goods build(GoodsReadOnly goodsReadOnly){
        Goods gd = new Goods();
        gd.setId(goodsReadOnly.getGoods_id().intValue());
        gd.setName(goodsReadOnly.getGoods_name());
        gd.setAuthor(goodsReadOnly.getGoods_author());
        gd.setProvider(goodsReadOnly.getGoods_provider());
        gd.setBuyLimit(0);
        gd.setDescrip(goodsReadOnly.getGoods_description());
        gd.setPicture(goodsReadOnly.getGoodsImgUrl());
        if (goodsReadOnly.getGoods_type().equals("goods")){
            gd.setGtype(0);
        }else if (goodsReadOnly.getGoods_type().equals("book")){
            gd.setGtype(1);
        }
        gd.setBookUrl(goodsReadOnly.getGoodsBookUrl());
        gd.setPrice(goodsReadOnly.getGoods_price());
        gd.setRefund(goodsReadOnly.getGoods_return_credits());
        gd.setStatus(1);
        gd.setStock(goodsReadOnly.getGoods_stock_number());
        gd.setCreateTime(new Date());
        return gd;
    }
}
