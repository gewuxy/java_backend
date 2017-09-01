package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.Goods;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/28.
 */
public interface WriteAbleGoodsService extends WriteAbleBaseService<Goods> {

    void addGoodsData(Goods goods) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

}
