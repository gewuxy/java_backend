package cn.medcn.user.dao;

import cn.medcn.user.model.CspPackageInfo;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageInfoDAO extends Mapper<CspPackageInfo> {
    List<CspPackageInfo> selectByPackageId(Integer packageId);
}
