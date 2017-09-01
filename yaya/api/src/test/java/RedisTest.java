import cn.medcn.api.dto.Principal;
import cn.medcn.common.Constants;
import cn.medcn.common.utils.RedisCacheUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/4/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class RedisTest {
    @Autowired
    private RedisCacheUtils<Principal> redisCacheUtils;

    @Test
    public void testRedis(){
        String princal = (String) redisCacheUtils.getCacheObject(Constants.TOKEN+"_0812dce29b4e4daf95b9873e9026a360");
        System.out.println(princal);
    }

    @Test
    public void testAdd(){
        Principal principal = new Principal();
        principal.setId(1);
        principal.setNickname("哈哈");
        principal.setMobile("15889978092");
        principal.setToken("3e2ae70ee76b4e62b2f97cb47a44ad0f");
        principal.setUsername("18194529@qq.com");
        redisCacheUtils.setCacheObject("token_3e2ae70ee76b4e62b2f97cb47a44ad0f",JSON.toJSONString(principal), Constants.TOKEN_EXPIRE_TIME);
    }


    @Test
    public void testGet(){
        String str = redisCacheUtils.getCacheObject("token_3e2ae70ee76b4e62b2f97cb47a44ad0f");
        System.out.println(str);
        Principal test = JSON.parseObject(str, Principal.class);
        System.out.println(test.getId()+" - "+test.getNickname()+" - "+test.getMobile()+" - token="+test.getToken());
    }
}
