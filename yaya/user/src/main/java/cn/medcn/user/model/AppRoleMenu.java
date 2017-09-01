package cn.medcn.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_app_rolemenu")
public class AppRoleMenu implements Serializable {
    @Id
    private Integer id;

    private Integer roleId;

    private Integer menuId;
}
