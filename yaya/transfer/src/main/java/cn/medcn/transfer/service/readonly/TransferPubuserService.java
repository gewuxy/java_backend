package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.PubUserSourceReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/15.
 */
public interface TransferPubuserService extends ReadOnlyBaseService<PubUserSourceReadOnly> {

    void tranferPubuser() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * 根据用户ID转移公众号用户信息
     */
    PubUserSourceReadOnly transferUserInfo(Long userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    PubUserSourceReadOnly findPubUserByUname(String userName) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Map<String, Integer> findAll() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
