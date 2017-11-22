package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.DoctorSuggestedCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/11/16.
 */
public interface DoctorSuggestedCategoryService extends ReadOnlyBaseService<DoctorSuggestedCategory> {
    // 医师建议目录
    String TABLE_NAME = "t_ysjyml";

    // 查找根目录列表
    List<DoctorSuggestedCategory> findRootCategory() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    // 查找子节点目录列表
    List<DoctorSuggestedCategory> findCategoryByPid(String pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    // 查找目录信息
    DoctorSuggestedCategory findCategoryByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    // 转移根目录数据
    void transfer() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    // 转移子节点目录数据
    void transferByCid(Long cid, String preId, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;


}
