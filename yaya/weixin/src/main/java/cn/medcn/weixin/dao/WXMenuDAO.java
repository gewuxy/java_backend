package cn.medcn.weixin.dao;

import cn.medcn.weixin.model.WXMenu;
import com.github.abel533.mapper.Mapper;

import java.util.List;


/**
 * Created by lixuan on 2017/7/28.
 */
public interface WXMenuDAO extends Mapper<WXMenu> {

    List<WXMenu> findAll();
}
