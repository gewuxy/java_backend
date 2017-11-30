package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.model.Recommend;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Create in 15:07 2017/11/27
 */
public interface RecommendMeetService extends BaseService<Recommend>{
    MyPage<Recommend> recommendMeetList(Pageable pageable);
}
