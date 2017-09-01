import cn.medcn.common.utils.ActiveCodeUtils;
import cn.medcn.user.model.ActiveCode;
import cn.medcn.user.service.ActiveCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by lixuan on 2017/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class ActiveCodeTest {

    @Autowired
    private ActiveCodeService activeCodeService;


    @Test
    public void testAddCode(){
        for(int i=0; i < 100; i ++){
            ActiveCode code = new ActiveCode();
            code.setUsed(false);
            code.setCode(ActiveCodeUtils.genericActiveCode());
            code.setOnwerid(14);
            code.setSendTime(new Date());
            code.setActived(true);
            activeCodeService.insert(code);
        }
    }
}
