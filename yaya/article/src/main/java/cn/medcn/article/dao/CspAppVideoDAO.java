package cn.medcn.article.dao;

import cn.medcn.article.model.AppVideo;
import com.github.abel533.mapper.Mapper;

/**
 * Created by Liuchangling on 2017/10/30.
 */

public interface CspAppVideoDAO extends Mapper<AppVideo> {

    AppVideo findCspAppVideo();
}
