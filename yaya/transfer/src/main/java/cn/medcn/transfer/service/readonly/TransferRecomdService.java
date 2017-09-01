package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetRecomdReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public interface TransferRecomdService extends ReadOnlyBaseService<MeetRecomdReadOnly> {


    MeetRecomdReadOnly findMeetRecomd(Integer meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
