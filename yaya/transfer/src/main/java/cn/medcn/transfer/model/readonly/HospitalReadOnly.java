package cn.medcn.transfer.model.readonly;

/**
 * Created by Liuchangling on 2017/6/20.
 */
public class HospitalReadOnly {

    private Long h_id;
    //  医院名称
    private String h_cname;
    //  医院等级
    private String h_level;

    public Long getH_id() {
        return h_id;
    }

    public void setH_id(Long h_id) {
        this.h_id = h_id;
    }

    public String getH_cname() {
        return h_cname;
    }

    public void setH_cname(String h_cname) {
        this.h_cname = h_cname;
    }

    public String getH_level() {
        return h_level;
    }

    public void setH_level(String h_level) {
        this.h_level = h_level;
    }
}
