package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/20.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_role")
public class AppRole implements Serializable{
    @Id
    private Integer id;
    /**角色名称*/
    private String roleName;
    /**角色描述*/
    private String roleDesc;
    /**角色权重*/
    private Integer roleWeight;

    public enum AppRoleType{
        PUB_USER(1,"单位号",999),
        DOCTOR(2,"医生", 10),
        YAOSHI(3,"药师", 10),
        PATIENT(4,"患者", 1);

        private Integer id;

        private String roleName;

        private Integer weight;

        public Integer getId(){
            return this.id;
        }

        public String getRoleName(){
            return this.roleName;
        }

        public Integer getWeight(){
            return this.weight;
        }

        AppRoleType(Integer id, String roleName, Integer weight){
            this.id = id;
            this.roleName = roleName;
            this.weight = weight;
        }
    }
}
