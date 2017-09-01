package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.model.writeable.AppUser;
import cn.medcn.transfer.model.writeable.AppUserDetail;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleAppUserDetailService;
import cn.medcn.transfer.service.writeable.WriteAbleAppUserService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class WriteAbleAppUserDetailServiceImpl extends WriteAbleBaseServiceImpl<AppUserDetail> implements WriteAbleAppUserDetailService {

    @Override
    public String getTable() {
        return "t_app_user_detail";
    }


    /**
     * 将旧的医生数据部分信息添加到用户明细表中
     * @param userDetail
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public void addAppUserDetailFromOldData(AppUserDetail userDetail) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(userDetail);
    }



}
