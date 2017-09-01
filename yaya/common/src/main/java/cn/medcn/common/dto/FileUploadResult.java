package cn.medcn.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/6/2.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult implements Serializable {
    private String relativePath; //相对路径

    private String absolutePath ;//绝对路径

}
