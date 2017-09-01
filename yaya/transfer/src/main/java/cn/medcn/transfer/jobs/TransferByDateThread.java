package cn.medcn.transfer.jobs;

import cn.medcn.transfer.service.TransferService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/7/6.
 */
public class TransferByDateThread extends Thread {

    private TransferService transferService;

    private String date;

    private String owner;

    public TransferByDateThread(TransferService transferService, String date, String owner){
        this.transferService = transferService;
        this.date = date;
        this.owner = owner;
        this.setName(owner);
    }

    @Override
    public void run() {
        try {
            transferService.transferByOwnerAndDate(owner, date);
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
