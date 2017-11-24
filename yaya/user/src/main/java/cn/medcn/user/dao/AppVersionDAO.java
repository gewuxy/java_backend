package cn.medcn.user.dao;

import cn.medcn.user.model.AppVersion;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/6/8.
 */
public interface AppVersionDAO extends Mapper<AppVersion> {

    /**
     * 查找最新的版本信息
     * @param appType
     * @param driveTag
     * @return
     */
    AppVersion findNewly(@Param("appType")String appType, @Param("driveTag") String driveTag);

    /**
     * 查看APP上架列表
     * @return
     */
    List<AppVersion> findappManageListByPage();

}
