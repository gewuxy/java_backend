package cn.medcn.user.model;

import cn.medcn.common.utils.StringUtils;
import cn.medcn.weixin.model.WXUserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/20.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_user")
public class AppUser {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**用户名*/
    private String username;
    /**昵称*/
    private String nickname;
    /**真实姓名*/
    private String linkman;
    /**用户头像*/
    private String headimg;
    /**联系电话*/
    private String mobile;
    /**密码*/
    private String password;
    /**微信OPENID*/
    private String unionid;
    /**历史遗留ID*/
    private Integer oldId;
    /**最后登录时间*/
    private Date lastLoginTime;
    /**最后登录IP*/
    private String lastLoginIp;
    /**是否审核通过*/
    private Boolean authed;
    /**是否是公众号*/
    private Boolean pubFlag;
    /**个性签名*/
    private String sign;
    /**注册时间*/
    private Date registDate;
    /**审核人ID*/
    private Integer authedBy;
    /**是否是推荐公众号 只有公众号才有此属性*/
    private Boolean tuijian;

    private String token;

    protected Boolean testFlag;//测试标记 标识该用户未测试用户 主要用于单位号的会议不会被选择到

    /**省份*/
    protected String province;
    /**城市*/
    protected String city;
    /**区*/
    protected String zone;

    /**身份证号*/
    protected String identify;
    /**年龄*/
    protected Integer age;
    /**角色ID*/
    protected Integer roleId;

    protected String address;

    protected String degree;

    protected Integer gender;

    protected String openid;

    @Transient
    private AppUserDetail userDetail;

    @Transient
    protected String roleName;

    @Transient
    private Integer credits;

    @Transient
    private WXUserInfo wxUserInfo;

    /*数量统计*/
    @Transient
    private String years;

    @Transient
    private String months;

    @Transient
    private String counts;




    public String getRoleName(){
        if(this.roleId == null || this.roleId == 0){
            return "";
        }
        return AppRole.AppRoleType.values()[this.roleId-1].getRoleName();
    }

    public static void splitUserAvatar(List<AppUser> userList, String baseUrl){
        if(userList != null){
            for(AppUser user:userList){
                if(!StringUtils.isEmpty(user.getHeadimg())){
                    user.setHeadimg(baseUrl + user.getHeadimg());
                }
            }
        }
    }

}
