package cn.medcn.meet.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.meet.model.MeetMaterial;

/**
 * Created by lixuan on 2017/6/9.
 */
public interface MeetMaterialService extends BaseService<MeetMaterial> {

    /**
     * 复制会议资料文件
     * @param oldMeetId
     * @param newMeetId
     * @return
     */
    void copyMeetMaterial(String oldMeetId, String newMeetId);
}
