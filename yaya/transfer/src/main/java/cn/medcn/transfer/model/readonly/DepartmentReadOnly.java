package cn.medcn.transfer.model.readonly;

/**
 * Created by Liuchangling on 2017/6/20.
 */
public class DepartmentReadOnly {
    private Long d_did;
    // 科室名称
    private String d_dname;

    public Long getD_did() {
        return d_did;
    }

    public void setD_did(Long d_did) {
        this.d_did = d_did;
    }

    public String getD_dname() {
        return d_dname;
    }

    public void setD_dname(String d_dname) {
        this.d_dname = d_dname;
    }
}
