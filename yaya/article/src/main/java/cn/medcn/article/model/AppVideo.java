package cn.medcn.article.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/10/30.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_app_video")
public class AppVideo implements Serializable {

    protected String id;
    // 视频url
    protected String videoUrl;
    // 创建时间
    protected Date createTime;
    // 视频版本号
    protected Integer version;

}
