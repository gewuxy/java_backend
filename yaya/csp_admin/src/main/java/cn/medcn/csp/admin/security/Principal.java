package cn.medcn.csp.admin.security;

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

    private String userName;

    private String headImg;

    private String nickName;

    private Integer roleId;

    public boolean isAdmin(){
        return this.id == 1;
    }


    public static Principal build(SystemUser user){
        Principal principal = new Principal();
        if(user != null){
            principal.setUserName(user.getUserName());
            principal.setHeadImg(user.getHeadImg());
            principal.setId(user.getId());
            principal.setNickName(user.getRealName());
            principal.setRoleId(user.getRoleId());
        }
        return principal;
    }
}
