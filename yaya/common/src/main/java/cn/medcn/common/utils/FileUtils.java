package cn.medcn.common.utils;

import cn.medcn.common.Constants;
import cn.medcn.common.supports.MediaInfo;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Created by lixuan on 2017/1/5.
 */
public class FileUtils {

    // 生成新闻缩略图路径
    public static String realUploadPath = "Z:\\news\\images\\";

    // 缩略图片宽
    public static int thumbWidth = 120;

    // 缩略图片高
    public static int thumbHeight = 120;

    // 背景音乐存放路径
    public static String backgroundMusicPath = "";


    public static String readFromInputStream(InputStream inputStream){
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
            isr = new InputStreamReader(inputStream,"UTF-8");
            br = new BufferedReader(isr);
            String str ;
            StringBuilder sb = new StringBuilder();
            while((str = br.readLine()) != null){
                sb.append(str);
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                isr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String fileSize2String(Long fileSize){
        int k = 1024;
        int m = k*1024;
        if(fileSize == null){
            fileSize = 0L;
        }
        if(fileSize > m){//大于1M
            return String.format("%.1f",fileSize*0.1*10/m)+"MB";
        }else if(fileSize > k){
            return String.format("%.1f",fileSize*0.1*10/k)+"KB";
        }else{
            return fileSize+"B";
        }
    }

    /**
     * 删除指定文件下的所有文件
     * @param dir
     */
    public static void deleteSubFiles(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if(file.isFile()){
                file.delete();
            }else{
                deleteSubFiles(file);
            }
        }
    }

    /**
     * 根据路径删除指定问价
     * @param filePath
     */
    public static void deleteTargetFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 获取文件夹下的子文件数目
     * @param dir
     * @return
     */
    public static int countFiles(File dir){
        File[] files = dir.listFiles();
        return files == null ? 0: files.length;
    }

    /**
     * 解析音频媒体信息
     * @param filePath
     * @return
     */
    public static MediaInfo parseAudioMediaInfo(String filePath){
        if(StringUtils.isEmpty(filePath)){
            return null;
        }
        if(filePath.toLowerCase().endsWith(".mp4") || filePath.toLowerCase().endsWith(".mp3")){
            return parseMp3(filePath);
        }
        return parseOtherAudio(filePath);
    }


    public static MediaInfo parseMp3(String mp3){
        if(StringUtils.isEmpty(mp3)){
            return null;
        }
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(mp3);
            int b=fis.available();
            Bitstream bitStream=new Bitstream(fis);
            Header header = bitStream.readFrame();
            int bitrate = header.bitrate();
            int time = (int) header.total_ms(b);
            return new MediaInfo(time/1000, bitrate);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (BitstreamException e){
            e.printStackTrace();
        } finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static MediaInfo parseOtherAudio(String audioFilePath){
        if(StringUtils.isEmpty(audioFilePath)){
            return null;
        }
        File file = new File(audioFilePath);
        if(!file.exists()){
            return null;
        }
        AudioInputStream audioInputStream = null;
        Clip clip = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            int bitrate = Math.round(audioInputStream.getFormat().getSampleRate());
            int duration = (int)clip.getMicrosecondLength()/1000/1000;
            return new MediaInfo(duration, bitrate);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        } finally {
            if(audioInputStream != null){
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(clip != null){
                clip.close();
            }
        }
        return null;
    }


    public static void downloadNetWorkFile(String urlString, String saveDir, String fileName){
        File dir = new File(saveDir);
        if (!dir.exists()){
            dir.mkdirs();
        }
        String saveFileName = saveDir+ "/" + fileName;
        InputStream is = null;
        OutputStream os = null;
        try{
            URL url = new URL(urlString);
            // 打开连接
            URLConnection conn = url.openConnection();
            is = conn.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            // 输出的文件流
            os = new FileOutputStream(saveFileName);
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param filePath
     * @param filename
     * @param file     源文件
     */
    public static void saveFile(String filePath, String filename, File file) {
        File newsFileRoot = new File(filePath);
        if (!newsFileRoot.exists()) {
            newsFileRoot.mkdirs();
        }
        saveFile(file, new File(filePath + filename));
    }

    /**
     *
     * @param source 源文件
     * @param dest 目标文件
     */
    public static void saveFile(File source, File dest) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {

            fos =new FileOutputStream(dest);
            fis = new FileInputStream(source);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    public static String sizeStr(Long size, FileSize format){
        if (format == null) {
            format = FileSize.M;
        }
        if (format.equals(FileSize.G)) {
            return gSize(size);
        } else if (format.equals(FileSize.M)){
            return mSize(size);
        } else {
            return kSize(size);
        }
    }


    public static String sizeStr(Long size){
        if (size > Constants.BYTE_UNIT_G) {
            return sizeStr(size, FileSize.G);
        } else if (size > Constants.BYTE_UNIT_M && size < Constants.BYTE_UNIT_G){
            return sizeStr(size, FileSize.M);
        } else {
            return sizeStr(size, FileSize.K);
        }
    }


    public static boolean exists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static String kSize(Long size){
        return size / Constants.BYTE_UNIT_K + "K";
    }

    public static String mSize(Long size){
        return size / Constants.BYTE_UNIT_M + "M";
    }

    public static String gSize(Long size){
        return size / Constants.BYTE_UNIT_G + "G";
    }

    /**
     * 根据文件全路径获取文件名称
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath){
        if (CheckUtils.isNotEmpty(filePath)) {
            return filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return filePath;
    }

    /**
     * 获取文件后缀名
     * @param filePath
     * @param hasDot 是否包含点号
     * @return
     */
    public static String getSuffix(String filePath, boolean hasDot){
        if (CheckUtils.isNotEmpty(filePath)) {
            if (hasDot) {
                return filePath.substring(filePath.lastIndexOf("."));
            } else {
                return filePath.substring(filePath.lastIndexOf(".") + 1);
            }
        }
        return null;
    }


    /**
     * 上传图片 生成缩略图
     * @param file
     * @param width
     * @param height
     * @param uploadPath 缩略图实际存储路径
     * @param realUploadPath 原图实际路径
     * @return
     */
    public static String thumbnailUploadImage(File file, int width, int height, String uploadPath, String realUploadPath){
         String des = realUploadPath + uploadPath;
        try {

            Thumbnails.of(file).size(width, height).toFile(new File(des));
            //Thumbnails.of(file.getInputStream()).size(width, height).toFile(des);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return des;
    }


    /**
     * 获取文件夹下所有文件的绝对路径
     * @param directoryPath 文件夹路径
     * @return
     */
    public static List<String> getSubsectionAudioList(String directoryPath){
        List<String> list = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files.length == 0) {
                return null;
            } else {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        list.add(directoryPath + file.getName());
                    }
                }
                Collections.sort(list);
                return list;
            }
        } else {
            return null;
        }
    }


    public static void move(String sourcePath, String targetPath, String targetName){
        File source = new File(sourcePath);
        File targetDir = new File(targetPath);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        source.renameTo(new File(targetPath + "/" + targetName));
    }

    public static void main(String[] args) {
        //缩略图实际存储路径
       /* String dateFolderPath = CalendarUtils.getCurrentDate();
        String filePath = realUploadPath + dateFolderPath + "\\";
        System.out.println(filePath);

        File file = new File(filePath + "18011816525170399094.png");

        System.out.println("缩略图："+thumbnailUploadImage(file, thumbWidth, thumbHeight, filePath, filePath));*/

       String sourcePath = "Z:/course/17166/audio/418043/0.mp3";
       String targetPath = "Z:/course/17166/audio/";
       String fileName = cn.medcn.common.utils.StringUtils.nowStr() + ".mp3";
       move(sourcePath, targetPath, fileName);
    }
}
