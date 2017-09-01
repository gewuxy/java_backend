import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.data.model.Category;
import cn.medcn.data.service.CategoryService;
import cn.medcn.data.service.DataFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class DataTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DataFileService dataFileService;

    @Test
    public void testAddCategory(){
        Category category = new Category();
        category.setId(UUIDUtil.getNowStringID());
        category.setName("临床分享");
        category.setLeaf(false);
        category.setSort(4);
        category.setPreId("0");
        categoryService.insert(category);
    }
}
