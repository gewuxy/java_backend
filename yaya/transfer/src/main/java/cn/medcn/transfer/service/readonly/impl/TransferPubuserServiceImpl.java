package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PubUserSourceReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferPubuserService;
import cn.medcn.transfer.utils.DAOUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/15.
 */
public class TransferPubuserServiceImpl  extends ReadOnlyBaseServiceImpl<PubUserSourceReadOnly> implements TransferPubuserService {

    @Override
    public String getTable() {
        return "t_public_userinfo";
    }

    @Override
    public String getIdKey() {
        return "pub_user_id";
    }

    @Override
    public void tranferPubuser() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {

    }

    /**
     * 根据用户ID 转移公众号用户信息
     * @param userId
     * @return
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public PubUserSourceReadOnly transferUserInfo(Long userId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select pub_user_id,pub_info,attention_give_credits,pub_tupe,linkman,address from t_public_userinfo where pub_user_id=?";
        //Object params[] = {userId};
        PubUserSourceReadOnly condition = new PubUserSourceReadOnly();
        condition.setPub_user_id(userId.intValue());
        Object obj = DAOUtils.selectOne(getConnection(),condition,getTable());
        PubUserSourceReadOnly pubUserSourceReadOnly = (PubUserSourceReadOnly) obj;
        return pubUserSourceReadOnly;

    }


    @Override
    public PubUserSourceReadOnly findPubUserByUname(String userName) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        PubUserSourceReadOnly condition = new PubUserSourceReadOnly();
        condition.setPub_uname(userName);
        return findOne(condition);
    }

    @Override
    public Map<String, Integer> findAll() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select pub_user_id, pub_uname from t_public_userinfo";
        List<PubUserSourceReadOnly> list = (List<PubUserSourceReadOnly>) DAOUtils.selectList(getConnection(),sql, null, PubUserSourceReadOnly.class);
        Map<String, Integer> map = new HashMap<>();
        for(PubUserSourceReadOnly user:list){
            map.put(user.getPub_uname(), user.getPub_user_id());
        }
        return map;
    }
}
