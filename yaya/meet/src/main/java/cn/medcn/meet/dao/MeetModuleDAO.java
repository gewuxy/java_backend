package cn.medcn.meet.dao;

import cn.medcn.meet.dto.MeetModuleDTO;
import cn.medcn.meet.model.MeetModule;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lixuan on 2017/4/21.
 */
public interface MeetModuleDAO extends Mapper<MeetModule> {

    /**
     * 查询所有激活的模块
     * @param meetId
     * @return
     */
    List<MeetModule> findActiveModules(@Param("meetId") String meetId);

}
