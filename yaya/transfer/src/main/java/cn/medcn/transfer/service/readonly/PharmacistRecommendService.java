package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.PharmacistRecommend;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/11/21.
 */
public interface PharmacistRecommendService extends ReadOnlyBaseService<PharmacistRecommend> {
    // 药师建议
    String TABLE_NAME = "drugeducation";

    void transferPharmacistRecommendByCid(Long cid, String categoryId, String rootId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
