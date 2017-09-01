package cn.medcn.data.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.data.dao.DataFileDAO;
import cn.medcn.data.dao.DataFileDetailDAO;
import cn.medcn.data.dto.DataFileDTO;
import cn.medcn.data.dto.FileCategoryDTO;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.data.service.DataFileService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/18.
 */
@Service
public class DataFileServiceImpl extends BaseServiceImpl<DataFile> implements DataFileService {

    @Autowired
    private DataFileDAO dataFileDAO;

    @Autowired
    private DataFileDetailDAO dataFileDetailDAO;


    @Override
    public Mapper<DataFile> getBaseMapper() {
        return dataFileDAO;
    }

    /**
     * 分页查询数据中心文件列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<DataFile> findByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<DataFile> page = MyPage.page2Mypage((Page) dataFileDAO.findDataFileByPage(pageable.getParams()));
        return page;
    }

    @Override
    public int deleteByPrimaryKey(Object t) {
        String id = (String) t;
        DataFileDetail condition = new DataFileDetail();
        condition.setDataFileId(id);
        dataFileDetailDAO.delete(condition);
        return super.deleteByPrimaryKey(t);
    }

    /**
     * 根据根iD获取所有数据
     *
     * @param rootId
     * @return
     */
    @Override
    public List<DataFile> findByRootId(String rootId) {
        DataFile condition = new DataFile();
        condition.setRootCategory(rootId);
        List<DataFile> list = dataFileDAO.select(condition);
        return list;
    }


    /**
     * 收藏的数据(汤森，药品目录，临床指南)
     * @param pageable
     * @return
     */
    @Override
    public MyPage<FileCategoryDTO> findFavorite(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<FileCategoryDTO> page = (Page)dataFileDAO.findFavorite(pageable.getParams());
        return MyPage.page2Mypage(page);
    }



    /**
     * 根据dataFileId查找文件的具体信息，以及是否被用户收藏
     * @param dataFileId
     * @return
     */
    @Override
    public List<DataFileDetail> selectDataFileDetail(String dataFileId) {
        DataFileDetail detail = new DataFileDetail();
        detail.setDataFileId(dataFileId);
        return dataFileDetailDAO.select(detail);
    }

    /**
     * 根据关键字搜索药品
     * @param pageable
     * @return
     */
    @Override
    public MyPage<FileCategoryDTO> selectMedicineListByKeyword(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<FileCategoryDTO> page = (Page)dataFileDAO.selectMedicineListByKeyword(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 根据关键字搜索临床指南
     * @param pageable
     * @return
     */
    @Override
    public MyPage<FileCategoryDTO> selectClinicalListByKeyword(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<FileCategoryDTO> page = (Page)dataFileDAO.selectClinicalListByKeyword(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<DataFileDetail> selectDetailByDataFileId(String dataFileId) {
        DataFileDetail dataFileDetail=new DataFileDetail();
        dataFileDetail.setDataFileId(dataFileId);
        return dataFileDetailDAO.select(dataFileDetail);
    }

    /**
     * 查找文件
     * @param pageable
     * @return
     */
    @Override
    public MyPage<FileCategoryDTO> findFile(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<FileCategoryDTO> page = (Page)dataFileDAO.findFile(pageable.getParams());
        return MyPage.page2Mypage(page);
    }


    /**
     * 查找文件夹
     * @param pageable
     * @return
     */
    @Override
    public MyPage<FileCategoryDTO> findCategory(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<FileCategoryDTO> page = (Page)dataFileDAO.findCategory(pageable.getParams());
        return MyPage.page2Mypage(page);
    }
}
