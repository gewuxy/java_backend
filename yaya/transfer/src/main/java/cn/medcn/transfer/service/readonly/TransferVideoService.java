package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.VideoReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/14.
 */
public interface TransferVideoService extends ReadOnlyBaseService<VideoReadOnly>{
    /**
     * 转换会议视频
     * @param source
     * @param meet
     */
    void transfer(MeetSourceReadOnly source, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
}
