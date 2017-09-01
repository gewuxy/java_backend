package cn.medcn.sys.dao;

import cn.medcn.sys.model.SystemMenu;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
public interface SysMenuDAO extends Mapper<SystemMenu> {

    /**
     * 获取父ID下面的所有子菜单
     * @param preid
     * @return
     */
    List<SystemMenu> findAllSubMenus(@Param("preid") Integer preid);

    List<SystemMenu> findRootMenus();

    List<SystemMenu> findMenuByRole(@Param("roleId")Integer roleId);

    List<SystemMenu> findSubMenusByRole(@Param("roleId")Integer roleId, @Param("preid")Integer preid);
}
