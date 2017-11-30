package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.RecommendMeetDAO;
import cn.medcn.meet.model.Recommend;
import cn.medcn.meet.service.RecommendMeetService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Create in 15:08 2017/11/27
 */
@Service
public class RecommendMeetServiceImpl extends BaseServiceImpl<Recommend> implements RecommendMeetService{

    @Autowired
    private RecommendMeetDAO recommendMeetDAO;

    @Override
    public Mapper<Recommend> getBaseMapper() {
        return recommendMeetDAO;
    }

    @Override
    public MyPage<Recommend> recommendMeetList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Recommend> page = MyPage.page2Mypage((Page) recommendMeetDAO.recommendMeetList(pageable.getParams()));
        return page;
    }

}
