package cn.medcn.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 公众号资料实体类
 * Created by LiuLP on 2017/4/21.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_material")
public class Material {

    @Id
    private String id;

    private  String materialName;

    private String materialType;

    private String materialUrl;

    private String materialDesc;

    //需要支付象数
    private Boolean needXs;

    //需要支付的象数
    private Integer xsCost;

    //时长 音视频资料需填写时长属性
    private Integer duration;

    private String infinityId;

    private Integer userId;

    private Date createTime;

    private Long fileSize;

    private String htmlUrl;



}
