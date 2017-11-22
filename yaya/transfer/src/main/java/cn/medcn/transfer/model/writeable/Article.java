package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.DoctorSuggested;
import cn.medcn.transfer.model.readonly.PharmacistRecommend;
import cn.medcn.transfer.utils.StringUtils;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/11/17.
 */

public class Article {

    protected String id;
    /**文章标题*/
    protected String title;
    /**栏目ID*/
    protected String categoryId;
    /**创建时间*/
    protected Date createTime;
    /**来源*/
    protected String xfrom;
    /**作者*/
    protected String author;
    /**文章内容 html格式*/
    protected String content;
    /**文章摘要*/
    protected String summary;
    /**pdf或者word格式源文档地址*/
    protected String filePath;
    /**文章显示的图片*/
    protected String articleImg;
    /**文章关键字*/
    protected String keywords;
    /**文章是否审核*/
    protected Boolean authed;
    /**文章点击量*/
    protected Integer hits;
    /**外链文章的地址*/
    protected String outLink;
    /**权重 影响列表中的排序 权重越大排在越前面*/
    protected Integer weight;
    /**旧数据ID*/
    protected Integer oldId;
    /** 小图片地址*/
    protected String articleImgS;
    /** 目录结构链，从跟目录到自身目录ID本身,用下划线分隔*/
    protected String historyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getXfrom() {
        return xfrom;
    }

    public void setXfrom(String xfrom) {
        this.xfrom = xfrom;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getAuthed() {
        return authed;
    }

    public void setAuthed(Boolean authed) {
        this.authed = authed;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getArticleImgS() {
        return articleImgS;
    }

    public void setArticleImgS(String articleImgS) {
        this.articleImgS = articleImgS;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public static Article build(DoctorSuggested suggested, String categoryId) {
        Article article = new Article();
        if (suggested != null) {
            article.setId(StringUtils.getNowStringID());
            article.setCategoryId(categoryId);
            article.setTitle(suggested.getTitle());
            article.setCreateTime(suggested.getLastUpdateTime());
            article.setXfrom(suggested.getAuthor());
            article.setAuthor(suggested.getAuthor());
            article.setContent(suggested.getYshtml());
            article.setSummary(suggested.getWzzy());
            article.setArticleImg(suggested.getImageurl());
            article.setKeywords(suggested.getKeywords());
            article.setAuthed(true);
            article.setOldId(suggested.getYid().intValue());
        }
        return article;
    }

    public static Article build(PharmacistRecommend recommend, String categoryId) {
        Article article = new Article();
        if (recommend != null) {
            article.setId(StringUtils.getNowStringID());
            article.setCategoryId(categoryId);
            String title = recommend.getTitle();
            if (title.contains(".doc")){
                title = title.replace(".doc", "");
            }
            article.setTitle(title);
            article.setCreateTime(recommend.getLastUpdateTime());
            article.setContent(recommend.getYashtml());
            article.setAuthed(true);
            article.setOldId(recommend.getDeid().intValue());
        }
        return article;
    }
}
