package cn.medcn.search.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;

import java.util.List;

/**
 * Created by weilong on 2017/8/7.
 */
public interface DataFileSearchService extends BaseSearchService {
    /*
    * 输入dataFile对象以及与他对应的所有DataFileDetail  (dataFile.id==DataFileDetail.dataFileId)
    * 当然也可以直接用updateById，他会自己去数据库找相应的数据来更新
    * */
    public void update(DataFile dataFile, List<DataFileDetail> dataFileDetailList) throws SystemException;
}
