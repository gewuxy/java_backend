package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PptReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/5/22.
 */
public interface TransferMessageService extends ReadOnlyBaseService<PptReadOnly> {

    Blob getAuidoBlobData(Long messageId);


    PptReadOnly findMeetMessage(Long messageId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    /**
     * 根据会议ID获取所有的
     * @param meetId
     * @return
     */
    List<PptReadOnly> findByMeetId(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transfer(MeetSourceReadOnly meetSourceReadOnly, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;
}
