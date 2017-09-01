package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MedClassMapping;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.MedClassMappingService;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedClassMappingServiceImpl extends ReadOnlyBaseServiceImpl<MedClassMapping> implements MedClassMappingService {
    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }


}
