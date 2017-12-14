package cn.medcn.user.dao;

import cn.medcn.user.model.CspPackage;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */
public interface CspPackageDAO extends Mapper<CspPackage> {

    CspPackage findUserPackageById(@Param("userId") String userId);

    List<CspPackage> findAllPackage();
}
