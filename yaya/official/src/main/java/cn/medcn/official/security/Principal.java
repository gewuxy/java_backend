package cn.medcn.official.security;

import cn.medcn.user.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal implements Serializable {

    private String id;

    private String account;

    private String username;

    protected String nickName;

    private Integer roleId;

    public static Principal build(Patient user){
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
