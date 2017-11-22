package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/11/16.
 * 药师建议 原始数据
 */

public class PharmacistRecommend {
    // id
    protected Long deid;
    // 文章标题
    protected String title;
    // 最近更新时间
    protected Date lastUpdateTime;
    // 栏目id
    protected Long cid;
    // 药师建议文章html内容
    protected String yashtml;

    public Long getDeid() {
        return deid;
    }

    public void setDeid(Long deid) {
        this.deid = deid;
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

    public String getYashtml() {
        return yashtml;
    }

    public void setYashtml(String yashtml) {
        this.yashtml = yashtml;
    }
}
