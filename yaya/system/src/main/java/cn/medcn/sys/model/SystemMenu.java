package cn.medcn.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_sys_menu")
public class SystemMenu implements Serializable{
    @Id
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限
     */
    private String perm;
    /**
     * 父菜单ID
     */
    private Integer preid;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 简述
     */
    private String menuDesc;
    /**
     * 菜单url
     */
    private String url;
    /**
     * url目标
     */
    private String target;
    /**
     * 是否隐藏
     */
    private Boolean hide;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 子菜单列表
     */
    @Transient
    private List<SystemMenu> menuList;

    @Transient
    private SystemMenu parent;
}
