package cn.medcn.csp.admin.security;

import cn.medcn.csp.admin.model.CspSysUser;
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

    private String account;

    private String username;

    private Integer roleId;

    public boolean isAdmin(){
        return this.id == 1;
    }

    public static Principal build(CspSysUser user){
        Principal principal = new Principal();
        if(user != null){
            principal.setUsername(user.getUserName());
            principal.setAccount(user.getAccount());
            principal.setId(user.getId());
            principal.setRoleId(user.getRoleId());
        }
        return principal;
    }
}
