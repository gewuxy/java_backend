import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.service.SystemRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/5/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class RoleTest {

    @Autowired
    private SystemRoleService systemRoleService;

    @Test
    public void testAddRole(){
        SystemRole role = new SystemRole();
        role.setRoleName("editor");
        role.setRoleDesc("内容管理员");
        role.setRoleWeight(10);
        systemRoleService.insert(role);
    }
}
