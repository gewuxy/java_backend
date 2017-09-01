package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.Material;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMaterialService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class WriteAbleMaterialServiceImpl extends WriteAbleBaseServiceImpl<Material> implements WriteAbleMaterialService{
    @Override
    public String getTable() {
        return "t_material";
    }


    @Override
    public void addMaterialFromPubUserMaterial(Material material) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(material);
    }
}
