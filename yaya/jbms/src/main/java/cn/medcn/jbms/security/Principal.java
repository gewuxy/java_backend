package cn.medcn.jbms.security;

import cn.medcn.sys.model.SystemUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/2.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal implements Serializable {


    private Integer id;

    private String username;

    private String headimg;

    private String nickname;

    private Integer roleId;

    public boolean isAdmin(){
        return this.id == 1;
    }


    public static Principal build(SystemUser user){
        Principal principal = new Principal();
        if(user != null){
            principal.setUsername(user.getUserName());
            principal.setHeadimg(user.getHeadImg());
            principal.setId(user.getId());
            principal.setNickname(user.getRealName());
            principal.setRoleId(user.getRoleId());
        }
        return principal;
    }
}
