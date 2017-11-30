package cn.medcn.meet.dao;

import cn.medcn.meet.model.Lecturer;
import com.github.abel533.mapper.Mapper;

/**
 * Created by lixuan on 2017/5/16.
 */
public interface MeetLecturerDAO extends Mapper<Lecturer>{
    Lecturer selectByMeetId(String meetId);
}
