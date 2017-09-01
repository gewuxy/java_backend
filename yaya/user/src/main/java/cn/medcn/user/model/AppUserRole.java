package cn.medcn.user.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
@Table(name = "t_app_userrole")
public class AppUserRole implements Serializable{
    @Id
    private Integer id;

    private Integer userId;

    private Integer roleId;

    public AppUserRole(Integer userId, Integer roleId){
        this.userId = userId;
        this.roleId = roleId;
    }
}
