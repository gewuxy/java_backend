package cn.medcn.transfer.model.writeable;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class VideoCourse {

    private Integer id;

    private String title;

    private String category;

    private Boolean published;

    private Integer owner;

    private Date createTime;

    private List<VideoCourseDetail> details;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<VideoCourseDetail> getDetails() {
        return details;
    }

    public void setDetails(List<VideoCourseDetail> details) {
        this.details = details;
    }
}
