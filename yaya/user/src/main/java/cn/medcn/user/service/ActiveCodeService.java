package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.ActiveCode;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface ActiveCodeService extends BaseService<ActiveCode> {

    int getActiveStore(Integer userId);
}
