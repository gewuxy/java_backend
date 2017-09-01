package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.model.writeable.xsCredits;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleXsCreditService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/24.
 */
public class WriteAbleXsCreditServiceImpl extends WriteAbleBaseServiceImpl<xsCredits> implements WriteAbleXsCreditService{
    @Override
    public String getTable() {
        return "t_xs_credits";
    }


    @Override
    public void addXsCredit(xsCredits credits) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(credits);
    }

}
