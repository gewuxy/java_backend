package cn.medcn.csp.admin.dao;

import cn.medcn.user.model.AppVersion;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 12:08 2017/11/10
 */
public interface AppManageDao extends Mapper<AppVersion>{
    List<AppVersion> findappManageListByPage();

    AppVersion selectFirstApp();
}
