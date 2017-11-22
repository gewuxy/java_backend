package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.Article;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleArticleService;

/**
 * Created by Liuchangling on 2017/11/17.
 */

public class WriteAbleArticleServiceImpl extends WriteAbleBaseServiceImpl<Article> implements WriteAbleArticleService {


    @Override
    public String getTable() {
        return "t_article";
    }
}
