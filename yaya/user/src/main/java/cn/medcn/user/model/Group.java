package cn.medcn.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by LiuLP on 2017/5/16/016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_group")
public class Group {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    //公众号id
    private Integer pubUserId;

    //分组的组名
    private String groupName;

    //成员数量
    @Transient
    private Integer memberCount;

    //分组信息备注
    private String remark;

}
