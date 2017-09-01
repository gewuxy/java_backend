package cn.medcn.transfer.jobs;

import cn.medcn.transfer.service.readonly.TransferMeetService;
import cn.medcn.transfer.service.readonly.impl.TransferMeetServiceImpl;
import cn.medcn.transfer.utils.CommonConnctionUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/27.
 */
public class TransferExamHsitoryThread extends Thread{
    private TransferMeetService transferService = new TransferMeetServiceImpl();

    private String userName;

    public TransferExamHsitoryThread(String userName){
        this.userName = userName;
        this.setName("线程 ["+userName+"] ");
    }

    @Override
    public void run() {
        try {
            transferService.transferExamByOnwer(userName);
            //释放数据库连接资源
            CommonConnctionUtils.closeConnection();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
