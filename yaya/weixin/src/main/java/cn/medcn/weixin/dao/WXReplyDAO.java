package cn.medcn.weixin.dao;

import cn.medcn.common.dao.BaseDAO;
import cn.medcn.weixin.model.PubWxReply;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Create in 16:44 2018/2/28
 */

public interface WXReplyDAO extends Mapper<PubWxReply>{
    List<PubWxReply> selectByContent(Integer content);

    List<PubWxReply> selectAll();
}
