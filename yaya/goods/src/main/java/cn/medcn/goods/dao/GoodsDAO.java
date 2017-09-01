package cn.medcn.goods.dao;

import cn.medcn.goods.dto.GoodsDTO;
import cn.medcn.goods.model.Goods;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by LiuLP on 2017/4/23.
 */
public interface GoodsDAO extends Mapper<Goods> {


    List<GoodsDTO> findAll();

}
