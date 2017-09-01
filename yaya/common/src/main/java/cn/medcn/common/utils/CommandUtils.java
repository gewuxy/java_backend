package cn.medcn.common.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lixuan on 2017/7/7.
 */
public class CommandUtils {

    public static void CMD(String command) {
        try {
            CMDProcess(Runtime.getRuntime().exec(command));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void CMDProcess(Process process) {
        try {
            CMDProcess(process.getErrorStream());
            CMDProcess(process.getInputStream());
            //阻塞进行为同步等待
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void CMDProcess(final InputStream inputStream) {
        //如果不进行stream释放，很容易导致底层死锁挂死等问题
        new Thread() {
            public void run() {
                try {
                    while (inputStream.read() > 0) {
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }
}
