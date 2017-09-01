package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.Material;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface WriteAbleMaterialService extends WriteAbleBaseService<Material> {

    void addMaterialFromPubUserMaterial (Material material) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
