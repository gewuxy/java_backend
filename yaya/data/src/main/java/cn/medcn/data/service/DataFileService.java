package cn.medcn.data.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.data.dto.DataFileDTO;
import cn.medcn.data.dto.FileCategoryDTO;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/18.
 */
public interface DataFileService extends BaseService<DataFile> {
    /**
     * 分页查询数据中心文件列表
     * @param pageable
     * @return
     */
    MyPage<DataFile> findByPage(Pageable pageable);

    /**
     * 根据根iD获取所有数据
     * @param rootId
     * @return
     */
    List<DataFile> findByRootId(String rootId);

    /**
     * 收藏的数据(汤森，药品目录，临床指南)
     * @param pageable
     * @return
     */
    MyPage<FileCategoryDTO> findFavorite(Pageable pageable);



    /**
     * 根据dataFileId查找文件的具体信息，以及是否被用户收藏
     * @param dataFileId
     * @return
     */
    List<DataFileDetail> selectDataFileDetail(String dataFileId);

    /**
     * 根据关键字搜索药品
     * @param pageable
     * @return
     */
    MyPage<FileCategoryDTO> selectMedicineListByKeyword(Pageable pageable);

    /**
     * 根据关键字搜索临床指南
     * @param pageable
     * @return
     */
    MyPage<FileCategoryDTO> selectClinicalListByKeyword(Pageable pageable);

    List<DataFileDetail> selectDetailByDataFileId(String dataFileId);

    /**
     * 查找文件
     * @param pageable
     * @return
     */
    MyPage<FileCategoryDTO> findFile(Pageable pageable);

    /**
     * 查找文件夹
     * @param pageable
     * @return
     */
    MyPage<FileCategoryDTO> findCategory(Pageable pageable);
}
