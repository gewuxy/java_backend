package cn.medcn.csp.admin.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dao.FluxOrderDao;
import cn.medcn.csp.admin.dto.FluxOrderDTO;
import cn.medcn.csp.admin.service.FluxOrderService;
import cn.medcn.user.dao.FluxOrderDAO;
import cn.medcn.user.model.FluxOrder;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 9:41 2017/11/15
 */
@Service
public class FluxOrderServiceImpl extends BaseServiceImpl<FluxOrderDTO> implements FluxOrderService{

    @Autowired
    private FluxOrderDao fluxOrderDao;

    @Override
    public MyPage<FluxOrderDTO> findFluxOrderList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<FluxOrderDTO> myPage = MyPage.page2Mypage((Page) fluxOrderDao.findFluxOrderList(pageable.getParams()));
        return myPage;
    }

    @Override
    public List<FluxOrderDTO> selectOrderInfo(String id) {
        return fluxOrderDao.selectOrderInfo(id);
    }

    @Override
    public Mapper<FluxOrderDTO> getBaseMapper() {
        return fluxOrderDao;
    }
}
