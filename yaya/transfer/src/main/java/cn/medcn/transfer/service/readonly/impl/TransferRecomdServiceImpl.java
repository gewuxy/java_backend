package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MeetRecomdReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferRecomdService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public class TransferRecomdServiceImpl extends ReadOnlyBaseServiceImpl<MeetRecomdReadOnly> implements TransferRecomdService {
    @Override
    public String getTable() {
        return "t_recommend";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MeetRecomdReadOnly findMeetRecomd(Integer meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MeetRecomdReadOnly condition = new MeetRecomdReadOnly();
        condition.setReId(meetId);
        condition.setReType(1);//会议推荐
        MeetRecomdReadOnly recomd = findOne(condition);
        return recomd;
    }
}
