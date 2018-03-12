import cn.medcn.weixin.model.WXMenu;
import cn.medcn.weixin.service.WXMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author：jianliang
 * @Date: Create in 11:55 2018/3/2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class WXMenuTest {

    @Autowired
    private WXMenuService wxMenuService;

    @Test
    public void test(){
        wxMenuService.createMenu();
       // wxMenuService.deleteMenu();
        WXMenu menu = wxMenuService.findMenu();
        System.out.println(menu);
    }

}
