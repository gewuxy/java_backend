package cn.medcn.article.service;

import cn.medcn.article.model.HotSearch;
import cn.medcn.common.service.BaseService;

import java.util.List;

/**
 * by create HuangHuibin 2017/12/8
 */
public interface HotSearchService extends BaseService<HotSearch>{

    List<HotSearch> findTopHost(String categoryId);
}
