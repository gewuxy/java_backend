package cn.medcn.weixin.service.impl;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.weixin.dao.WXUserInfoDAO;
import cn.medcn.weixin.model.WXUserInfo;
import cn.medcn.weixin.service.WXUserInfoService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuan on 2017/7/24.
 */
@Service
public class WXUserInfoServiceImpl extends BaseServiceImpl<WXUserInfo> implements WXUserInfoService {

    @Autowired
    private WXUserInfoDAO wxUserInfoDAO;

    @Override
    public Mapper<WXUserInfo> getBaseMapper() {
        return wxUserInfoDAO;
    }


    @Override
    public void addWXUser(WXUserInfo wxUserInfo) throws SystemException {
        WXUserInfo userInfo = findWXUserInfo(wxUserInfo.getUnionid());
        if (userInfo != null){
            throw new SystemException("微信用户unionId="+userInfo.getUnionid()+"已经存在");
        }
        wxUserInfoDAO.insert(wxUserInfo);
    }

    @Override
    public WXUserInfo findWXUserInfo(String unionId) {
        WXUserInfo userInfo = wxUserInfoDAO.selectByPrimaryKey(unionId);
        return userInfo;
    }

    @Override
    public Integer checkBindStatus(String openid) {
        Integer userId = wxUserInfoDAO.checkBindStatus(openid);
        return userId;
    }

    @Override
    public void doUnSubscribe(String openid) {
        if (!CheckUtils.isEmpty(openid)){
            WXUserInfo condition = new WXUserInfo();
            condition.setOpenid(openid);
            WXUserInfo user = wxUserInfoDAO.selectOne(condition);
            if (user != null){
                user.setSubscribe(false);
            }
            wxUserInfoDAO.updateByPrimaryKey(user);
        }
    }

    @Override
    public WXUserInfo findWxUserInfoByAppUserId(Integer userId) {
        return wxUserInfoDAO.findWxUserInfoByAppUserId(userId);
    }
}
