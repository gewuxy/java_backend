import cn.medcn.common.service.JPushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class JpushTest {

    @Autowired
    private JPushService jPushService;

    @Test
    public void testSend(){
        //jPushService.sendToRegistrationId("FC56D5F6-F648-4C9A-A300-5A9A1737CF6B", "test", "test", "haha", "");
    }
}
