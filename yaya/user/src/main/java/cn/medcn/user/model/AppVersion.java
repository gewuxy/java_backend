package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/6/8.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_version")
public class AppVersion implements Serializable{

    @Id
    private Integer id;
    /**版本*/
    private Integer version;
    /**版本信息的字符串描述*/
    private String versionStr;
    /**更新时间*/
    private Date updateTime;
    /**对应的手机类型*/
    private String driveTag;
    /**下载地址*/
    private String downLoadUrl;
    /**文件大小*/
    private Long fileSize;
    /**应用类型*/
    private String appType;
    /**版本更新说明*/
    private String details;
    /**是否强制更新*/
    private Boolean forced;

    public enum APP_TYPE{
        YAYA_YISHI("yaya_yishi"),
        YAYA_YAOSHI("yaya_yaoshi"),
        HLYY("hlyy"),
        CSPMeeting("csp");
        public String type;

        APP_TYPE(String type){
            this.type = type;
        }

    }


    public enum DRIVE_TAG{
        IOS("ios"),
        IPAD("ipad"),
        ANDROID("android");

        public String type;

        DRIVE_TAG(String type){
            this.type = type;
        }
    }

}
