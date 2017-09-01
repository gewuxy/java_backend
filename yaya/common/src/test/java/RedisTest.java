import cn.medcn.common.Constants;
import cn.medcn.common.utils.RedisCacheUtils;
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
    private RedisCacheUtils redisCacheUtils;

    @Test
    public void testAddCache(){
        String key = Constants.TOKEN+"_0812dce29b4e4daf95b9873e9026a360";
    }

    @Test
    public void testRedis(){
        String princal = (String) redisCacheUtils.getCacheObject(Constants.TOKEN+"_0812dce29b4e4daf95b9873e9026a360");
        System.out.println(princal);
    }
}
