package cn.medcn.transfer.jobs;

import cn.medcn.transfer.service.TransferService;
import cn.medcn.transfer.service.TransferServiceImpl;
import cn.medcn.transfer.service.readonly.TransferMeetService;
import cn.medcn.transfer.service.readonly.impl.TransferMeetServiceImpl;
import cn.medcn.transfer.utils.CommonConnctionUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/23.
 */
public class TransferThread extends Thread{
    private TransferService transferService = new TransferServiceImpl();

    private String userName;

    public TransferThread(String userName){
        this.userName = userName;
        this.setName("线程 ["+userName+"] ");
    }

    @Override
    public void run() {
        try {
            transferService.transferByOwner(userName);
            //释放数据库连接资源
            CommonConnctionUtils.closeConnection();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
