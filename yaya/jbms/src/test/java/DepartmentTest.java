import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.user.model.Department;
import cn.medcn.user.service.HospitalService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class DepartmentTest {

    @Autowired
    private HospitalService hospitalService;


    @Test
    public void testBatchAdd() throws Exception {
        File file = new File("D:\\lixuan\\工作记录\\YAYA医师重构文档\\科室列表.xlsx");
        List<Object[]> objs = ExcelUtils.readExcel(file);
        List<Department> list = Lists.newArrayList();
        for(Object[] objects:objs){
            Department depart = new Department();
            depart.setName((String) objects[0]);
            depart.setCategory((String) objects[1]);
            list.add(depart);
        }
        hospitalService.executeBatchAddDepart(list);
    }
}
