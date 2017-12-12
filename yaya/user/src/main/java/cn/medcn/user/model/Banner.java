package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**banner广告实体类
 * Created by LiuLP on 2017/4/20.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_banner")
public class Banner {
    @Id
    private String id;

    private String title;

    //图片地址
    private String imageUrl;

    //是否是有效状态 0 关闭 1开启
    private Boolean active;

    //banner排序 权重
    private Integer weight;

    //创建时间
    private Date createTime;

    //0表示系统banner 1表示用户banner
    private Integer type;

    //所属公众号ID
    private Integer pubUserId;

    //0表示yaya 1表示合理用药
    private Integer appId;

    //内容
    private String content;

}
