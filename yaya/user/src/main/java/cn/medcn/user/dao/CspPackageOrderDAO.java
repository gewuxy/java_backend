package cn.medcn.user.dao;

import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.model.CspPackageOrder;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspPackageOrderDAO extends Mapper<CspPackageOrder>{
    Map<Integer,Float> selectAbroadAndHomeMoney();

    List<CspPackageOrderDTO> findOrderListByCurrencyType(int type);
}
