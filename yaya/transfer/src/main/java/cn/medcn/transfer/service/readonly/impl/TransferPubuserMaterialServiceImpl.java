package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.PubUserMaterialReadOnly;
import cn.medcn.transfer.model.writeable.Material;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferPubuserMaterialService;
import cn.medcn.transfer.service.writeable.WriteAbleMaterialService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMaterialServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class TransferPubuserMaterialServiceImpl extends ReadOnlyBaseServiceImpl<PubUserMaterialReadOnly> implements TransferPubuserMaterialService {

    private WriteAbleMaterialService writeAbleMaterialService = new WriteAbleMaterialServiceImpl();

    @Override
    public String getTable() {
        return "t_public_material";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    /**
     * 查询所有的公众号资料
     * @return
     */
    @Override
    public List<PubUserMaterialReadOnly> findMaterialList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PubUserMaterialReadOnly materialReadOnly = new PubUserMaterialReadOnly();
        List<PubUserMaterialReadOnly> materialList = (List<PubUserMaterialReadOnly>)
                DAOUtils.selectList(getConnection(),materialReadOnly,getTable());
        LogUtils.debug(TransferPubuserMaterialService.class,"--查询所有公众号资料-- "+materialList.size());
        return materialList;
    }

    /**
     * 转换公众号资料
     */
    @Override
    public void transferMaterial() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<PubUserMaterialReadOnly> materialList = findMaterialList();
        LogUtils.debug(TransferPubuserMaterialServiceImpl.class,"--转换公众号资料 开始 --");
        for (PubUserMaterialReadOnly materialReadOnly : materialList){
            Material material = Material.build(materialReadOnly);
            LogUtils.debug(TransferPubuserMaterialServiceImpl.class,"--转换公众号资料名称--"+material.getMaterialName());
            writeAbleMaterialService.addMaterialFromPubUserMaterial(material);
        }
        LogUtils.debug(TransferPubuserMaterialServiceImpl.class,"--转换公众号资料 结束--");
    }

    @Override
    public List<PubUserMaterialReadOnly> findMaterialListByUserId(Integer userId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        PubUserMaterialReadOnly condition = new PubUserMaterialReadOnly();
        condition.setPub_user_id(userId);
        List<PubUserMaterialReadOnly> materialList = (List<PubUserMaterialReadOnly>)
                DAOUtils.selectList(getConnection(),condition,getTable());
        LogUtils.debug(TransferPubuserMaterialService.class,"--公众号【"+userId+"】资料大小-- "+materialList.size());
        return materialList;
    }

    @Override
    public void transferMaterialByUserId(Integer userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<PubUserMaterialReadOnly> list = findMaterialListByUserId(userId);
        for (PubUserMaterialReadOnly materialReadOnly : list){
            Material material = Material.build(materialReadOnly);
            LogUtils.debug(TransferPubuserMaterialServiceImpl.class,"--转换公众号资料名称--"+material.getMaterialName());
            writeAbleMaterialService.addMaterialFromPubUserMaterial(material);
        }
        LogUtils.debug(TransferPubuserMaterialServiceImpl.class,"--转换公众号资料 结束--");
    }
}
