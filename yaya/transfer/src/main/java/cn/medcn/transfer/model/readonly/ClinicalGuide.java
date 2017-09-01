package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/8/21.
 */
public class ClinicalGuide {

    protected Long mid;

    protected String title;//标题

    protected String date;//日期

    protected String size;//大小

    protected Long cid;//栏目ID

    protected String znzy;//

    protected String author;

    protected String lcznDate;

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getZnzy() {
        return znzy;
    }

    public void setZnzy(String znzy) {
        this.znzy = znzy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLcznDate() {
        return lcznDate;
    }

    public void setLcznDate(String lcznDate) {
        this.lcznDate = lcznDate;
    }
}
