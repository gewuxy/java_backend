package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/12/8.
 * csp用户会员套餐
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_user_package")
public class CspUserPackage implements Serializable {
    // 用户id
    @Id
    protected String userId;
    // 套餐id
    protected Integer packageId;
    // 套餐开始时间
    protected Date packageStart;
    // 套餐结束时间
    protected Date packageEnd;
    // 当前版本更新时间
    protected Date updateTime;
    // 套餐更新来源 0:过期自动降级 1:升级购买 2:管理员修改 3：绑定YaYa数字平台
    protected Integer sourceType;

    public enum modifyType {
        EXPIRE_DOWNGRADE(), // 过期降级
        BUY_UPGRADE(),  // 升级购买
        ADMIN_MODIFY(), // 管理员修改
        BIND_YAYA(); // 绑定YaYa数字平台
    }

}
