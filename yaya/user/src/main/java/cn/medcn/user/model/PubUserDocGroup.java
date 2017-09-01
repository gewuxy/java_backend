package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**公众号，医生，分组对应关系
 * Created by LiuLP on 2017/5/19/019.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_user_doctor_group")
public class PubUserDocGroup {

    @Id
    private Integer id;

    //分组id
    private Integer groupId;

    //医生id
    private Integer doctorId;

    //公众号id
    private Integer pubUserId;
}
