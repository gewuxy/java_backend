import cn.medcn.meet.model.Live;
import cn.medcn.meet.service.LiveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lixuan on 2017/12/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class LiveTest {

    @Autowired
    protected LiveService liveService;

    @Test
    public void testFindTimeOutCourses(){
        List<Live> list = liveService.findTimeOutLives();
        System.out.println("超时直播列表长度 = " + list.size());
    }

    @Test
    public void testModifyLiveState(){
        liveService.doModifyLiveState();
    }
}
