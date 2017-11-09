package cn.medcn.user.dao;

import cn.medcn.user.model.UserFlux;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by LiuLP on 2017/4/20.
 */
public interface UserFluxDAO extends Mapper<UserFlux>{

    /**
     * 根据演讲稿ID获取用户流量
     * @param courseId
     * @return
     */
    UserFlux findByCourseId(@Param("courseId") Integer courseId);
}
