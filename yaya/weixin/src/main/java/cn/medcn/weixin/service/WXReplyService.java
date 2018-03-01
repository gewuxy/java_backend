package cn.medcn.weixin.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.weixin.model.PubWxReply;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 16:40 2018/2/28
 */
public interface WXReplyService extends BaseService<PubWxReply>{
    List<PubWxReply> selectByContent(Integer content);

    List<PubWxReply> selectAll();
}
