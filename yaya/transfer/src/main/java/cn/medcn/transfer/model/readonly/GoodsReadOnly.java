package cn.medcn.transfer.model.readonly;

import java.sql.Blob;

/**
 * Created by Liuchangling on 2017/6/28.
 */
public class GoodsReadOnly {

    private Long goods_id;
    private String goods_booknumber; //唯一随机数作为礼品id（16位随机字符）
    private String goods_name; //礼品名称
    private Integer goods_credits; //礼品所需积分数
    private Integer goods_price; //礼品价格
    private String goods_city;  //礼品所在地
    private String goods_provider; //礼品供应商
    private Integer goods_stock_number; //礼品库存数量
    private Blob goods_picture; //礼品图片
    private String goods_author; //书籍作者
    private String goods_author_introduce ;  //作者介绍
    private String goods_keywords;  //书籍关键词（关联医师建议的文章）
    private String goods_bkcatalog;  //书籍目录
    private String goods_description; //礼品描述
    private Blob goods_contents;  //礼品内容 二进制存储
    private String goods_type;  //礼品类别（实物:goods 电子书：book）
    private String goods_flag;  //标志是专业版:jk 健康版:zy
    private String goods_contenturl;  //编辑的礼品内容的网页 (实物)
    private Integer goods_exchange_number; //礼品兑换的人数
    private Integer goods_return_credits; //评论返还积分数
    private Integer cid;

    // 礼品图片URL
    private String goodsImgUrl;
    // 礼品内容URL
    private String goodsBookUrl;

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getGoodsBookUrl() {
        return goodsBookUrl;
    }

    public void setGoodsBookUrl(String goodsBookUrl) {
        this.goodsBookUrl = goodsBookUrl;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_booknumber() {
        return goods_booknumber;
    }

    public void setGoods_booknumber(String goods_booknumber) {
        this.goods_booknumber = goods_booknumber;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Integer getGoods_credits() {
        return goods_credits;
    }

    public void setGoods_credits(Integer goods_credits) {
        this.goods_credits = goods_credits;
    }

    public Integer getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Integer goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_city() {
        return goods_city;
    }

    public void setGoods_city(String goods_city) {
        this.goods_city = goods_city;
    }

    public String getGoods_provider() {
        return goods_provider;
    }

    public void setGoods_provider(String goods_provider) {
        this.goods_provider = goods_provider;
    }

    public Integer getGoods_stock_number() {
        return goods_stock_number;
    }

    public void setGoods_stock_number(Integer goods_stock_number) {
        this.goods_stock_number = goods_stock_number;
    }

    public Blob getGoods_picture() {
        return goods_picture;
    }

    public void setGoods_picture(Blob goods_picture) {
        this.goods_picture = goods_picture;
    }

    public String getGoods_author() {
        return goods_author;
    }

    public void setGoods_author(String goods_author) {
        this.goods_author = goods_author;
    }

    public String getGoods_author_introduce() {
        return goods_author_introduce;
    }

    public void setGoods_author_introduce(String goods_author_introduce) {
        this.goods_author_introduce = goods_author_introduce;
    }

    public String getGoods_keywords() {
        return goods_keywords;
    }

    public void setGoods_keywords(String goods_keywords) {
        this.goods_keywords = goods_keywords;
    }

    public String getGoods_bkcatalog() {
        return goods_bkcatalog;
    }

    public void setGoods_bkcatalog(String goods_bkcatalog) {
        this.goods_bkcatalog = goods_bkcatalog;
    }

    public String getGoods_description() {
        return goods_description;
    }

    public void setGoods_description(String goods_description) {
        this.goods_description = goods_description;
    }

    public Blob getGoods_contents() {
        return goods_contents;
    }

    public void setGoods_contents(Blob goods_contents) {
        this.goods_contents = goods_contents;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_flag() {
        return goods_flag;
    }

    public void setGoods_flag(String goods_flag) {
        this.goods_flag = goods_flag;
    }

    public String getGoods_contenturl() {
        return goods_contenturl;
    }

    public void setGoods_contenturl(String goods_contenturl) {
        this.goods_contenturl = goods_contenturl;
    }

    public Integer getGoods_exchange_number() {
        return goods_exchange_number;
    }

    public void setGoods_exchange_number(Integer goods_exchange_number) {
        this.goods_exchange_number = goods_exchange_number;
    }

    public Integer getGoods_return_credits() {
        return goods_return_credits;
    }

    public void setGoods_return_credits(Integer goods_return_credits) {
        this.goods_return_credits = goods_return_credits;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
