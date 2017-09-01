package cn.medcn.jcms.controller.file;

import lombok.Data;

import java.text.DecimalFormat;

/**
 * Created by lixuan on 2017/3/13.
 */
@Data
public class FileUploadProgress {

    private long bytesRead;

    private long contentLength;

    private String progress;

    public String getProgress(){
        if(contentLength == 0){
            return "0.0%";
        }else{
            DecimalFormat format = new DecimalFormat( "#####0.0");
            return format.format(bytesRead*1.0*100/contentLength)+"%";
        }
    }
}
