package cn.medcn.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/2.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_sys_role")
public class SystemRole implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**角色名称*/
    private String roleName;
    /**角色描述*/
    private String roleDesc;
    /**角色权重*/
    private Integer roleWeight;

    public enum SystemRoleType{
        ADMIN(1, "管理员"),
        EDITOR(2, "内容管理员");

        private Integer roleId;

        private String roleName;

        public Integer getRoleId() {
            return roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        SystemRoleType(Integer roleId, String roleName){
            this.roleId = roleId;
            this.roleName = roleName;
        }
    }
}
