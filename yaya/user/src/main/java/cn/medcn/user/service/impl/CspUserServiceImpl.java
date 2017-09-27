package cn.medcn.user.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dao.CspUserInfoDAO;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.service.CspUserService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuchangling on 2017/9/26.
 */
@Service
public class CspUserServiceImpl extends BaseServiceImpl<CspUserInfo> implements CspUserService {

    @Autowired
    protected CspUserInfoDAO cspUserInfoDAO;

    @Override
    public Mapper<CspUserInfo> getBaseMapper() {
        return cspUserInfoDAO;
    }


    @Override
    public CspUserInfo findBindUserByUniqueId(String uniqueId) {
        return null;
    }

    @Override
    public CspUserInfo findByLoginName(String username) {
        return null;
    }

    @Override
    public void register(CspUserInfo userInfo) throws SystemException{
        if (userInfo == null) {
            throw new SystemException("user info can not be null");
        }
        userInfo.setId(StringUtils.nowStr());
        String password = userInfo.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            userInfo.setPassword(MD5Utils.md5(password));
        }
        cspUserInfoDAO.insert(userInfo);
    }

    @Override
    public void sendCaptcha(String mobile, Integer type) {

    }

    /**
     * 根据 短信模板类型  获取不同的短信内容
     * @param mobile
     * @param type 短信模板类型 0=登录 1=绑定
     */
    protected void sendCaptchaByType(String mobile, Integer type) {
    }

    @Override
    public void checkCaptchaIsOrNotValid(String captcha, String mobile) {

    }
}
