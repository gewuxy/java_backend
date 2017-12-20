package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 供Principal继承使用
 * by create HuangHuibin 2017/12/20
 */
@Data
@NoArgsConstructor
public class CspUserInfos {
    protected String id;

    protected String nickName;

    protected String mobile;

    protected String email;

    protected String token;

    protected String avatar;

    protected Boolean abroad;

    //是否新用户
    protected Boolean newUser;

    //套餐id
    protected Integer packageId;

    // 用户套餐
    protected CspPackage cspPackage;

    //购买成功消息
    protected String pkChangeMsg;
    //老用戶為true 新用戶為false
    protected Boolean state;
    //是否冻结 true 表示未冻结 false 表示已冻结
    protected Boolean active;

}
