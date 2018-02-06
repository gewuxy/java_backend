package cn.medcn.common.service;

import cn.medcn.common.supports.baidu.SearchResultDTO;
import cn.medcn.common.supports.baidu.NearbySearchDTO;

/**
 * Created by lixuan on 2017/7/26.
 */
public interface BaiduApiService {

    String APP_KEY = "6xGe03bqnLtFg6o9sXHcDDTU4OOmZ4Ti";
    //String APP_KEY = "XlgUFkD2Gir0u83w725EiRkOK4FX3OQj";

    String SERACH_API = "http://api.map.baidu.com/place/v2/search";

    SearchResultDTO search(NearbySearchDTO searchDTO);
}
