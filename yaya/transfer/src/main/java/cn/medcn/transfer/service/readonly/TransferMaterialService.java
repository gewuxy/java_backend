package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetMaterialReadOnly;
import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/21.
 */
public interface TransferMaterialService extends ReadOnlyBaseService<MeetMaterialReadOnly> {

    List<MeetMaterialReadOnly> findMaterials(Long meetingId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transfer(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;
}
