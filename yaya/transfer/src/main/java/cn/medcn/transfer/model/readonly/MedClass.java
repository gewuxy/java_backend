package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedClass {

    protected Long cid;

    protected String pid;

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
