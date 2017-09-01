import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/7/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class WeixinTest {

//    @Autowired
//    private WXOauthService wxOauthService;
//
//    @Test
//    public void testFindUserInfo(){
//        String openId = "o6QjQvgaCgEKl46FCA7ykKp-N7e8";
//        WXUserInfo user = wxOauthService.getUserInfoByOpenId(openId);
//        Assert.assertNotNull(user);
//    }
}
