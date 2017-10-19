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
//        String pptFilePath = "C:\\Users\\Administrator\\Desktop\\YCY\\v1_产品说明文档.pptx";
//        String targetFilePath = "course/test/";
//        openOfficeService.convertPPT(pptFilePath, targetFilePath);
//    }

    @Test
    public void testConvertToHtml() {
        //String pptFilePath = "C:\\Users\\Administrator\\Desktop\\YCY\\v1_产品说明文档.pptx";

        String pptFilePath = "C:\\Users\\Administrator\\Desktop\\YCY\\公众账号申请表(新).doc";
        String targetFilePath = "C:\\Users\\Administrator\\Desktop\\YCY\\doc.html";
        openOfficeService.convert2Html(pptFilePath, targetFilePath);
    }
}
