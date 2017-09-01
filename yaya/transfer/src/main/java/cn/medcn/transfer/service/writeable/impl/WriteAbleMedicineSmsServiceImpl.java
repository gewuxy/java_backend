package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.Medsms;
import cn.medcn.transfer.model.writeable.MedicineSms;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMedicineSmsService;
import cn.medcn.transfer.support.Medicine;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/8/21.
 */
public class WriteAbleMedicineSmsServiceImpl extends WriteAbleBaseServiceImpl<MedicineSms> implements WriteAbleMedicineSmsService {
    @Override
    public String getTable() {
        return "t_data_file";
    }


    @Override
    public void transfer(MedicineSms medicineSms) {

        try {
            insert(medicineSms);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
