package cn.medcn.transfer.service.writeable.impl;


import cn.medcn.transfer.model.writeable.Goods;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleGoodsService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Liuchangling on 2017/6/28.
 */
public class WriteAbleGoodsServiceImpl extends WriteAbleBaseServiceImpl<Goods> implements WriteAbleGoodsService {

    @Override
    public String getTable() {
        return "t_goods";
    }

    @Override
    public void addGoodsData(Goods goods) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        insertReturnId(goods);
    }
}
