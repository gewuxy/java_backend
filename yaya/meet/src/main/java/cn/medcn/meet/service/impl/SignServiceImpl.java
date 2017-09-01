package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.MeetPositionDAO;
import cn.medcn.meet.dao.MeetSignDAO;
import cn.medcn.meet.dto.MeetSignHistoryDTO;
import cn.medcn.meet.model.MeetPosition;
import cn.medcn.meet.model.MeetSign;
import cn.medcn.meet.service.SignService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.SocketMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
@Service
public class SignServiceImpl extends BaseServiceImpl<MeetPosition> implements SignService {

    @Autowired
    private MeetSignDAO meetSignDAO;

    @Autowired
    private MeetPositionDAO meetPositionDAO;

    @Override
    public Mapper<MeetPosition> getBaseMapper() {
        return meetPositionDAO;
    }


    /**
     * 插入签到会议
     *
     * @param sign
     */
    @Override
    public void insertSign(MeetSign sign) {
        meetSignDAO.insert(sign);
    }

    /**
     * 查询签到信息
     *
     * @param condition
     */
    @Override
    public MeetSign selectOneSign(MeetSign condition) {
        return meetSignDAO.selectOne(condition);
    }

    /**
     * 查询签到地址信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public MeetPosition findPosition(String meetId, Integer moduleId) {
        MeetPosition condition = new MeetPosition();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        MeetPosition position = selectOne(condition);
        return position;
    }

    /**
     * 查询签到记录
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetSignHistoryDTO> findSignRecord(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<MeetSignHistoryDTO> page = (Page<MeetSignHistoryDTO>) meetSignDAO.findSignRecordByMeetId(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 查询签到数据导出excel
     * @param params
     * @return
     */
    @Override
    public List<MeetSignHistoryDTO> findSignListExcel(Map<String,Object> params){
        List<MeetSignHistoryDTO> signlist = meetSignDAO.findSignRecordByMeetId(params);
        return signlist;
    }

    /**
     * 变更签到数据
     * @param sign
     */
    @Override
    public void updateSign(MeetSign sign) {
        meetSignDAO.updateByPrimaryKey(sign);
    }


}
