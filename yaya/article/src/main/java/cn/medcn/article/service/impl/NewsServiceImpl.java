package cn.medcn.article.service.impl;

import cn.medcn.article.dao.ArticleDAO;
import cn.medcn.article.dao.NewsDAO;
import cn.medcn.article.jobs.UpdateNewsThread;
import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.News;
import cn.medcn.article.model.NewsReadOnly;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.UUIDUtil;
import com.alibaba.fastjson.JSON;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/2/13.
 */
@Service("newsService")
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {

    private static Log log = LogFactory.getLog(NewsServiceImpl.class);

    @Autowired
    private NewsDAO newsDAO;

    @Override
    public Mapper<News> getBaseMapper() {
        return newsDAO;
    }
    @Autowired
    private ArticleDAO articleDAO;

    /**
     * 批量添加新闻
     *
     * @param type
     * 这里的type 只能用NEWS_TYPE中的label来获取服务器的数据
     */
    @Override
    public void  saveBatchNews(String type) throws JDOMException, IOException, ParseException {
        //首先获取最大新闻id
        Integer maxNid = 0;

        // 安全用药
        if (type.equals("药品资讯")){
             maxNid = getMaxId(News.NEWS_CATEGORY.CATEGORY_AQYY.categoryId);
        }
        // 医学综合
        if (type.equals("医学综合")){
            maxNid = getMaxId(News.NEWS_CATEGORY.CATEGORY_ZYZX.categoryId);
        }
        // 医药动态
        if (type.equals("行业要闻")){
            maxNid = getMaxId(News.NEWS_CATEGORY.CATEGORY_YYDT.categoryId);
        }
        // 公司动态
        if (type.equals("公司动态")){
            maxNid = getMaxId(News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId);
        }

        //首先从yaya医生获取新闻列表
        Map<String, Object> params = Maps.newHashMap();
        params.put("type", type);
        params.put("nid", String.valueOf(maxNid));
        // 查询所有新闻
        String jsonString = HttpUtils.get(NEWS_LIST_URL, params);
        if(!StringUtils.isBlank(jsonString)){
            List<NewsReadOnly> newsList = JSON.parseArray(jsonString, NewsReadOnly.class);
            if (newsList != null && !newsList.isEmpty()){
                for (NewsReadOnly newsReadOnly : newsList){
                    Article article = new Article();
                    article.setId(UUIDUtil.getNowStringID());
                    article.setTitle(newsReadOnly.getSubject());
                    // 安全用药
                    if (newsReadOnly.getType().equals("药品资讯")){
                        article.setCategoryId(News.NEWS_CATEGORY.CATEGORY_AQYY.categoryId);
                    }
                    // 医学综合
                    if (newsReadOnly.getType().equals("医学综合")){
                        article.setCategoryId(News.NEWS_CATEGORY.CATEGORY_ZYZX.categoryId);
                    }
                    // 医药动态
                    if (newsReadOnly.getType().equals("行业要闻")){
                        article.setCategoryId(News.NEWS_CATEGORY.CATEGORY_YYDT.categoryId);
                    }
                    // 公司动态
                    if (newsReadOnly.getType().equals("公司动态")){
                        article.setCategoryId(News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String updates = newsReadOnly.getUpdatedate();
                    if (updates.endsWith("　")){
                        updates = updates.substring(0, updates.length() - 1).trim();
                    }
                    article.setCreateTime(sdf.parse(updates+" 09:00:00"));
                    article.setXfrom(newsReadOnly.getXwfrom());
                    article.setAuthor(newsReadOnly.getAuthor());
                    article.setContent(newsReadOnly.getContents());
                    article.setSummary(newsReadOnly.getXwzy());
                    article.setFilePath("");
                    article.setArticleImg(newsReadOnly.getWzimgurl());
                    article.setKeywords(newsReadOnly.getKeywords());
                    article.setAuthed(true);
                    article.setHits(0);
                    article.setOutLink("");
                    article.setArticleImgS(newsReadOnly.getImgurl());
                    article.setOldId(newsReadOnly.getNid().intValue());
                    Integer id = articleDAO.insert(article);
                }
                LogUtils.info(log, "成功更新"+newsList.size()+"条新闻");
            }
        }
    }

    /**
     * 分页查询新闻列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<News> pageNews(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),true);
        Page<News> page = (Page<News>) newsDAO.findByParams(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 启动更新新闻列表定时器
     */
    @Override
    public void runUpdateNewsTimer() {
        Runnable runnable = new UpdateNewsThread(this);
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,60, 3600, TimeUnit.SECONDS);
    }

    /**
     * 更新新闻列表
     */
    @Override
    public void updateNews() throws JDOMException, IOException, ParseException {
        LogUtils.info(log, "开始从服务器更新新闻列表数据...");
        long start = System.currentTimeMillis();
        saveBatchNews(News.NEWS_TYPE.YOZX.label);
        saveBatchNews(News.NEWS_TYPE.HYYW.label);
        saveBatchNews(News.NEWS_TYPE.YXZH.label);
        saveBatchNews(News.NEWS_TYPE.GSDT.label);

        LogUtils.info(log, "更新新闻操作耗时="+(System.currentTimeMillis()-start)+" ms");

    }

    @Override
    public Integer getMaxId(String categoryId) {
        return newsDAO.getMaxNid(categoryId);
    }


    @Override
    public MyPage<News> findAllNews(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),false);
        Page<News> page = (Page<News>) newsDAO.findAllNews(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public MyPage<News> findNewsList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        Page<News> page = (Page<News>) newsDAO.findNewsList(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    @Override
    public List<ArticleCategory> findCategoryList() {
        return newsDAO.findCategoryList();
    }

}
