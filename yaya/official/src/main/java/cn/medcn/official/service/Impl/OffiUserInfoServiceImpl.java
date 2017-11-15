package cn.medcn.official.service.Impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.official.dao.OffUserInfoDao;
import cn.medcn.official.model.OffUserInfo;
import cn.medcn.official.service.OffiUserInfoService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by create HuangHuibin 2017/11/15
 */
@Service
public class OffiUserInfoServiceImpl extends BaseServiceImpl<OffUserInfo> implements OffiUserInfoService{

    @Autowired
    private OffUserInfoDao offUserInfoDao;

    @Override
    public Mapper<OffUserInfo> getBaseMapper() {
        return offUserInfoDao;
    }
}
