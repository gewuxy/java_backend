package cn.medcn.search.service.impl;

import cn.medcn.article.model.Article;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.search.dto.ArticleSearchDTO;
import cn.medcn.search.service.ArticleSearchService;
import cn.medcn.search.util.SearchUtil;
import cn.medcn.search.util.SolrReloadThread;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by weilong on 2017/8/7.
 */
@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {
    @Autowired
    private SearchUtil searchUtil;
    @Autowired
    private ArticleService articleService;


    @Value("${solr.article.SOLR_CORE}")
    private String ARTICLE_CORE;
    @Value("${app.file.upload.base}")
    private String uploadPath;

    //保存重新更新检索库的进度信息
    private static String reloadSchedule = "";
    //上面的进度信息等于以下值时停止更新，即：STOP_ARTICLE_RELOAD.equals(reloadSchedule)
    private static final String STOP_ARTICLE_RELOAD = "STOP_ARTICLE_RELOAD";

    @Override
    public void updateById(String id) throws SystemException {
        if (id == null) {
            throw new SystemException("要更新到索引库的内容有误(null)");
        }
        Article article = articleService.selectByPrimaryKey(id);
        if (article == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        ArticleSearchDTO articleSearchDTO = entity2searchDTO(article);
        try {
            searchUtil.add(articleSearchDTO, ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException(ARTICLE_CORE + "内容更新到solr索引库失败");
        }
    }

    @Override
    public void updateByIdList(List<String> idList) throws SystemException {
        if (idList == null) {
            throw new SystemException("要更新到索引库的" + ARTICLE_CORE + "内容有误(null)");
        }
        for (String id : idList) {
            updateById(id);
        }
    }

    @Override
    public void deleteById(String id) throws SystemException {
        if (id == null) {
            throw new SystemException("要删除索引库的" + ARTICLE_CORE + "内容时参数有误(null)");
        }
        try {
            searchUtil.deleteById(id, ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException("删除索引库的" + ARTICLE_CORE + "内容失败");
        }
    }

    @Override
    public void deleteByIdList(List<String> idList) throws SystemException {
        if (idList == null) {
            throw new SystemException("要删除索引库的" + ARTICLE_CORE + "内容时参数有误(null)");
        }
        try {
            searchUtil.deleteByIdList(idList, ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException("删除索引库的" + ARTICLE_CORE + "内容失败");
        }
    }

    @Override
    public void empty() throws SystemException {
        try {
            searchUtil.emptyCore(ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException("清空" + ARTICLE_CORE + "索引库的内容失败");
        }
    }

    @Override
    public QueryResponse search(SolrQuery solrQuery) throws SystemException {
        try {
            return searchUtil.search(solrQuery, ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！没有找到数据！(solr err)");
        }
    }

    @Override
    public void reload() {
        //如果在更新中就不用再开线程更新了。
        if(reloadSchedule.equals(ARTICLE_CORE+"数据更新中")){
            return;
        }
        new SolrReloadThread(searchUtil, articleService, this,
                ARTICLE_CORE, STOP_ARTICLE_RELOAD).start();
    }

    @Override
    public String getReloadSchedule() {
        return reloadSchedule;
    }

    @Override
    public void setReloadSchedule(String schedule) {
        this.reloadSchedule = schedule;
    }

    @Override
    public void stopReload() {
        reloadSchedule = STOP_ARTICLE_RELOAD;
    }

    @Override
    public void update(Article article) throws SystemException {
        if (article == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        ArticleSearchDTO articleSearchDTO = entity2searchDTO(article);
        try {
            searchUtil.add(articleSearchDTO, ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException(ARTICLE_CORE + "内容更新到solr索引库失败");
        }
    }

    @Override
    public void updateList(List<Article> articleList) throws SystemException {
        List<Object> articleSearchDTOList = new ArrayList<Object>();
        for (Article article : articleList) {
            articleSearchDTOList.add(entity2searchDTO(article));
        }
        try {
            searchUtil.addList(articleSearchDTOList, ARTICLE_CORE);
        } catch (Exception e) {
            throw new SystemException(ARTICLE_CORE + "内容更新到solr索引库失败");
        }
    }

    public ArticleSearchDTO entity2searchDTO(Article article) {
        if (article == null) {
            article = new Article();
        }
        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO();
        articleSearchDTO.setAuthor(article.getAuthor());
        articleSearchDTO.setArticleImg(article.getArticleImg());
        articleSearchDTO.setArticleImgS(article.getArticleImgS());
        articleSearchDTO.setAuthed((article.getAuthed() == null ? null : article.getAuthed() ? "true" : "false"));
        articleSearchDTO.setCategoryId(article.getCategoryId());
        articleSearchDTO.setContent(article.getContent());
        articleSearchDTO.setFilePath(article.getFilePath());
        articleSearchDTO.setHits(article.getHits() == null ? null : "" + article.getHits());
        articleSearchDTO.setId(article.getId());
        articleSearchDTO.setKeywords(article.getKeywords());
        articleSearchDTO.setOldId(article.getOldId() == null ? null : "" + article.getOldId());
        articleSearchDTO.setOutLink(article.getOutLink());
        articleSearchDTO.setSummary(article.getSummary());
        articleSearchDTO.setTitle(article.getTitle());
        articleSearchDTO.setWeight(article.getWeight() == null ? null : "" + article.getWeight());
        articleSearchDTO.setXfrom(article.getXfrom());
        articleSearchDTO.setHistoryId(article.getHistoryId());

        if (article.getCreateTime() == null) {
            articleSearchDTO.setCreateTime(null);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            //dateString最终要的是这种格式："yyyy-MM-ddTHH:mm:ssZ"，而formatter不认识"T,Z"
            String dateString = formatter.format(article.getCreateTime());
            StringBuffer strbuf = new StringBuffer(dateString);
            strbuf.replace(10, 11, "T");
            strbuf.replace(19, 20, "Z");
            dateString = strbuf.toString();
            articleSearchDTO.setCreateTime(dateString);
        }
        return articleSearchDTO;
    }

    public Article searchDTO2entity(ArticleSearchDTO articleSearchDTO) {
        Article article = new Article();
        article.setAuthor(articleSearchDTO.getAuthor());
        article.setArticleImg(articleSearchDTO.getArticleImg());
        article.setArticleImgS(articleSearchDTO.getArticleImgS());
        article.setAuthed(articleSearchDTO.getAuthed() == null ? null : articleSearchDTO.getAuthed().equals("true"));
        article.setCategoryId(articleSearchDTO.getCategoryId());
        article.setContent(articleSearchDTO.getContent());
        article.setFilePath(articleSearchDTO.getFilePath());
        article.setHits(articleSearchDTO.getHits() == null ? null : new Integer(articleSearchDTO.getHits()));
        article.setId(articleSearchDTO.getId());
        article.setKeywords(articleSearchDTO.getKeywords());
        article.setOldId(articleSearchDTO.getOldId() == null ? null : new Integer(articleSearchDTO.getOldId()));
        article.setOutLink(articleSearchDTO.getOutLink());
        article.setSummary(articleSearchDTO.getSummary());
        article.setTitle(articleSearchDTO.getTitle());
        article.setWeight(articleSearchDTO.getWeight() == null ? null : new Integer(articleSearchDTO.getWeight()));
        article.setXfrom(articleSearchDTO.getXfrom());
        article.setHistoryId(articleSearchDTO.getHistoryId());

        if (articleSearchDTO.getCreateTime() == null) {
            article.setCreateTime(null);
        } else {
            String dateString = articleSearchDTO.getCreateTime();
            //拿出来的dateString格式是这样的："yyyy-MM-ddTHH:mm:ssZ"，而formatter不认识"T,Z"
            StringBuffer strbuf = new StringBuffer(dateString);
            strbuf.replace(10, 11, " ");
            strbuf.replace(19, 20, " ");
            dateString = strbuf.toString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            ParsePosition pos = new ParsePosition(8);
            Date createTime = formatter.parse(dateString, pos);
            article.setCreateTime(createTime);
        }
        return article;
    }

//    @Override
//    public String getCore() {
//        return core;
//    }


    @Override
    public Object getEntity() {
        return new Article();
    }

    @Override
    public ArrayList<Object> getEntitySearchDtoList(List<Object> list) {
        //System.out.println("----进入了Article 的getEntitySearchDtoList");
        ArrayList<Object> entitySearchDTOList = new ArrayList<Object>();
        for (Object entity : list) {
            Article article = (Article) entity;
            entitySearchDTOList.add(entity2searchDTO(article));
        }
        return entitySearchDTOList;
    }
}
