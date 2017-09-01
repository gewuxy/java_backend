package cn.medcn.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import cn.medcn.article.model.Article;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 本
 * Created by weilong on 2017/7/24.
 */
@Data
@NoArgsConstructor
public class ArticleSearchDTO implements Serializable {

    @Field
    protected String id;

    /**
     * 文章标题
     */
    @Field
    protected String title;
    @Field
    /**栏目ID*/
    protected String categoryId;
    @Field
    /**创建时间*/
    protected String createTime;
    @Field
    /**来源*/
    protected String xfrom;
    @Field
    /**作者*/
    protected String author;
    @Field
    /**文章内容 html格式*/
    protected String content;
    @Field
    /**文章摘要*/
    protected String summary;
    @Field
    /**pdf或者word格式源文档地址*/
    protected String filePath;
    @Field
    /**文章显示的图片*/
    protected String articleImg;
    @Field
    /**文章关键字*/
    public String keywords;
    @Field
    /**文章是否审核*/
    protected String authed;
    @Field
    /**文章点击量*/
    protected String hits;
    @Field
    /**外链文章的地址*/
    protected String outLink;
    @Field
    /**权重 影响列表中的排序 权重越大排在越前面*/
    protected String weight;
    @Field
    /**旧数据ID*/
    protected String oldId;
    @Field
    /** 小图片地址*/
    protected String articleImgS;
    @Field
    /**目录结构id列表*/
    protected String historyId;


}
