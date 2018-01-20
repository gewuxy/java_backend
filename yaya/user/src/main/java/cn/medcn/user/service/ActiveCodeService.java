package cn.medcn.user.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.ActiveCode;

import java.util.List;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface ActiveCodeService extends BaseService<ActiveCode> {

    int getActiveStore(Integer userId);

    MyPage<ActiveCode> findActiveCodeList(Pageable pageable);
}
