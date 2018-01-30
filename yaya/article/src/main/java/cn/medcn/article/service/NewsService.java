package cn.medcn.article.service;

import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.News;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import org.jdom.JDOMException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by lixuan on 2017/2/13.
 */
public interface NewsService extends BaseService<News> {

    //String NEWS_LIST_URL = "http://192.168.1.114:8082/txw!findNews";
    String NEWS_LIST_URL = "http://www.medcn.cn/txw!findNews";

    /**
     * 批量添加新闻
     *
     * @param type
     */
    void saveBatchNews(String type) throws JDOMException, IOException, ParseException;

    /**
     * 分页查询新闻列表
     * @param pageable
     * @return
     */
    MyPage<News> pageNews(Pageable pageable);

    /**
     * 启动更新新闻列表定时器
     */
    void runUpdateNewsTimer();

    /**
     * 更新新闻列表
     */
    void updateNews() throws JDOMException, IOException, ParseException;

    Integer getMaxId(String categoryId);

    MyPage<News> findAllNews(Pageable pageable);


    /**
     * 官网查询新闻列表 根据分类或者关键字查找
     */
    MyPage<News> findNewsList(Pageable pageable);

    /**
     * 查询新闻类别列表
     * @return
     */
    List<ArticleCategory> findCategoryList();


}
