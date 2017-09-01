package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**资源收藏
 * Created by LiuLP on 2017/5/18/018.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_resource_favorite")
public class Favorite implements Serializable {

    @Id
    private Integer id;

    private Integer userId;

    private String resourceId;

    private Date storeTime;

    private Integer resourceType;


    public enum FavoriteType{
        meet,//会议
        thomson,//汤森路透
        med,//药品目录
        guide;//临床指南
    }
}
