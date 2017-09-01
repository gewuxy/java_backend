package cn.medcn.jcms.security;

import cn.medcn.user.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal implements Serializable {

    private Integer id;

    private String username;

    private String nickname;

    private String linkman;

    private String headimg;

    private Integer roleId;

    public static Principal build(AppUser user){
        Principal principal = new Principal();
        if(user != null){
            principal.setId(user.getId());
            principal.setUsername(user.getUsername());
            principal.setLinkman(user.getLinkman());
            principal.setNickname(user.getNickname());
            principal.setHeadimg(user.getHeadimg());
            principal.setRoleId(user.getRoleId());
        }
        return principal;
    }

}
