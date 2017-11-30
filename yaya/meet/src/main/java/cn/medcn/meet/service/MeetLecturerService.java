package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.model.Lecturer;

/**
 * @Author：jianliang
 * @Date: Create in 12:08 2017/11/28
 */
public interface MeetLecturerService extends BaseService<Lecturer>{
    /**
     * 根据会议ID查询
     * @param meetId
     * @return
     */
    Lecturer selectByMeetId(String meetId);
}
