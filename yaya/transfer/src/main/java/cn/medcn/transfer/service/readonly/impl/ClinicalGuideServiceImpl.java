package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.ClinicalGuide;
import cn.medcn.transfer.model.writeable.MedicineSms;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.ClinicalGuideService;
import cn.medcn.transfer.service.writeable.WriteAbleMedCategoryService;
import cn.medcn.transfer.service.writeable.WriteAbleMedicineSmsService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMedCategoryServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMedicineSmsServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.FileUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/8/21.
 */
public class ClinicalGuideServiceImpl extends ReadOnlyBaseServiceImpl<ClinicalGuide> implements ClinicalGuideService {


    @Override
    public String getIdKey() {
        return "mid";
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }

    protected int counter = 0;

    protected WriteAbleMedicineSmsService writeAbleMedsmsService = new WriteAbleMedicineSmsServiceImpl();

    protected WriteAbleMedCategoryService writeAbleMedCategoryService = new WriteAbleMedCategoryServiceImpl();

    protected List<ClinicalGuide> findListByCid(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClinicalGuide condition = new ClinicalGuide();
        condition.setCid(cid);
        List<ClinicalGuide> subList = findList(condition);
        return subList;
    }



    @Override
    public void transferByCid(Long cid, String categoryId, String rootId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {


        List<ClinicalGuide> subList = findListByCid(cid);
        if (subList != null && subList.size() > 0){
            for (ClinicalGuide clinicalGuide : subList){
                MedicineSms medicineSms = MedicineSms.build(clinicalGuide, categoryId);
                if (medicineSms != null){
                    Blob blob = findBlob(clinicalGuide.getMid());
                    //导出blob文件
                    String saveFileName = FileUtils.saveClinicalGuide(blob, ".pdf");
                    medicineSms.setFilePath(saveFileName);
                    medicineSms.setHistoryId(writeAbleMedCategoryService.getHistoryId(categoryId, rootId));
                    writeAbleMedsmsService.insert(medicineSms);
                }
                counter ++;
                LogUtils.debug(this.getClass(), "成功共保存了临床指南文件 = "+counter+"个 ！！！");
            }
        }
    }

    protected ClinicalGuide findById(Long cid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClinicalGuide condition = new ClinicalGuide();
        condition.setMid(cid);
        ClinicalGuide clinicalGuide = findOne(condition);
        return clinicalGuide;
    }

    protected Blob findBlob(Long mid){
        String sql = "select contents from `"+TABLE_NAME+"` where mid = ?";
        Object[] params = {mid};
        Blob blob = DAOUtils.getBlobData(getConnection(), sql, params);
        return blob;
    }
}
