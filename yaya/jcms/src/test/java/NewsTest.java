import cn.medcn.article.model.AppVideo;
import cn.medcn.article.model.Article;
import cn.medcn.article.model.News;
import cn.medcn.article.model.NewsReadOnly;
import cn.medcn.article.service.ArticleService;
import cn.medcn.article.service.CspAppVideoService;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class NewsTest {

    @Autowired
    private NewsService newsService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    protected CspAppVideoService appVideoService;

    @Test
    public void testFindMaxId() throws ParseException {
        //Integer maxId = newsService.getMaxId(News.NEWS_CATEGORY.CATEGORY_ZYZX.categoryId);
        //Integer maxId = newsService.getMaxId(News.NEWS_CATEGORY.CATEGORY_YYDT.categoryId);
        //Integer maxId = newsService.getMaxId(News.NEWS_CATEGORY.CATEGORY_AQYY.categoryId);
        Integer maxId = newsService.getMaxId(News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId);

        System.out.println("maxId="+maxId);
        //首先从yaya医生获取新闻列表
        Map<String, Object> params = Maps.newHashMap();
        params.put("type", News.NEWS_TYPE.YXZH.label);
        params.put("nid", (maxId==null?0:maxId)+"");
        // 查询所有新闻
        String jsonString = HttpUtils.get(newsService.NEWS_LIST_URL, params);
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
                    articleService.insert(article);
                }

                System.out.println("成功更新"+newsList.size()+"条新闻");
            }
        }
    }

    @Test
    public void testFindAll(){
        Pageable pageable = new Pageable(1,29);
        MyPage<News> page = newsService.findAllNews(pageable);
        for(News news:page.getDataList()){
            System.out.println(news.getTitle());
        }
    }

    @Test
    public void testModifyNewsState() {
        String filePath = "C:\\Users\\Administrator\\Desktop\\其他\\需要删除的新闻0928.txt";
        File file = new File(filePath);
        String title = null;
        try{
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while((title = br.readLine())!=null){//使用readLine方法，一次读一行
                System.out.println("-- "+title);
                /*List<Article> list = articleService.findArticleByNewsTitle(title);
                if (!CheckUtils.isEmpty(list)) {
                   Article article1 = list.get(list.size()-1);
                   article1.setAuthed(false);
                   articleService.updateByPrimaryKeySelective(article1);

                }*/
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testfindvideo() {
        Integer version = 2;
        AppVideo video = appVideoService.findCspAppVideo();
        if (video != null) {
            if (version < video.getVersion()) {
                System.out.println(APIUtils.success(video));
            } else {
                System.out.println(APIUtils.success(new AppVideo()));
            }
        }

    }


}
