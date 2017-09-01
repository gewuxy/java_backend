package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.MedicineSmsDetail;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMedicineSmsDetailService;

/**
 * Created by lixuan on 2017/8/21.
 */
public class WriteAbleMedicineSmsDetailServiceImpl extends WriteAbleBaseServiceImpl<MedicineSmsDetail> implements WriteAbleMedicineSmsDetailService {
    @Override
    public String getTable() {
        return "t_data_file_detail";
    }
}
