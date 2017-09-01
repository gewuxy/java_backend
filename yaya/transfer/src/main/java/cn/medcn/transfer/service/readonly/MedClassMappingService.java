package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MedClassMapping;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

/**
 * Created by lixuan on 2017/8/21.
 */
public interface MedClassMappingService extends ReadOnlyBaseService<MedClassMapping> {

    String TABLE_NAME = "t_medsms_medclass";
}
