import cn.medcn.user.model.AppMenu;
import cn.medcn.user.service.AppMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lixuan on 2017/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class AppMenuTest {

    @Autowired
    private AppMenuService appMenuService;

    @Test
    public void testFindMenuByRole(){
        List<AppMenu> list = appMenuService.findMenuByRole(1);
        for(AppMenu appMenu:list){
            System.out.println(appMenu.getMenuName());
        }
    }
}
