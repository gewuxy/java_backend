package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class PubUserMemberReadOnly {
    private Integer pum_id;
    private Integer pub_user_id; //公众号ID
    private Integer user_id; // 用户ID
    private String user_name;// 用户姓名
    private Date attention_time; // 关注时间
    private Integer group_id; // 用户所在公众号分组ID

    public Integer getPum_id() {
        return pum_id;
    }

    public void setPum_id(Integer pum_id) {
        this.pum_id = pum_id;
    }

    public Integer getPub_user_id() {
        return pub_user_id;
    }

    public void setPub_user_id(Integer pub_user_id) {
        this.pub_user_id = pub_user_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getAttention_time() {
        return attention_time;
    }

    public void setAttention_time(Date attention_time) {
        this.attention_time = attention_time;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }
}
