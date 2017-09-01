package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.model.writeable.AppUser;
import cn.medcn.transfer.model.writeable.AppUserDetail;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleAppUserService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class WriteAbleAppUserServiceImpl extends WriteAbleBaseServiceImpl<AppUser> implements WriteAbleAppUserService {

    @Override
    public String getTable() {
        return "t_app_user";
    }


    /**
     * 根据旧ID返回当前的ID
     *
     * @param old
     * @return
     */
    @Override
    public Integer findIdByOld(Integer old) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        AppUser condition = new AppUser();
        condition.setOldId(old);
        AppUser user = findOne(condition);
        return user == null ? null : user.getId();
    }

    /**
     * 根据之前的用户名返回当前ID
     *
     * @param owner
     * @return
     */
    @Override
    public Integer findIdByOldName(String owner) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        AppUser condition = new AppUser();
        condition.setUsername(owner);
        AppUser user = findOne(condition);
        return user == null ? null : user.getId();
    }

    /**
     * 将旧的医生数据部分信息添加到用户表中
     * @param appUser
     * @return
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public Integer addAppUserFromOldData(AppUser appUser) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object obj = insertReturnId(appUser);
        if(obj == null){
            return null;
        }
        Long idl = (Long) obj;
        Integer id = idl.intValue();
        return id;
    }

    /**
     * 查询新的公众号用户数据
     * @return
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public List<AppUser> findAppUserList() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        AppUser condition = new AppUser();
        condition.setPubFlag(true);
        return findList(condition);
    }



}
