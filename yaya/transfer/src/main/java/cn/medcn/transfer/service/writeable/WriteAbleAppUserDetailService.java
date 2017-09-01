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
public interface WriteAbleAppUserDetailService extends WriteAbleBaseService<AppUserDetail>{

    /**
     * 将旧的医生数据部分信息添加到用户明细表中
     * @param userDetail
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    void addAppUserDetailFromOldData(AppUserDetail userDetail) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;


}
