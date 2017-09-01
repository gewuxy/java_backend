package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.PubuserGroupReadOnly;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class UserGroup {
    private Integer id;
    private Integer pubUserId;
    private String groupName;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPubUserId() {
        return pubUserId;
    }

    public void setPubUserId(Integer pubUserId) {
        this.pubUserId = pubUserId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static UserGroup build(PubuserGroupReadOnly pubuserGroupReadOnly){
        UserGroup userGroup = new UserGroup();
        userGroup.setId(pubuserGroupReadOnly.getPub_groupid());
        userGroup.setPubUserId(pubuserGroupReadOnly.getPub_user_id());
        userGroup.setGroupName(pubuserGroupReadOnly.getGroup_name());
        return userGroup;
    }
}
