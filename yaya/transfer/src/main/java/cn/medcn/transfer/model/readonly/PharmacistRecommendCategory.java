package cn.medcn.transfer.model.readonly;

/**
 * Created by Liuchangling on 2017/11/16.
 * 药师建议目录  原始数据
 */

public class PharmacistRecommendCategory {
    // id
    protected Long cid;
    // 父目录id
    protected String pid;
    // 目录名
    protected String name;


    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
