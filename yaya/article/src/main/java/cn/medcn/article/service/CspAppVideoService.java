package cn.medcn.article.service;

import cn.medcn.article.model.AppVideo;
import cn.medcn.common.service.BaseService;

/**
 * Created by Liuchangling on 2017/10/30.
 */
public interface CspAppVideoService extends BaseService<AppVideo> {

    AppVideo findCspAppVideo();
}
