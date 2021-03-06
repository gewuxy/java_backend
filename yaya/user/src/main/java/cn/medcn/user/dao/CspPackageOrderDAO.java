package cn.medcn.user.dao;

import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.dto.CspOrderPlatFromDTO;
import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.weixin.dto.OAuthDTO;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspPackageOrderDAO extends Mapper<CspPackageOrder>{
    List<CspPackageOrder> selectAbroadAndHomeMoney();

    List<CspPackageOrderDTO> findOrderListByCurrencyType(Map<String,Object> map);

    Integer findOrderSuccessSum(@Param("type") Integer type,@Param("startTime") String startTime, @Param("endTime")String endTime);
    List<CspPackageOrderDTO> findOrderListByCurrencyType(int type);

    List<Map<String,Object>> totalMoney();

    List<Map<String,Object>> orderCapitalStati(@Param("grain")Integer grain,@Param("abroad")Integer abroad,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    List<Map<String,Object>> getCapitalByDay(Map<String,Object> map);

    Double selectNewMoney(@Param("currencyType") Integer currencyType);

    CspOrderPlatFromDTO getTotalCapital(Map<String,Object> map);

    CspOrderPlatFromDTO getTotalCapital(@Param("grain")Integer grain, @Param("abroad")Integer abroad, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    List<Map<String,Object>> transfStats(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
