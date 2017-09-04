package cn.medcn.meet.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.MeetMaterialDAO;
import cn.medcn.meet.model.MeetMaterial;
import cn.medcn.meet.service.MeetMaterialService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 复制会议资料文件
     * @param oldMeetId
     * @param newMeetId
     */
    public void copyMeetMaterial(String oldMeetId, String newMeetId) {
        MeetMaterial condition = new MeetMaterial();
        condition.setMeetId(oldMeetId);
        List<MeetMaterial> materials = meetMaterialDAO.select(condition);
        if (!CheckUtils.isEmpty(materials)) {
            for (MeetMaterial material : materials) {
                material.setMeetId(newMeetId);
                MeetMaterial newMaterial = material;
                newMaterial.setId(null);
                meetMaterialDAO.insert(newMaterial);
            }
        }
    }

}
