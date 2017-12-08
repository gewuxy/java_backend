package cn.medcn.article.dao;

import cn.medcn.article.model.HotSearch;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * by create HuangHuibin 2017/12/8
 */
public interface HotSearchDAO extends Mapper<HotSearch>{

    List<HotSearch> findTopHost(String categoryId);

}
