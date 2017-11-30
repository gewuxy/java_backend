package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.MeetLecturerDAO;
import cn.medcn.meet.model.Lecturer;
import cn.medcn.meet.service.MeetLecturerService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：jianliang
 * @Date: Create in 12:08 2017/11/28
 */
@Service
public class MeetLecturerServiceImpl extends BaseServiceImpl<Lecturer> implements MeetLecturerService{

    @Autowired
    private MeetLecturerDAO meetLecturerDAO;

    @Override
    public Mapper<Lecturer> getBaseMapper() {
        return meetLecturerDAO;
    }

    @Override
    public Lecturer selectByMeetId(String meetId) {
        return meetLecturerDAO.selectByMeetId(meetId);
    }
}
