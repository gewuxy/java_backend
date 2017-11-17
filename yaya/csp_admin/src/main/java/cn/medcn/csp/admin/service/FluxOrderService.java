package cn.medcn.csp.admin.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.csp.admin.dto.FluxOrderDTO;
import cn.medcn.user.model.FluxOrder;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 9:38 2017/11/15
 */
public interface FluxOrderService extends BaseService<FluxOrderDTO> {
    MyPage<FluxOrderDTO> findFluxOrderList(Pageable pageable);

    List<FluxOrderDTO> selectOrderInfo(String id);
}
