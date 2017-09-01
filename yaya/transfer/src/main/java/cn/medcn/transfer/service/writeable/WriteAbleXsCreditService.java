package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.xsCredits;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/24.
 */
public interface WriteAbleXsCreditService extends WriteAbleBaseService<xsCredits> {
    void addXsCredit(xsCredits credits) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
