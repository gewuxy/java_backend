package cn.medcn.article.service.impl;

import cn.medcn.article.dao.ArticleCategoryDAO;
import cn.medcn.article.dao.ArticleDAO;
import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Override
    public Mapper<Article> getBaseMapper() {
        return articleDAO;
    }


    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'article_category_'+#category.preId")
    public void deleteCategory(ArticleCategory category) {
        articleCategoryDAO.deleteByPrimaryKey(category.getId());
    }

    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'article_category_'+#category.preId")
    public void addCategory(ArticleCategory category) {
        articleCategoryDAO.insert(category);
    }

    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'article_category_'+#category.preId")
    public void updateCategory(ArticleCategory category) {
        articleCategoryDAO.updateByPrimaryKeySelective(category);
    }

    @Override
    @Cacheable(value = DEFAULT_CACHE, key="'article_category_'+#preId")
    public List<ArticleCategory> findCategoryByPreid(String preId) {
        return articleCategoryDAO.findCategoryByPreid(preId);
    }

    /**
     * 根据栏目Id获取文章列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<Article> findArticles(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Article> page = MyPage.page2Mypage((Page) articleDAO.findArticles(pageable.getParams()));
        return page;
    }

    @Override
    public MyPage<Article> searchArticles(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Article> page = MyPage.page2Mypage((Page) articleDAO.searchArticles(pageable.getParams()));
        return page;
    }

    @Override
    public List<ArticleCategory> findAllCategory() {
        return articleCategoryDAO.findAllCategory();
    }

    /**
     * 根据查找category
     *
     * @param id
     * @return
     */
    @Override
    public ArticleCategory selectCategory(String id) {
        return articleCategoryDAO.selectByPrimaryKey(id);
    }

    /**
     * 查询最上面两层的栏目
     *
     * @return
     */
    @Override
    public List<ArticleCategory> findFirstLevelCategories() {
        ArticleCategory condition = new ArticleCategory();
        condition.setPreId("0");
        List<ArticleCategory> list = articleCategoryDAO.select(condition);
        return list;
    }

    /**
     * 分页查询文章列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<Article> findArticleByPage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Article> page = MyPage.page2Mypage((Page) articleDAO.findArticles(pageable.getParams()));
        return page;
    }

    /**
     * 查询app广告页面信息
     *
     * @param categoryId
     * @return
     */
    @Override
    public Article findAppAdvert(String categoryId) {
        return articleDAO.findAdvert(categoryId);
    }

    /**
     * 查询app Banner
     *
     * @param pageable
     * @param categoryId
     * @return
     */
    @Override
    public MyPage<Article> findAppBanners(Pageable pageable, String categoryId) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Article> page = MyPage.page2Mypage((Page) articleDAO.findBanners(categoryId));
        return page;
    }
}
