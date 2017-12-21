import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import cn.medcn.search.supports.Searchable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/12/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class SearchTest {


    @Autowired
    private SearchService testServiceImpl;

    @Test
    public void testSearch(){
        Searchable searchable = new Searchable();
//        searchable.and("history_id", "170510121523528");
        searchable.or("title", "药物");
        searchable.or("content", "药物");
        SearchResult result = testServiceImpl.search(searchable);

        System.out.println(result);
    }
}
