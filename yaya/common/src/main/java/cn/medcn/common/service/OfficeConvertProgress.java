package cn.medcn.common.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

/**
 * Created by lixuan on 2017/7/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeConvertProgress {

    private int total;

    private int finished;

    private String progress;

    private int courseId;

    public OfficeConvertProgress(int total, int finished, int courseId){
        this.total = total;
        this.finished = finished;
        this.courseId = courseId;
    }

    public String getProgress(){
        if(finished == 0){
            return "0.0%";
        }else{
            if(total == 0){
                return "0.0%";
            }
            DecimalFormat format = new DecimalFormat( "##0.0");
            return format.format(finished*1.0*100/total)+"%";
        }
    }
}
