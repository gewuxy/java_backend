package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/12/8.
 * csp套餐信息表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_package_info")
public class CspPackageInfo implements Serializable {
    @Id
    protected Integer id;
    // 套餐id
    protected Integer packageId;
    // 服务标识
    protected String iden;
    // 描述中文
    protected String descriptCn;
    // 描述
    protected String descriptTw;
    // 描述英文
    protected String descriptUs;
    // 状态
    protected Boolean state;


}
