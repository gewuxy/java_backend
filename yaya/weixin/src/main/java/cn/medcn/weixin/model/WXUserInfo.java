package cn.medcn.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by LiuLP on 2017/7/21/021.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_wx_userinfo")
public class WXUserInfo {

    private Boolean subscribe;

    private String openid;

    private String nickname;

    private Integer sex;

    private String language;

    private String country;

    private String province;

    private String city;

    private String headimgurl;

    private Long subscribe_time;
    @Id
    private String unionid;

}
