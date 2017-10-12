import cn.medcn.common.service.OpenOfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lixuan on 2017/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class OpenOfficeTest {

    @Autowired
    private OpenOfficeService openOfficeService;


//    @Test
//    public void testConverPPT(){
//        String pptFilePath = "D:\\lixuan\\test\\精神病学-1.ppt";
//        String targetFilePath = "course/test/";
//        openOfficeService.convertPPT(pptFilePath, targetFilePath);
//    }

    @Test
    public void testConvertHtml(){
        String pptFilePath = "D:/lixuan/test/Z11021165.doc";
        String htmlFilePath = "D:/lixuan/test/";
        openOfficeService.convert2Html(pptFilePath, htmlFilePath);
    }
}
