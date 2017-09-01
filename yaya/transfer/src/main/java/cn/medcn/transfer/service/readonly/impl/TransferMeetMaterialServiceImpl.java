package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MeetMaterialReadOnly;
import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.model.writeable.MeetMaterial;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferMaterialService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetMaterialService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetMaterialServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.FileUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/21.
 */
public class TransferMeetMaterialServiceImpl extends ReadOnlyBaseServiceImpl<MeetMaterialReadOnly> implements TransferMaterialService {
    @Override
    public String getTable() {
        return "t_meeting_material";
    }

    @Override
    public String getIdKey() {
        return "materialId";
    }

    private WriteAbleMeetMaterialService writeAbleMeetMaterialService = new WriteAbleMeetMaterialServiceImpl();

    @Override
    public List<MeetMaterialReadOnly> findMaterials(Long meetingId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MeetMaterialReadOnly condition = new MeetMaterialReadOnly();
        condition.setMeetingId(meetingId);
        List<MeetMaterialReadOnly> list = findList(condition);
        return list;
    }

    @Override
    public void transfer(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        LogUtils.debug(this.getClass(), "开始转换会议资料 ...");
        List<MeetMaterialReadOnly> list = findMaterials(meetSourceReadOnly.getMeetingId());
        if(list == null || list.size() == 0){
            LogUtils.warm(this.getClass(), "会议["+meetSourceReadOnly.getMeetingName()+"]没有会议资料 !!!");
        }else{
            for(MeetMaterialReadOnly materialReadOnly : list){
                Blob data = getMaterialBlob(materialReadOnly.getMaterialId());
                String fileUrl = FileUtils.saveMaterial(data, "."+materialReadOnly.getMaterialType());
                LogUtils.debug(this.getClass(), "转换会议资料["+materialReadOnly.getMaterialName()+"] 到文件系统成功 !!!");
                writeAbleMeetMaterialService.addMeetMaterial(MeetMaterial.build(materialReadOnly, meet, data.length(), fileUrl));
                LogUtils.debug(this.getClass(), "转转会议资料["+materialReadOnly.getMaterialName()+"] 到数据库成功 !!!");
            }
        }
        LogUtils.debug(this.getClass(), "转换会议["+meetSourceReadOnly.getMeetingName()+"] 资料成功 !!!");
    }


    private Blob getMaterialBlob(Long materialId){
        String sql = "select material_content from `"+this.getTable()+"` where material_id = ?";
        Object[] params = new Object[]{materialId};
        return DAOUtils.getBlobData(this.getConnection(), sql, params);
    }
}
