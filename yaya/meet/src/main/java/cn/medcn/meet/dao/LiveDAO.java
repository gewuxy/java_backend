package cn.medcn.meet.dao;

import cn.medcn.meet.model.Live;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/9/26.
 */
public interface LiveDAO extends Mapper<Live> {


    List<Live> findTimeOutLives(@Param("now")Date now);
}
