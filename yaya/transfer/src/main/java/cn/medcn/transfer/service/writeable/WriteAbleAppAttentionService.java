package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.AppAttention;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public interface WriteAbleAppAttentionService extends WriteAbleBaseService<AppAttention> {

    /**
     * 添加公众号粉丝到粉丝关注表
     * @param appAttention
     */
    void addAppAttentionFromPubUserMember(AppAttention appAttention) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
