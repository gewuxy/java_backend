package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.ReportRegisterDAO;
import cn.medcn.user.model.ReportRegister;
import cn.medcn.user.service.ReportService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/12/26.
 */
@Service
public class ReportServiceImpl extends BaseServiceImpl<ReportRegister> implements ReportService {

    @Autowired
    protected ReportRegisterDAO reportRegisterDAO;

    @Override
    public Mapper<ReportRegister> getBaseMapper() {
        return reportRegisterDAO;
    }
}
