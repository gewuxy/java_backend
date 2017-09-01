package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.GoodsReadOnly;
import cn.medcn.transfer.model.writeable.Goods;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferGoodsService;
import cn.medcn.transfer.service.writeable.WriteAbleGoodsService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleGoodsServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.FileUtils;


import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/28.
 */
public class TransferGoodsServiceImpl extends ReadOnlyBaseServiceImpl<GoodsReadOnly> implements TransferGoodsService {
    @Override
    public String getIdKey() {
        return "goods_id";
    }

    @Override
    public String getTable() {
        return "t_goods";
    }

    private WriteAbleGoodsService writeAbleGoodsService = new WriteAbleGoodsServiceImpl();

    /**
     * 查询所有的礼品数据
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<GoodsReadOnly> findGoodsList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        String sql = "select goods_id,goods_booknumber,goods_name,goods_credits,goods_price,goods_city,goods_provider,goods_stock_number," +
                "goods_author,goods_author_introduce,goods_keywords,goods_bkcatalog,goods_description,goods_type,goods_flag,goods_contenturl," +
                "goods_exchange_number,goods_return_credits,cid from t_goods";
        List<GoodsReadOnly> list = (List<GoodsReadOnly>)DAOUtils.selectList(getConnection(),sql,null,GoodsReadOnly.class);
        /*GoodsReadOnly condition = new GoodsReadOnly();
        List<GoodsReadOnly> list = (List<GoodsReadOnly>)DAOUtils.selectList(getConnection(),condition,getTable());*/
        return list;
    }

    // 获取礼品电子书内容
    @Override
    public Blob getGoodsBookUrl(Long goodsId) throws SQLException {
        String sql = "select goods_contents from t_goods where goods_id=?";
        Object params[] = {goodsId};
        Blob gdBlob = DAOUtils.getBlobData(getConnection(),sql,params);
        if(gdBlob!=null && gdBlob.length()!=0){
            return gdBlob;
        }else{
            return null;
        }
    }

    // 获取礼品图片内容
    @Override
    public Blob getGoodsImgUrl(Long goodsId) throws SQLException {
        String sql = "select goods_picture from t_goods where goods_id=?";
        Object params[] = {goodsId};
        Blob gdImgBlob = DAOUtils.getBlobData(getConnection(),sql,params);
        if(gdImgBlob!=null && gdImgBlob.length()!=0){
            return gdImgBlob;
        }else{
            return null;
        }
    }

    /**
     * 转换礼品数据
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws SQLException
     * @throws IOException
     */
    @Override
    public void transferGoods() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, IOException {
        List<GoodsReadOnly> gdlist = findGoodsList();
        for (GoodsReadOnly gd : gdlist){

            Blob gdImgBlob = getGoodsImgUrl(gd.getGoods_id());
            String gdImgUrl = "";
            String gdBookUrl = "";
            if (gdImgBlob!=null && gdImgBlob.length()!=0){
                gdImgUrl = FileUtils.saveGoodsImg(gdImgBlob,".jpg");
                gd.setGoodsImgUrl(gdImgUrl);
            }

            Blob gdBookBlob = getGoodsBookUrl(gd.getGoods_id());
            if (gdBookBlob !=null && gdBookBlob.length()!=0){
                gdBookUrl = FileUtils.saveGoodsBook(gdBookBlob,".pdf");
                gd.setGoodsBookUrl(gdBookUrl);
            }

            // 转换礼品数据
            Goods goods = Goods.build(gd);
            writeAbleGoodsService.addGoodsData(goods);
        }
    }

}
