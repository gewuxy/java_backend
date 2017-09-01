package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.AppLog;

/**
 * Created by lixuan on 2017/5/2.
 */
public interface LogService extends BaseService<AppLog> {

    String LOG_QUEUE_NAME = "app_log_queue";

    void pushToQueue(AppLog log);

    AppLog bpopFromQueue();
}
