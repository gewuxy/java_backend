package cn.medcn.csp.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 录音上传DTO
 * Created by lixuan on 2018/1/10.
 */
@Data
@NoArgsConstructor
public class RecordUploadDTO implements Serializable{

    protected Integer courseId;

    protected Integer detailId;

    protected Integer playType = 0;

    protected Integer pageNum = 0;

    protected Integer audioNum;

    //是否还有下一个音频，没有的话，就执行合并音频操作
    protected Boolean hasNext;
}
