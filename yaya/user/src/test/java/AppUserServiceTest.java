import cn.medcn.common.utils.ActiveCodeUtils;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.user.dao.ActiveCodeDAO;
import cn.medcn.user.model.ActiveCode;
import cn.medcn.user.model.AppUnit;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml", "classpath*:spring-local.xml"})
public class AppUserServiceTest {

    @Autowired
    private AppUserService appUserService;


    @Test
    public void testRegistUser() throws Exception {
        AppUser user=  new AppUser();
        user.setMobile("15889978092");
        user.setUsername("test@qq.com");
        user.setNickname("test");
        user.setLinkman("test");
        user.setAuthed(true);
        AppUnit userDetail = new AppUnit();
        user.setProvince("广东");
        user.setCity("广州");
        userDetail.setUnitName("海印南院");
        userDetail.setSubUnitName("泌尿外科");
        user.setAddress("广东省广州市天河区珠江新城天汇广场2603");
        user.setPassword(MD5Utils.MD5Encode("123456"));
        user.setUserDetail(userDetail);
       // appUserService.executeRegist(user,"123456");
    }




}
