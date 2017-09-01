package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.ClinicalGuideCategory;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/8/22.
 */
public interface ClinicalGuideCategoryService extends ReadOnlyBaseService<ClinicalGuideCategory> {


    List<ClinicalGuideCategory> findRootCategoryList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<ClinicalGuideCategory> findByPid(String pid) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transferByCid(Long cid, String pid, Integer sort) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void transfer() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException;
}
