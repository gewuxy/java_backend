package cn.medcn.common.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by lixuan on 2017/2/23.
 */
public class ZIPUtils {

    /**
     * 解压zip文件
     * @param zipFile
     * @param outPath
     * @return
     */
    public static int unzip(File zipFile, String outPath){
        ZipInputStream zis = null;
        BufferedInputStream bis = null;
        File entryFile=null;
        ZipEntry entry;
        int entryCount = 0;
        try {
            zis=new ZipInputStream(new FileInputStream(zipFile));//输入源zip路径
            bis=new BufferedInputStream(zis);
            while((entry = zis.getNextEntry())!=null && !entry.isDirectory()){
                entryFile=new File(outPath,entry.getName());
                File outPathDir = new File(outPath);
                if(!outPathDir.exists()){
                    outPathDir.mkdirs();
                }
                FileOutputStream out=new FileOutputStream(entryFile);
                BufferedOutputStream bout=new BufferedOutputStream(out);
                int b;
                while((b=bis.read())!=-1){
                    bout.write(b);
                }
                entryCount++;
                bout.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return entryCount;
    }

    public static void main(String[] args) {
        File zipFile = new File("D:\\lixuan\\test\\test.zip");
        int entryCount = unzip(zipFile, "D:\\lixuan\\test\\ppt\\");
        System.out.println("成功解压"+entryCount+"个文件");
    }
}
