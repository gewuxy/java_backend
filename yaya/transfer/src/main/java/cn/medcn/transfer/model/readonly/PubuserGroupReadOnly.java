package cn.medcn.transfer.model.readonly;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class PubuserGroupReadOnly {
    private Integer pub_groupid;
    private Integer pub_user_id;
    private String group_name;

    public Integer getPub_groupid() {
        return pub_groupid;
    }

    public void setPub_groupid(Integer pub_groupid) {
        this.pub_groupid = pub_groupid;
    }

    public Integer getPub_user_id() {
        return pub_user_id;
    }

    public void setPub_user_id(Integer pub_user_id) {
        this.pub_user_id = pub_user_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
