package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/9/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_bind_info")
public class BindInfo {

    protected String id;
    // 用户id
    protected String userId;
    // 第三方平台id 1代表微信，2代表微博，3代表facebook,4代表twitter，5代表yaya医师
    protected Integer thirdPartyId;
    // 绑定时间
    protected Date bindDate;
    // 昵称
    protected String nickName;
    // 第三方唯一标识 例如微信的openid
    protected String uniqueId;
    // 性别
    protected String gender;
    // 头像
    protected String avatar;

    public enum Type {
        WE_CHAT(1, "微信"),
        WEI_BO(2, "微博"),
        FACEBOOK(3, "facebook"),
        TWITTER(4, "twitter"),
        YaYa(5, "YaYa医师"),
        MOBILE(6, "手机"),
        EMAIL(7, "邮箱");

        private Integer typeId;
        private String label;

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        Type(Integer typeId, String label){
            this.typeId = typeId;
            this.label = label;
        }
    }
}