package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.Medsms;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.MedsmsService;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.FileUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.util.List;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedsmsServiceImpl extends ReadOnlyBaseServiceImpl<Medsms> implements MedsmsService {
    @Override
    public String getIdKey() {
        return "M_id";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }


    @Override
    public List<Medsms> findSmsByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select  mcs.*  from t_medicinesms mcs " +
                "INNER JOIN t_medsms_medclass mmc ON mmc.mid = mcs.M_id " +
                "INNER JOIN medclass mc ON mc.cid = mmc.cid " +
                "WHERE mc.cid = ?";
        Object[] params = {cid};
        List<Medsms> medsmss = (List<Medsms>) DAOUtils.selectList(getConnection(), sql, params, Medsms.class);
        return medsmss;
    }

    @Override
    public void transferByCid(Long cid) {

    }


    protected void copyFile(Long id){
        Blob blob = getMedsmsBlob(id);
        //FileUtils.save
    }


    protected Blob getMedsmsBlob(Long id){
        String sql = "select m_word from t_medsms_document where m_id = ?";
        Object[] params = {id};
        return DAOUtils.getBlobData(getConnection(), sql, params);
    }
}
