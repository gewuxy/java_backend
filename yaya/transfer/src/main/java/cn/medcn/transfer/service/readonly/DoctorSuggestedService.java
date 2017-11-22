package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.DoctorSuggested;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/11/16.
 */

public interface DoctorSuggestedService extends ReadOnlyBaseService<DoctorSuggested> {
    // 医师建议
    String TABLE_NAME = "t_ysjy";

    void transferDoctorSuggestByCid(Long cid, String categoryId, String rootId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

}
