import cn.medcn.common.Constants;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.MD5Utils;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUnit;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lixuan on 2017/4/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class AppUserTest {

    @Autowired
    private AppUserService appUserService;

    //@Autowired
    //private RedisService redisService;

    @Test
    public void testAddUser(){
        AppUser user = new AppUser();
        user.setPubFlag(false);
        user.setUsername("584479243@qq.com");
        user.setNickname("海印南院");
        user.setLinkman("李轩");
        user.setAuthed(true);
        user.setPassword(MD5Utils.MD5Encode("123456","utf-8"));
        user.setMobile("15889978092");
        int result = appUserService.insert(user);
        System.out.println(result);
    }

    @Test
    public void testFind(){
        AppUser appUser = new AppUser();
        List<AppUser> list = appUserService.select(appUser);
        for(AppUser user:list){
            System.out.println(user.getUsername());
        }
    }


    @Test
    public void testPage(){
        Pageable pageable = new Pageable(1,1);
        AppUser appUser = new AppUser();
        appUser.setUsername("584479243@qq.com");
        pageable.setEntity(appUser);
        MyPage<AppUser> page = appUserService.page(pageable);
        System.out.println("page datas = "+page.getDataList().size());
        for(AppUser user:page.getDataList()){
            System.out.println("user = "+user.getUsername());
        }
    }


    @Test
    public void testRegist() throws Exception {
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
        user.setRoleId(AppRole.AppRoleType.PUB_USER.getId());
        //appUserService.executeRegist(user,null);
    }


    @Test
    public void testResetPwd(){
        Integer userId = 1;
        AppUser user = new AppUser();
        user.setId(userId);
        user.setPassword(MD5Utils.MD5Encode("888888"));
        appUserService.updateByPrimaryKeySelective(user);
    }

    @Test
    public void testAttention(){
        Integer slaverId = 7;

        Integer masterId = 14;
        appUserService.executeAttention(slaverId, masterId);
    }

    @Test
    public void testRedis(){
        //String princal = redisService.get(Constants.TOKEN+"_0812dce29b4e4daf95b9873e9026a360");
        //System.out.println(princal);
    }
}
