package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.VideoHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleVideoHistoryService;

/**
 * Created by lixuan on 2017/6/22.
 */
public class WriteAbleVideoHistoryServiceImpl extends WriteAbleBaseServiceImpl<VideoHistory> implements WriteAbleVideoHistoryService {
    @Override
    public String getTable() {
        return "t_video_history";
    }
}
