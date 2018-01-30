package cn.medcn.user.dao;

import cn.medcn.user.model.ActiveCode;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface ActiveCodeDAO extends Mapper<ActiveCode> {

    List<ActiveCode> findActiveCodeList(Map<String, Object> params);
}
