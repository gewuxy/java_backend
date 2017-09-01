package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MeetSignReadOnly;
import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PositionReadOnly;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.model.writeable.MeetModule;
import cn.medcn.transfer.model.writeable.MeetPosition;
import cn.medcn.transfer.model.writeable.MeetSign;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferSignService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetModuleService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetSignService;
import cn.medcn.transfer.service.writeable.WriteAblePositionService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetModuleServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetSignServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAblePositionServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class TransferSignServiceImpl extends ReadOnlyBaseServiceImpl<PositionReadOnly> implements TransferSignService {
    @Override
    public String getTable() {
        return "t_meeting_position";
    }

    @Override
    public String getIdKey() {
        return "posId";
    }

    private WriteAbleMeetModuleService writeAbleMeetModuleService = new WriteAbleMeetModuleServiceImpl();

    private WriteAblePositionService writeAblePositionService = new WriteAblePositionServiceImpl();

    private WriteAbleMeetSignService writeAbleMeetSignService = new WriteAbleMeetSignServiceImpl();

    /**
     * 转换会议签到信息
     *
     * @param meetSource
     * @param meet
     */
    @Override
    public void transfer(MeetSourceReadOnly meetSource, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSource.getMeetingName()+"] 签到信息 ...");

        PositionReadOnly positionReadOnly = findPositionByMeetId(meetSource.getMeetingId());
        if(positionReadOnly == null){
            LogUtils.warm(this.getClass(),"未找到会议的签到信息 !!!!");
        }else{
            MeetModule module = new MeetModule();
            module.setFunctionId(MeetModule.ModuleFunction.SIGN.getFunId());
            module.setActive(true);
            module.setMainFlag(false);
            module.setMeetId(meet.getId());
            module.setModuleName(MeetModule.ModuleFunction.SIGN.getFunName());
            Integer moduleId = writeAbleMeetModuleService.addMeetmodule(module);
            MeetPosition position = MeetPosition.build(positionReadOnly);
            position.setMeetId(meet.getId());
            position.setModuleId(moduleId);
            writeAblePositionService.insert(position);

            LogUtils.debug(this.getClass(), "开始转换会议["+meetSource.getMeetingName()+"]签到记录 ...");
            List<MeetSignReadOnly> signList = findSignByPosId(position.getId());
            for(MeetSignReadOnly signReadOnly : signList){
                MeetSign meetSign = MeetSign.build(signReadOnly);
                meetSign.setMeetId(meet.getId());
                meetSign.setSignFlag(true);
                //防止重复数据

                writeAbleMeetSignService.insert(meetSign);
            }

            LogUtils.debug(this.getClass(), "转换会议["+meetSource.getMeetingName()+"]签到记录成功 !!!");
        }
    }

    private PositionReadOnly findPositionByMeetId(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        PositionReadOnly condition = new PositionReadOnly();
        condition.setMeetingId(meetId);
        PositionReadOnly positionReadOnly = findOne(condition);
        return positionReadOnly;
    }


    private List<MeetSignReadOnly> findSignByPosId(Integer posId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select max(sign_id) as sign_id, user_id, pos_id, max(position_lng) position_lng, max(position_lat) position_lat, max(create_time) create_time from t_meeting_sign  where pos_id = ? GROUP BY user_id";
        Object[] params = new Object[]{posId};
        List<MeetSignReadOnly> list = (List<MeetSignReadOnly>) DAOUtils.selectList(getConnection(), sql, params, MeetSignReadOnly.class);
        return list;
    }
}
