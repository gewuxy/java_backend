package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.PubUserMemberReadOnly;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class UserDoctorGroup {
    private Integer id;
    private Integer groupId;
    private Integer doctorId;
    private Integer pubUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPubUserId() {
        return pubUserId;
    }

    public void setPubUserId(Integer pubUserId) {
        this.pubUserId = pubUserId;
    }

    public static UserDoctorGroup build(PubUserMemberReadOnly userMemberReadOnly){
        UserDoctorGroup userDoctorGroup = new UserDoctorGroup();
        if (userMemberReadOnly.getGroup_id()!=0){
            userDoctorGroup.setId(userMemberReadOnly.getPum_id());
            userDoctorGroup.setPubUserId(userMemberReadOnly.getPub_user_id());
            userDoctorGroup.setDoctorId(userMemberReadOnly.getUser_id());
            userDoctorGroup.setGroupId(userMemberReadOnly.getGroup_id());
        }
        return userDoctorGroup;
    }
}
