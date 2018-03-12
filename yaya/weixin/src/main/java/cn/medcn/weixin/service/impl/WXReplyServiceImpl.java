package cn.medcn.weixin.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.weixin.dao.WXReplyDAO;
import cn.medcn.weixin.model.PubWxAnswer;
import cn.medcn.weixin.model.PubWxReply;
import cn.medcn.weixin.service.WXReplyService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 16:42 2018/2/28
 */
@Service
public class WXReplyServiceImpl extends BaseServiceImpl<PubWxReply> implements WXReplyService{

    @Autowired
    private WXReplyDAO wxReplyDAO;

    @Override
    public Mapper<PubWxReply> getBaseMapper() {
        return wxReplyDAO;
    }

    @Override
    public List<PubWxReply> selectByContent(Integer content) {
        return wxReplyDAO.selectByContent(content);
    }

    @Override
    @Cacheable(value = DEFAULT_CACHE,key = "'menu_auto_reply'")
    public List<PubWxReply> selectAll() {
        return wxReplyDAO.selectAll();
    }
}
