package cn.medcn.csp.security;

import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.CspUserInfos;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/12.
 */
@Data
@NoArgsConstructor
public class Principal extends CspUserInfos implements Serializable{

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
