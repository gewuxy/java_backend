package cn.medcn.data.dto;

import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/19.
 */
@Data
@NoArgsConstructor
public class FileCategoryDTO implements Serializable{

    //文件id或者文件夹id
    private String id;

    private Boolean leaf;

    //文件或文件夹名称
    private String title;

    //是否为文件，文件为true，文件夹为false
    private Boolean isFile;

    //作者或者生产商
    private String author;

    private String dataFrom;

    private Float fileSize;

    private Date updateDate;

    private String filePath;

    //文件打开方式，1代表用pdf打开，2代表请求文件详情接口打开，3代表用html打开
    private Integer openType;

    //html路径
    private String htmlPath;






}
