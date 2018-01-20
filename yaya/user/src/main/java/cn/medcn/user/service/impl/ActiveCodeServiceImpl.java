package cn.medcn.user.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.ActiveCodeDAO;
import cn.medcn.user.dao.ActiveStoreDAO;
import cn.medcn.user.model.ActiveCode;
import cn.medcn.user.model.ActiveStore;
import cn.medcn.user.service.ActiveCodeService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/4/24.
 */
@Service
public class ActiveCodeServiceImpl extends BaseServiceImpl<ActiveCode> implements ActiveCodeService {

    @Autowired
    private ActiveCodeDAO activeCodeDAO;

    @Autowired
    protected ActiveStoreDAO activeStoreDAO;

    @Override
    public Mapper<ActiveCode> getBaseMapper() {
        return activeCodeDAO;
    }

    @Override
    public int getActiveStore(Integer userId) {
        ActiveStore activeStore = activeStoreDAO.selectByPrimaryKey(userId);
        if (activeStore == null) {
            return 0;
        } else {
            return activeStore.getStore() == null ? 0 : activeStore.getStore().intValue();
        }
    }

    @Override
    public MyPage<ActiveCode> findActiveCodeList(Pageable pageable) {
        startPage(pageable, true);
        Page<ActiveCode> page = (Page)activeCodeDAO.findActiveCodeList(pageable.getParams());
        return MyPage.page2Mypage(page);
    }
}
