package cn.medcn.common.utils;

import cn.medcn.common.supports.MediaInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixuan on 2017/3/10.
 * 需要将ffmpeg设置到环境变量中
 *
 */
public class FFMpegUtils {

    private static final String FFMPEG_COMMAND = "ffmpeg";

    private static Log log = LogFactory.getLog(FFMpegUtils.class);

    private static final String MP3_OUTPUT_KBPS = "48k";




    /**
     * 将wav文件转转成MP3文件
     * 也可用来压缩mp3文件
     * @param wavPath
     * @param outPutPath
     */
    public static void wavToMp3(String wavPath, String outPutPath) {
        String targetFileName = wavPath.substring(wavPath.lastIndexOf("/") + 1);
        targetFileName = targetFileName.substring(0, targetFileName.lastIndexOf(".")) + ".mp3";
        String command = FFMPEG_COMMAND + " -i " + wavPath + " -acodec libmp3lame -ab " + MP3_OUTPUT_KBPS + " " + outPutPath + "/" + targetFileName;
        LogUtils.info(log, "wav to mp3 command=" + command);
        CommandUtils.CMD(command);
    }

    /**
     * 将音频文件按打点分割
     *
     * @param sourcePath
     * @param targetPath
     * @param points
     */
    public static void cuttingAudio(String sourcePath, String targetPath, AudioPoint[] points) {
        targetPath = targetPath.endsWith("/")?targetPath:targetPath+"/";
        String suffixName = sourcePath.substring(sourcePath.lastIndexOf("."));
        if (points != null) {
            String starttime = null;
            String endtime = null;
            String command = null;
            for (int index =0; index < points.length; index++) {
                starttime = points[index].getStartPoint();
                endtime = points[index].getEndPoint();
                command = FFMPEG_COMMAND+" -i "+sourcePath+" -ss "+starttime+" -to "+endtime+" -acodec copy "+targetPath+(index+1)+suffixName;
                CommandUtils.CMD(command);
            }
        }
    }


    public static void main(String[] args) {
        wavToMp3("D:/lixuan/test/0916.wav", "D:/lixuan/test/mp3");
        //compress("D:/lixuan/test/2652.mp3", "D:/lixuan/test/mp3/");
        //cuttingAudio("D:/lixuan/test/韩济生.mp3","D:/lixuan/test/mp3/",new AudioPoint[]{new AudioPoint(0,30),new AudioPoint(30,80),new AudioPoint(80,110),new AudioPoint(110,200),new AudioPoint(200,280),new AudioPoint(280,350),new AudioPoint(350,830),new AudioPoint(830,2500)});
        //MediaInfo mediaInfo = parseMedia("D:/lixuan/test/2652.mp3");
        //System.out.println(mediaInfo);
    }
}
