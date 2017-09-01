package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.PubUserMemberReadOnly;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class AppAttention {
    private Integer id;
    /**关注时间*/
    private Date attentionTime;
    /**关注者ID*/
    private Integer slaverId;
    /**被关注者ID*/
    private Integer masterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(Date attentionTime) {
        this.attentionTime = attentionTime;
    }

    public Integer getSlaverId() {
        return slaverId;
    }

    public void setSlaverId(Integer slaverId) {
        this.slaverId = slaverId;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public static AppAttention build(PubUserMemberReadOnly userMemberReadOnly){
        AppAttention appAttention = new AppAttention();
        //appAttention.setId(userMemberReadOnly.getPum_id());
        appAttention.setAttentionTime(userMemberReadOnly.getAttention_time());
        appAttention.setMasterId(userMemberReadOnly.getPub_user_id());
        appAttention.setSlaverId(userMemberReadOnly.getUser_id());
        return  appAttention;
    }
}
