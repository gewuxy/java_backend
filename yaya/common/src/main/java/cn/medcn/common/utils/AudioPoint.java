package cn.medcn.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/3/16.
 */
@Data
@NoArgsConstructor
public class AudioPoint {
    /**音频开始点 格式 hh:mm:ss*/
    private String startPoint;
    /**音频开始点 格式 hh:mm:ss*/
    private String endPoint;

    private double start;

    private double end;

    public AudioPoint(double start, double end){
        this.start = start;
        this.end = end;
        this.startPoint = format(start);
        this.endPoint = format(end);
    }

    private static String format(double point){
        int intPoint = (int) Math.round(point);
        int h =intPoint/3600;
        int m = intPoint%3600/60;
        int s = intPoint%60;
        return (h>=10?h:"0"+h)+":"+(m>=10?m:"0"+m)+":"+(s>=10?s:"0"+s);
    }

    public static void main(String[] args) {
        double point = 120;
        System.out.println(format(point));
    }
}
