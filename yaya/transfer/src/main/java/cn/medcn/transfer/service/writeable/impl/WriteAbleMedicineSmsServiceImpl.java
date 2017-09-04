package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.readonly.Medsms;
import cn.medcn.transfer.model.writeable.MedicineSms;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleMedicineSmsService;
import cn.medcn.transfer.support.Medicine;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

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


    @Override
    public void modifyTitle() {
        try {
            String sql = "select * from t_data_file where root_category = '17051816413005881649' and instr(title,'_') > 0";
            List<MedicineSms> list = (List<MedicineSms>) DAOUtils.selectList(this.getConnection(), sql, null, MedicineSms.class);
            if (list != null && list.size() > 0){
                for (MedicineSms medicineSms : list){
                    String title = medicineSms.getTitle();
                    String[] arr = title.split("_");
                    if (arr.length > 0){
                        title = arr[0];
                        String author = arr.length > 1 ? arr[1] : null;
                        medicineSms.setAuthor(author);
                        medicineSms.setTitle(title);
                        medicineSms.setUpdateDate(new Date());
                    }
                }
            }
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
