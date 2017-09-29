package cn.medcn.user.model;

import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dto.CspUserInfoDTO;
import com.jcraft.jsch.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/** csp用户信息
 * Created by LiuLP on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_user_info")
public class CspUserInfo implements Serializable{
    @Id
    protected String id;

    protected String nickName;

    protected String userName;

    protected String password;

    protected String avatar;

    protected String country;

    protected String province;

    protected String city;

    protected String district;

    protected String email;

    protected String mobile;

    protected String major;

    protected Date lastLoginTime;

    protected String lastLoginIp;

    protected String token;

    protected Date registerTime;

    protected String info;

    // 国内=0、海外=1
    protected Boolean abroad;
    // 是否有激活 未激活=0 已激活=1
    protected Boolean active;


    public static CspUserInfo buildToUserInfo(CspUserInfoDTO dto) {
        CspUserInfo userInfo = new CspUserInfo();
        userInfo.setId(StringUtils.nowStr());
        userInfo.setNickName(dto.getNickName());
        userInfo.setAvatar(dto.getAvatar());
        userInfo.setCountry(dto.getCountry());
        userInfo.setProvince(dto.getProvince());
        userInfo.setCity(dto.getCity());
        userInfo.setDistrict(dto.getDistrict());
        userInfo.setRegisterTime(new Date());
        return userInfo;
    }

    /**
     * 邮箱模板内容
     */
    public enum MailTemplate{
        REGISTER(0, "register"), // 注册
        FIND_PWD(1, "pwdRest"), // 找回密码
        BIND(2, "bindEmail"); // 绑定

        private Integer labelId;
        private String label;

        public Integer getLabelId() {
            return labelId;
        }

        public void setLabelId(Integer labelId) {
            this.labelId = labelId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        MailTemplate(Integer labelId, String label){
            this.labelId = labelId;
            this.label = label;
        }
    }
}
