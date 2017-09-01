package cn.medcn.api.wexin.security;

import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/7/28.
 */
@Data
@NoArgsConstructor
public class Principal {

    private Integer id;

    private String openid;

    private String unionid;

    private String nickname;

    private String linkman;

    private Integer roleId;

    public static Principal build(AppUser appUser){
        Principal principal = new Principal();
        principal.setId(appUser.getId());
        principal.setUnionid(appUser.getUnionid());
        principal.setOpenid(appUser.getOpenid());
        principal.setLinkman(appUser.getLinkman());
        principal.setNickname(appUser.getNickname());
        principal.setRoleId(AppRole.AppRoleType.DOCTOR.getId());
        return principal;
    }
}
