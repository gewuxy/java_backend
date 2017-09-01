package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.GoodsReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/28.
 */
public interface TransferGoodsService extends ReadOnlyBaseService<GoodsReadOnly> {

    // 查询所有的礼品数据
    List<GoodsReadOnly> findGoodsList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    // 根据礼品ID 获取礼品二进制文件
    Blob getGoodsBookUrl(Long goodsId) throws SQLException;

    // 根据礼品Id 获取礼品图片二进制文件
    Blob getGoodsImgUrl(Long goodsId) throws SQLException;

    void transferGoods() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, IOException;
}
