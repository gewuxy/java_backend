package cn.medcn.user.dao;

import cn.medcn.user.model.CspUserPackage;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by Liuchangling on 2017/12/8.
 */

public interface CspUserPackageDAO extends Mapper<CspUserPackage>{

    List<CspUserPackage> findUserPackages();

    int selectPremiumEdition();

    int selectProfessionalEdition();

    int selectStandardEdition();

    int selectStandardEditionByUs();

    int selectPremiumEditionByUs();

    int selectProfessionalEditionByUs();
}
