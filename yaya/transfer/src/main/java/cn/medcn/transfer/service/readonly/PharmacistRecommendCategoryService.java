package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.PharmacistRecommendCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/11/20.
 */

public interface PharmacistRecommendCategoryService extends ReadOnlyBaseService<PharmacistRecommendCategory>{
    // 药师建议目录
    String TABLE_NAME = "drugedu";

   // 查找根目录列表
    List<PharmacistRecommendCategory> findRootCategory() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException  ;

    // 查找子节点目录列表
    List<PharmacistRecommendCategory> findCategoryByPid(String pid) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException ;

    // 查找目录信息
    PharmacistRecommendCategory findCategoryByCid(Long cid) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException  ;

    // 转移根目录数据
    void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException ;

    // 转移子节点目录数据
    void transferByCid(Long cid, String preId, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException ;

}
