package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.MeetMaterialDAO;
import cn.medcn.meet.model.MeetMaterial;
import cn.medcn.meet.service.MeetMaterialService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/6/9.
 */
@Service
public class MeetMaterialServiceImpl extends BaseServiceImpl<MeetMaterial> implements MeetMaterialService {

    @Autowired
    private MeetMaterialDAO meetMaterialDAO;

    @Override
    public Mapper<MeetMaterial> getBaseMapper() {
        return meetMaterialDAO;
    }


}
