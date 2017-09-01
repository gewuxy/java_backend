package cn.medcn.data.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.data.dao.DataFavoriteDAO;
import cn.medcn.data.dao.DataFileDAO;
import cn.medcn.data.dao.DataFileDetailDAO;
import cn.medcn.data.dto.DataFileDTO;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.data.service.DataFavoriteService;
import cn.medcn.data.service.DataFileService;
import cn.medcn.user.model.Favorite;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/7/1.
 */
@Service
public class DataFavoriteServiceImpl extends BaseServiceImpl<Favorite> implements DataFavoriteService {

    @Autowired
    private DataFavoriteDAO dataFavoriteDAO;

    @Override
    public Mapper<Favorite> getBaseMapper() {
        return dataFavoriteDAO;
    }
}
