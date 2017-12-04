package cn.medcn.article.service;

import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;

import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
public interface ArticleService extends BaseService<Article> {


    void deleteCategory(ArticleCategory category);

    void addCategory(ArticleCategory category);

    void updateCategory(ArticleCategory category);

    List<ArticleCategory> findCategoryByPreid(String preId);

    /**
     * 根据栏目Id获取文章列表
     * @param pageable
     * @return
     */
    MyPage<Article> findArticles(Pageable pageable);

    List<ArticleCategory> findAllCategory();

    /**
     * 根据查找category
     * @param id
     * @return
     */
    ArticleCategory selectCategory(String id);

    /**
     * 查询最上面两层的栏目
     * @return
     */
    List<ArticleCategory> findFirstLevelCategories();

    /**
     * 分页查询文章列表
     * @param pageable
     * @return
     */
    MyPage<Article> findArticleByPage(Pageable pageable);

    /**
     * 查询app广告页面信息
     * @param categoryId
     * @return
     */
    Article findAppAdvert(String categoryId);

    /**
     * 查询app Banner
     * @param pageable
     * @param categoryId
     * @return
     */
    MyPage<Article> findAppBanners(Pageable pageable, String categoryId);

    MyPage<Article> searchArticles(Pageable pageable);

}
