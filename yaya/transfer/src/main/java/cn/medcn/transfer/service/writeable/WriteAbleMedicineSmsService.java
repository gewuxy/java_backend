package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.readonly.Medsms;
import cn.medcn.transfer.model.writeable.MedicineSms;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

/**
 * Created by lixuan on 2017/8/21.
 */
public interface WriteAbleMedicineSmsService extends WriteAbleBaseService<MedicineSms> {


    void transfer(MedicineSms medicineSms);
}
