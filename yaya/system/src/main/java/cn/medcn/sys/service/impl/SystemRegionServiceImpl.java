package cn.medcn.sys.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.sys.dao.SystemRegionDAO;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/5/4.
 */
@Service
public class SystemRegionServiceImpl extends BaseServiceImpl<SystemRegion> implements SystemRegionService {

    @Autowired
    private SystemRegionDAO systemRegionDAO;

    @Override
    public Mapper<SystemRegion> getBaseMapper() {
        return systemRegionDAO;
    }


    /**
     * 批量添加行政区划
     *
     * @param datas
     */
    @Override
    public void executeBatchAdd(List<SystemRegion> datas) {
        for(SystemRegion region : datas){
            systemRegionDAO.insert(region);
        }
    }

    /**
     * 获取preid下的所有区划
     *
     * @param preid
     * @return
     */
    @Override
    @Cacheable(value=MIDDLE_TIME_CACHE, key = "'sys_region_'+#preid")
    public List<SystemRegion> findRegionByPreid(Integer preid) {
        SystemRegion condition = new SystemRegion();
        condition.setPreId(preid);
        List<SystemRegion> datas = systemRegionDAO.select(condition);
        return datas;
    }

    @Override
    public MyPage<SystemRegion> findByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<SystemRegion> page = MyPage.page2Mypage((Page) systemRegionDAO.findByPage(pageable.getParams()));
        return page;
    }

    /**
     * 根据父级名称获取子级行政区划
     * 适合于省份名称查城市
     *
     * @param preName
     * @return
     */
    @Override
    public List<SystemRegion> findRegionByPreName(String preName) {
        SystemRegion condition = new SystemRegion();
        condition.setName(preName);
        List<SystemRegion> list = systemRegionDAO.select(condition);
        if(list.size() == 0){
            return Lists.newArrayList();
        }
        SystemRegion parent = list.get(0);
        return findRegionByPreid(parent.getId());
    }

    @Override
    @Cacheable(value = MIDDLE_TIME_CACHE, key = "'all_regions'")
    public List<SystemRegion> findAll() {
        List<SystemRegion> list = systemRegionDAO.findAll();
        return list;
    }

    /**
     * 获取省市区相关联列表
     * @return
     */
    @Override
    public List<SystemRegion> getPCZRelationList() {
        List<SystemRegion> regions = findAll();
        List<SystemRegion> result = Lists.newArrayList();
        for(SystemRegion region : regions){
            if (region.getLevel() == 1){
                setSubList(region, regions);
                result.add(region);
            }
        }
        return result;
    }


    /**
     * 递归方法添加省对应的市区
     * @param region
     * @param regions
     */
    private void setSubList(SystemRegion region, List<SystemRegion> regions){
        for(SystemRegion sub:regions){
            if(region.getId().intValue() == sub.getPreId().intValue()){
                if(region.getLevel() < 3){
                    setSubList(sub, regions);
                }
                region.getDetails().add(sub);
            }
        }
    }
}
