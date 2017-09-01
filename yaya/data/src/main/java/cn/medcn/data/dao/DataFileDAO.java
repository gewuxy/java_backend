package cn.medcn.data.dao;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.data.dto.DataFileDTO;
import cn.medcn.data.dto.FileCategoryDTO;
import cn.medcn.data.model.DataFile;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/18.
 */
public interface DataFileDAO extends Mapper<DataFile>{

    List<DataFile> findDataFileByPage(Map<String, Object> params);

    List<FileCategoryDTO> findFavorite(Map<String, Object> params);

    List<FileCategoryDTO> selectClinicalListByKeyword(Map<String, Object> params);

    List<FileCategoryDTO> findFile(Map<String, Object> params);

    List<FileCategoryDTO> findCategory(Map<String, Object> params);

    List<FileCategoryDTO> selectMedicineListByKeyword(Map<String,Object> map);
}
