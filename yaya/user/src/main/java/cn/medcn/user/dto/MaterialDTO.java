package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by LiuLP on 2017/5/8
 */
@Data
@NoArgsConstructor
public class MaterialDTO {

    private String id;

    private String materialName;

    private String materialType;

    private String materialUrl;

    private Date createTime;

    //文件大小
    private Long fileSize;

    // html路径
    private String htmlUrl;







}
