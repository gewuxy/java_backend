package cn.medcn.meet.dao;

import cn.medcn.meet.model.BackgroundMusic;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2018/2/1.
 */

public interface BackgroundMusicDAO extends Mapper<BackgroundMusic>{

    List<BackgroundMusic> findMusicPageList(Map<String, Object> param);

}
