import cn.medcn.common.utils.MD5Utils;
import cn.medcn.sys.model.SystemRole;
import cn.medcn.sys.model.SystemUser;
import cn.medcn.sys.service.SystemUserService;
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
public class UserTest {

    @Autowired
    private SystemUserService systemUserService;

    @Test
    public void testAddUser(){
        SystemUser user = new SystemUser();
        user.setMobile("");
        user.setActive(true);
        user.setUserName("admin");
        user.setRealName("系统管理员");
        user.setPassword(MD5Utils.MD5Encode("123456"));
        user.setRoleId(SystemRole.SystemRoleType.ADMIN.getRoleId());
        //user.setRole(SystemRole.SystemRoleType.ADMIN.getRoleId());
        systemUserService.insert(user);
    }

    @Test
    public void testUpdateUser(){
        SystemUser user = systemUserService.selectByPrimaryKey(1);
        user.setPassword(MD5Utils.MD5Encode("123456"));
        systemUserService.updateByPrimaryKeySelective(user);
    }
}
