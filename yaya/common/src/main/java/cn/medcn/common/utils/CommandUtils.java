package cn.medcn.common.utils;

import cn.medcn.common.excptions.SystemException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lixuan on 2017/7/7.
 */
public class CommandUtils {

    /**
     * 判断操作系统是否是linux
     * @return
     */
    public static boolean isLinux(){
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }

    public static void CMD(String command) {
        try {
            if (isLinux()) {
                String[] cmd = new String[]{"sh", "-c", command};
                CMDProcess(Runtime.getRuntime().exec(cmd));
            } else {
                CMDProcess(Runtime.getRuntime().exec(command));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void CMDProcess(Process process) {
        try {
            //阻塞进行为同步等待
            int exitValue = process.waitFor();
            if (exitValue != 0) {
                throw new SystemException("exec process fail");
            }
            CMDProcess(process.getErrorStream());
            CMDProcess(process.getInputStream());
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
