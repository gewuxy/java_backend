package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/12/12.
 * 用户套餐变更历史记录
 */
@Data
@NoArgsConstructor
@Entity
@Table (name = "t_csp_user_package_history")
public class CspUserPackageHistory implements Serializable {
    @Id
    private String  id;
    // 用户id
    private String userId;
    // 变更前的套餐id
    private Integer beforePackageId;
    // 变更后的套餐id
    private Integer afterPackageId;
    // 变更时间
    private Date updateTime;
    // 变更方式 0:过期自动降级 1:升级购买 2:管理员修改 3：绑定YaYa数字平台
    private Integer updateType;

    public enum modifyType {
        EXPIRE_DOWNGRADE(), // 过期降级
        BUY_UPGRADE(),  // 升级购买
        ADMIN_MODIFY(), // 管理员修改
        BIND_YAYA(); // 绑定YaYa数字平台
    }
}
