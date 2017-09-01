package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.MeetSignHistoryDTO;
import cn.medcn.meet.model.MeetPosition;
import cn.medcn.meet.model.MeetSign;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface SignService extends BaseService<MeetPosition> {

    /**
     * 插入签到会议
     * @param sign
     */
    void insertSign(MeetSign sign);

    /**
     * 查询签到信息
     * @param condition
     */
    MeetSign selectOneSign(MeetSign condition);

    /**
     * 查询签到地址信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetPosition findPosition(String meetId, Integer moduleId);

    /**
     * 查询签到数据 分页
     * @param pageable
     * @return
     */
    MyPage<MeetSignHistoryDTO> findSignRecord(Pageable pageable);

    /**
     * 查询签到数据导出excel
     * @param params
     * @return
     */
    List<MeetSignHistoryDTO> findSignListExcel(Map<String,Object> params);

    /**
     * 变更签到数据
     * @param sign
     */
    void updateSign(MeetSign sign);
}
