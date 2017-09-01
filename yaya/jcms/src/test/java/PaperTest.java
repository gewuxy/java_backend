import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.meet.model.ExamPaper;
import cn.medcn.meet.model.ExamQuestion;
import cn.medcn.meet.service.ExamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class PaperTest {

    @Autowired
    private ExamService examService;

    @Test
    public void testParsePaper() throws Exception {
        File file = new File("D:\\lixuan\\test\\试题批量导入模板.xlsx");
        List<Object[]> datas = ExcelUtils.readExcel(file);
        List<ExamQuestion> questionList = examService.parseExamQuestions(datas);
        for(ExamQuestion question:questionList){
            System.out.println(question.getTitle()+" - potin="+question.getPoint()+" - rightKey = "+question.getRightKey());
        }
    }
}
