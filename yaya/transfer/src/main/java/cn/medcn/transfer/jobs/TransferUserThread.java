package cn.medcn.transfer.jobs;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.service.readonly.TransferDoctorService;
import cn.medcn.transfer.service.readonly.impl.TransferDoctorServiceImpl;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.utils.CommonConnctionUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/23.
 */
public class TransferUserThread extends Thread{

    private TransferDoctorService transferDoctorService = new TransferDoctorServiceImpl();


    private int count = 0;

    private int pageSize = 10000;

    public TransferUserThread (){
        this.setName("转换医生线程");
    }

    @Override
    public void run() {

        try {
            Pageable pageable = new Pageable(1, pageSize);
            DoctorReadOnly condition = new DoctorReadOnly();
            condition.setUser_role(0);
            pageable.setCondition(condition);
            int pages = transferDoctorService.transferAppUser(pageable);
            for(int i = 2; i <= pages; i++){
                pageable.setPageNum(i);
                transferDoctorService.transferAppUser(pageable);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CommonConnctionUtils.closeConnection();
    }
}
