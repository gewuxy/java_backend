import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.dto.PublicAccountDTO;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.service.AppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/4/20.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class PublicAccountTest {

    @Autowired
    private AppUserService appUserService;


    @Test
    public void testPublicAccount(){


    }

    @Test
    public void testSearch(){
        Map map = new HashMap<String,String>();
        Pageable pageable = new Pageable();
        map.put("keyword","测试");
        MyPage<PublicAccountDTO> myPage = appUserService.searchAccount(pageable);
        System.out.println(myPage);
        for (PublicAccountDTO dto:myPage.getDataList()
             ) {
            System.out.println(dto.getNickname());
        }
    }

    @Test
    public void testAttention(){
        Map<String,Integer> map = new HashMap<>();
        Pageable pageable = new Pageable(1,5);
        map.put("slaverId",7);
        MyPage<PublicAccountDTO> myPage = appUserService.mySubscribe(pageable);
        System.out.println(myPage);
        for (PublicAccountDTO dto:myPage.getDataList()
                ) {
            System.out.println(dto.getNickname());
        }
    }


}


