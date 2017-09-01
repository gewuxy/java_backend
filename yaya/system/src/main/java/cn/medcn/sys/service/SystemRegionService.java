package cn.medcn.sys.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemRegion;

import java.util.List;

/**
 * Created by lixuan on 2017/5/4.
 */
public interface SystemRegionService extends BaseService<SystemRegion> {

    /**
     * 批量添加行政区划
     * @param datas
     */
    void executeBatchAdd(List<SystemRegion> datas);

    /**
     * 获取preid下的所有区划
     * @param preid
     * @return
     */
    List<SystemRegion> findRegionByPreid(Integer preid);

    MyPage<SystemRegion> findByPage(Pageable pageable);

    /**
     * 根据父级名称获取子级行政区划
     * @param preName
     * @return
     */
    List<SystemRegion> findRegionByPreName(String preName);

    List<SystemRegion> findAll();

}
