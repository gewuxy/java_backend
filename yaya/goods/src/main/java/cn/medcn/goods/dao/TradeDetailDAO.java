package cn.medcn.goods.dao;

import cn.medcn.goods.dto.TradeDetailDTO;
import cn.medcn.goods.model.TradeDetail;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface TradeDetailDAO extends Mapper<TradeDetail>{
    List<TradeDetailDTO> findTradeInfo(Map<String, Object> params);
}
