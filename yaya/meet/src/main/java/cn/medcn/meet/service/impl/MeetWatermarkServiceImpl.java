package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.MeetMaterialDAO;
import cn.medcn.meet.dao.MeetWatermarkDAO;
import cn.medcn.meet.model.MeetMaterial;
import cn.medcn.meet.model.MeetWatermark;
import cn.medcn.meet.service.MeetMaterialService;
import cn.medcn.meet.service.MeetWatermarkService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/6/9.
 */
@Service
public class MeetWatermarkServiceImpl extends BaseServiceImpl<MeetWatermark> implements MeetWatermarkService {

    @Autowired
    private MeetWatermarkDAO meetWatermarkDAO;

    @Override
    public Mapper<MeetWatermark> getBaseMapper() {
        return meetWatermarkDAO;
    }


    @Override
    public MeetWatermark findWatermarkByCourseId(Integer courseId) {
        MeetWatermark watermark = new MeetWatermark();
        watermark.setCourseId(courseId);
        return meetWatermarkDAO.selectOne(watermark);
    }
}
