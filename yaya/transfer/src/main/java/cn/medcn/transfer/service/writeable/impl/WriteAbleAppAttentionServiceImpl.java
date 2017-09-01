package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.AppAttention;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleAppAttentionService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class WriteAbleAppAttentionServiceImpl extends WriteAbleBaseServiceImpl<AppAttention> implements WriteAbleAppAttentionService {
    @Override
    public String getTable() {
        return "t_app_attention";
    }

    /**
     * 添加公众号粉丝数据
     * @param appAttention
     */
    @Override
    public void addAppAttentionFromPubUserMember(AppAttention appAttention) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insert(appAttention);
    }
}
