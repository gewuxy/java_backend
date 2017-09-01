package cn.medcn.weixin.dao;

import cn.medcn.weixin.model.WXUserInfo;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lixuan on 2017/7/24.
 */
public interface WXUserInfoDAO extends Mapper<WXUserInfo> {

    Integer checkBindStatus(@Param("openid") String openid);

    WXUserInfo findWxUserInfoByAppUserId(@Param("userId") Integer userId);
}
