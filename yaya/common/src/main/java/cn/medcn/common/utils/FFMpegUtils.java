package cn.medcn.common.utils;

import cn.medcn.common.supports.MediaInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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


    public static int duration(String videoPath) {
        List<String> commands = new ArrayList<>();
        commands.add(FFMPEG_COMMAND);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTime(m.group(1));
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getTime(String timeStr){
        int min=0;
        String strs[] = timeStr.split(":");
        if (strs[0].compareTo("0") > 0) {
            min+=Integer.valueOf(strs[0])*60*60;//秒
        }
        if(strs[1].compareTo("0")>0){
            min+=Integer.valueOf(strs[1])*60;
        }
        if(strs[2].compareTo("0")>0){
            min+=Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    /**
     * 视频截图
     * @param videoPath
     * @return
     */
    public static String printScreen(String videoPath){
        String jpgName = StringUtils.nowStr() + ".jpg";
        String baseDir = videoPath.substring(0, videoPath.lastIndexOf("/"));
        List<String> commands = new ArrayList<>();
        commands.add(FFMPEG_COMMAND);
        commands.add("-ss");
        commands.add("2");//这个参数是设置截取视频多少秒时的画面
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add("720x540"); //这个参数是设置截取图片的大小
        commands.add(baseDir +"/"+ jpgName);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            return jpgName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void main(String[] args) {
//        wavToMp3("D:/lixuan/test/0916.wav", "D:/lixuan/test/mp3");
        //System.out.println(duration("D:/lixuan/test/test.mp4"));
        //compress("D:/lixuan/test/2652.mp3", "D:/lixuan/test/mp3/");
        //cuttingAudio("D:/lixuan/test/韩济生.mp3","D:/lixuan/test/mp3/",new AudioPoint[]{new AudioPoint(0,30),new AudioPoint(30,80),new AudioPoint(80,110),new AudioPoint(110,200),new AudioPoint(200,280),new AudioPoint(280,350),new AudioPoint(350,830),new AudioPoint(830,2500)});
        //MediaInfo mediaInfo = parseMedia("D:/lixuan/test/2652.mp3");
        //System.out.println(mediaInfo);

        printScreen("D:/lixuan/test/test.mp4");
    }
}
