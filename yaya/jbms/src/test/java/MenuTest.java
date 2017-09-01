import cn.medcn.common.excptions.SystemException;
import cn.medcn.sys.model.SystemMenu;
import cn.medcn.sys.service.SysMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lixuan on 2017/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class MenuTest {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void testAddMenu(){
        SystemMenu menu = new SystemMenu();
        menu.setPreid(0);
        menu.setName("测试管理");
        menu.setSort(6);
        menu.setHide(false);
        sysMenuService.insert(menu);

    }

    @Test
    public void testFindMenu() throws SystemException {
        List<SystemMenu> menuList = sysMenuService.findAllSubMenus(1);
        for(SystemMenu menu:menuList){
            System.out.println(menu.getName());
        }
    }
}
