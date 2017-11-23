package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.user.dao.PatientUserDAO;
import cn.medcn.user.model.Patient;
import cn.medcn.user.service.PatientUserService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/11/23.
 * 合理用药 用户
 */
@Service
public class PatientUserServiceImpl extends BaseServiceImpl<Patient> implements PatientUserService{
    @Autowired
    protected PatientUserDAO patientUserDAO;

    @Override
    public Mapper<Patient> getBaseMapper() {
        return patientUserDAO;
    }
}
