package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.model.writeable.AppUser;
import cn.medcn.transfer.model.writeable.AppUserDetail;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public interface WriteAbleAppUserService extends WriteAbleBaseService<AppUser>{

    /**
     * 根据旧ID返回当前的ID
     * @param old
     * @return
     */
    Integer findIdByOld(Integer old) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * 根据之前的用户名返回当前ID
     * @param owner
     * @return
     */
    Integer findIdByOldName(String owner) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * 将旧的用户部分信息添加到新的用户表
     * @param appUser
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    Integer addAppUserFromOldData(AppUser appUser) throws IntrospectionException,InstantiationException,IllegalAccessException,InvocationTargetException;

    // 查询新的医生用户数据
    List<AppUser> findAppUserList() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


}
