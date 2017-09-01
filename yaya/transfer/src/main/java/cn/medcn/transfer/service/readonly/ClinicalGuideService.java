package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.ClinicalGuide;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/8/21.
 */
public interface ClinicalGuideService extends ReadOnlyBaseService<ClinicalGuide>{

    String TABLE_NAME = "t_lczn";


    void transferByCid(Long cid, String categoryId, String rootId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;
}
