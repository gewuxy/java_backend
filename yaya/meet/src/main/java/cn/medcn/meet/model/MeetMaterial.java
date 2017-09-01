package cn.medcn.meet.model;

import cn.medcn.common.utils.FileUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/6/9.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_material")
public class MeetMaterial implements Serializable {

    @Id
    private Integer id;

    private String name;

    private String fileType;

    private Integer userId;

    private Date createTime;

    private Long fileSize;

    private String meetId;

    private String fileUrl;
    //用于前端呈现文件大小的字符串
    @Transient
    private String fileSizeStr;

    public String getFileSizeStr(){
        return FileUtils.fileSize2String(fileSize);
    }

    public static void main(String[] args) {
        MeetMaterial m = new MeetMaterial();
        m.setFileSize(10224L);
        System.out.println(m.getFileSizeStr());
    }
}
