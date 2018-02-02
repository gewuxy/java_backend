package cn.medcn.meet.dao;

import cn.medcn.meet.model.BackgroundImage;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2018/2/1.
 */
public interface BackgroundImageDAO extends Mapper<BackgroundImage> {

    List<BackgroundImage> findImagePageList(Map<String, Object> param);
}
