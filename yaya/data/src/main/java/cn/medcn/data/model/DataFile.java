package cn.medcn.data.model;

import cn.medcn.common.utils.FileUtils;
import cn.medcn.data.dto.FileCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/18.
 * 此类的作用：用来抽象数据中心所有的对象
 * 所有的数据中心的对象都是一个DataFile
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="t_data_file")
public class DataFile implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    private String categoryId;
    /**文件标题*/
    private String title;
    /**源文件路径*/
    private String filePath;
    /**文件大小*/
    private Float fileSize;
    /**作者*/
    private String author;
    /**文件摘要*/
    private String summary;
    /**html格式内容*/
    private String content;
    /**下载所需象数*/
    private Integer downLoadCost;
    /**修改日期*/
    private Date updateDate;
    /**根栏目ID 用来区分数据类型*/
    private String rootCategory;
    /**资料来源*/
    private String dataFrom;
    /**是否审核*/
    private Boolean authed;
    /**关键字（医师建议）*/
    private String keywords;
    /**图片（医师建议）*/
    private String img;
    /** 目录结构链，从跟目录到自身目录ID本身,用下划线分隔*/
    protected String historyId;

    private String htmlPath;

    @Transient
    private String fileSizeStr;



    public String getFileSizeStr(){
        if(fileSize != null){
            Long size = Long.valueOf(fileSize.intValue()*1024);
            return FileUtils.fileSize2String(size);
        }
        return null;
    }

    public enum DataType{
        MEET(0,"会议"),
        THOMSON(1,"汤森路透"),
        MEDICINE(2,"药品目录"),
        CLINICAL(3,"临床指南");

        private Integer type;
        private String name;

        public Integer getType(){
            return type;
        }

        public String getName(){
            return name;
        }

        DataType(Integer type,String name){
            this.type = type;
            this.name = name;
        }

    }

    public enum OpenType{
        BY_PDF(1,"pdf打开"),
        BY_DETAIL(2,"key，value打开"),
        BY_HTML(3,"html打开");

        private Integer type;

        private String desc;

        public Integer getType(){
            return type;
        }

        public String getDesc(){
            return desc;
        }

        OpenType(Integer type,String desc){
            this.type = type;
            this.desc = desc;
        }
    }


}
