package cn.medcn.user.dao;

import cn.medcn.user.model.AppMenu;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
public interface AppMenuDAO extends Mapper<AppMenu> {

    List<AppMenu> findMenusByRole(@Param("roleId") Integer roleId);

    List<AppMenu> findAll();
}
