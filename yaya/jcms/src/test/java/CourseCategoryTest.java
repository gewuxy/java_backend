import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.meet.model.CourseCategory;
import cn.medcn.meet.service.CourseCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;

/**
 * Created by lixuan on 2017/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class CourseCategoryTest {

    @Autowired
    protected CourseCategoryService courseCategoryService;


    @Test
    public void testParse() throws Exception {
        String path = "D:\\lixuan\\工作记录\\csp\\course_category.xlsx";
        List<Object[]> objArrayList = ExcelUtils.readExcel(new File(path));

        String rootNamec = null;
        String rootNamee = null;
        Integer parentId = null;
        for (Object[] objArray : objArrayList) {

            if (objArray[0] != null && !"".equals(objArray[0])) {
                rootNamec = (String) objArray[0];
                rootNamee = (String) objArray[1];
                //保存root栏目
                CourseCategory cc = new CourseCategory();
                cc.setDepth(1);
                cc.setNameCn(rootNamec);
                cc.setNameEn(rootNamee);
                cc.setParentId(0);
                courseCategoryService.insert(cc);
                parentId = cc.getId();
            }
            //保存子栏目
            CourseCategory sub = new CourseCategory();
            sub.setParentId(parentId);
            sub.setDepth(2);
            sub.setNameCn((String) objArray[2]);
            sub.setNameEn((String) objArray[3]);
            courseCategoryService.insert(sub);
        }
    }
}
