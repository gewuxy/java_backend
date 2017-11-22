package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/11/16.
 * 医师建议 原始数据
 */

public class DoctorSuggested {

    // id
    protected Long yid;
    // 文章标题
    protected String title;
    // 最近更新时间
    protected Date lastUpdateTime;
    // 栏目id
    protected Long cid;
    // 医师建议文章html内容
    protected String yshtml;
    // 医师建议文章图片url
    protected String imageurl;
    // 关键词
    protected String keywords;
    // 文章摘要
    protected String wzzy;
    // 来源
    protected String author;

    public Long getYid() {
        return yid;
    }

    public void setYid(Long yid) {
        this.yid = yid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getYshtml() {
        return yshtml;
    }

    public void setYshtml(String yshtml) {
        this.yshtml = yshtml;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getWzzy() {
        return wzzy;
    }

    public void setWzzy(String wzzy) {
        this.wzzy = wzzy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
