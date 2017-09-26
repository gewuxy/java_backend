package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**用户流量
 * Created by LiuLP on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_user_flux")
public class UserFlux implements Serializable{
    @Id
    protected Integer userId;

    //流量值
    protected Integer flux;



}
