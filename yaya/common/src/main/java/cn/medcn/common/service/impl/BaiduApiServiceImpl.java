package cn.medcn.common.service.impl;

import cn.medcn.common.service.BaiduApiService;
import cn.medcn.common.supports.baidu.SearchResultDTO;
import cn.medcn.common.supports.baidu.NearbySearchDTO;
import cn.medcn.common.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lixuan on 2017/7/26.
 */
@Service
public class BaiduApiServiceImpl implements BaiduApiService {

    @Override
    public SearchResultDTO search(NearbySearchDTO searchDTO) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("ak", APP_KEY);
        params.put("output",searchDTO.getOutput());
        params.put("query", searchDTO.getQuery());
        params.put("filter",searchDTO.getFilter());
        params.put("scope",searchDTO.getScope());//返回检索POI详细信息
        params.put("page_size",searchDTO.getPageSize()+"");
        params.put("page_num", searchDTO.getPageNo()+"");
        params.put("radius",searchDTO.getRadius());
        params.put("location", searchDTO.getLocation());
        String response = HttpUtils.get(SERACH_API, params);
        SearchResultDTO searchResultDTO = JSON.parseObject(response, SearchResultDTO.class);
        return searchResultDTO;
    }
}
