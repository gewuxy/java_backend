package cn.medcn.user.dao;

import cn.medcn.user.model.BindInfo;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/4/20.
 */
public interface BindInfoDAO extends Mapper<BindInfo>{

    List<BindInfo> findBindUserList(@Param("userId") String userId);

}
