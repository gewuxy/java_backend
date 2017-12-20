package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/12.
 */
@Data
@NoArgsConstructor
public class Principal implements Serializable{

    protected String id;

    protected String nickName;

    protected String mobile;

    protected String email;

    protected String token;

    protected String avatar;

    protected Boolean abroad;

    //是否新用户
    protected Boolean newUser;

    //套餐id
    protected Integer packageId;

    // 用户套餐
    protected CspPackage cspPackage;

    //购买成功消息
    protected String pkChangeMsg;
    //老用戶為true 新用戶為false
    protected Boolean state;
    //是否冻结 true 表示未冻结 false 表示已冻结
    protected Boolean active;

    public static Principal build(CspUserInfo userInfo){
        if (userInfo != null) {
            Principal principle = new Principal();
            principle.setId(userInfo.getId());
            principle.setMobile(userInfo.getMobile());
            principle.setEmail(userInfo.getEmail());
            principle.setNickName(userInfo.getNickName());
            principle.setToken(userInfo.getToken());
            principle.setAbroad(userInfo.getAbroad() == null ? false : userInfo.getAbroad());
            principle.setAvatar(userInfo.getAvatar());
            principle.setState(userInfo.getState());
            principle.setActive(userInfo.getActive());

            return principle;
        }

        return null;
    }

}
