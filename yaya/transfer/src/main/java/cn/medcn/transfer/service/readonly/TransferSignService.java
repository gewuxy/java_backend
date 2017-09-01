package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PositionReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/14.
 */
public interface TransferSignService extends ReadOnlyBaseService<PositionReadOnly>{
    /**
     * 转换会议签到信息
     * @param meetSource
     * @param meet
     */
    void transfer(MeetSourceReadOnly meetSource, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
