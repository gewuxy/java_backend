package cn.medcn.official.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.official.model.OffSearch;

import java.util.List;

/**
 * by create HuangHuibin 2017/11/15
 */
public interface OffiSearchService extends BaseService<OffSearch>{
    List<OffSearch> findTopHost(Integer searchType);
}
