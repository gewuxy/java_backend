package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.PubUserMaterialReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface TransferPubuserMaterialService extends ReadOnlyBaseService<PubUserMaterialReadOnly> {

    /**
     * 查询所有的公众号资料
     * @return
     */
    List<PubUserMaterialReadOnly> findMaterialList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    void transferMaterial() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<PubUserMaterialReadOnly> findMaterialListByUserId(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    void transferMaterialByUserId(Integer userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
