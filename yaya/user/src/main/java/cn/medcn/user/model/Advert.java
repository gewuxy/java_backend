package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**app广告实体类
 * Created by LiuLP on 2017/4/20.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_advert")
public class Advert {
    @Id
    private Integer id;
    //广告页内容
    private String content;

    //是否使用
    private Boolean active;

    //创建时间
    private Date createTime;

    //
    private Integer pubUserId;

    //0表示系统的广告图 1表示公众用户的广告图
    private Integer type;

    //0表示yaya 1表示客户端
    private Integer  appId;

    private String pageUrl;


}
