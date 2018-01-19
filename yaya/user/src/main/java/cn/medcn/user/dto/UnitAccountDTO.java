package cn.medcn.user.dto;

import cn.medcn.user.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2018/1/16.
 */
@Data
@NoArgsConstructor
public class UnitAccountDTO extends AppUser {
    //激活码数量
    protected Integer activeStore = 0;
    //粉丝数量
    protected Integer fans;
}
