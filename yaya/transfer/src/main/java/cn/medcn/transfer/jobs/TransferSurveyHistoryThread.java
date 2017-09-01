package cn.medcn.transfer.jobs;

import cn.medcn.transfer.service.readonly.TransferMeetService;
import cn.medcn.transfer.service.readonly.impl.TransferMeetServiceImpl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/29.
 */
public class TransferSurveyHistoryThread extends Thread {

    private TransferMeetService transferService = new TransferMeetServiceImpl();

    private String userName;

    public TransferSurveyHistoryThread(String userName){
        this.userName = userName;
        this.setName("线程 ["+userName+"] ");
    }



    @Override
    public void run() {
        try {
            transferService.transferSurveyHistoryByOwner(userName);
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
