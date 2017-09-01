
//import cn.medcn.search.dto.ArticleSearchDto;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

/**
 * Created by admin on 2017/7/14.
 */
public class SearchTest {
    /*
    @Test
    public void test1() throws Exception {
        // 1.测试插入文档
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "0006");
        map.put("title", "标题6");
        map.put("xfrom", "xfrom6");
        map.put("author", "作者6");
        //map.put("content", "内容内容 内容88");
        //map.put("summary", "总结88");
        //map.put("keywords", "关键字88");
        ArticleSearch.add(map, ArticleSearch.SOLR_CORE);
        //if(true){return;}

        // 2.通过bean添加document
        List<ArticleSearchDto> articles = new ArrayList<ArticleSearchDto>();
        ArticleSearchDto article1=new ArticleSearchDto();
        article1.setId("63");
        article1.setAuthor("作者6");
        article1.setContent("内容内容内容63");
        article1.setTitle("Title63");
        article1.setSummary("Summary63");
        article1.setKeywords("Keywords63");
        article1.setXfrom("Xfrom63");
        article1.setOutLink("outLine655");
        article1.setArticleImgS("ssssssssssssssssssss");
        articles.add(article1);
        ArticleSearchDto article2=new ArticleSearchDto();
        article2.setId("64");
        article2.setAuthor("作者644");
        article2.setContent("内容内容内容6444");
        article2.setTitle("Title64");
        article2.setSummary("Summary64");
        article2.setKeywords("Keywords644");
        article2.setXfrom("Xfrom64");
        article2.setOutLink("outLine64");
        articles.add(article2);
        ArticleSearch.addList(articles, ArticleSearch.SOLR_CORE);

        // 3.根据id集合删除索引
        List<String> ids = new ArrayList<String>();
        ids.add("34");
        ArticleSearch.deleteByIdList(ids, ArticleSearch.SOLR_CORE);

        // 4.查询
        //ArticleSearch.getDocument(ArticleSearch.SOLR_CORE);

        // 5.spell测试
        //ArticleSearch.getSpell(ArticleSearch.SOLR_CORE);

    }/**/
}
