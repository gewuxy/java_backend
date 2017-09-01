import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.LetterUtils;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.SurveyService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lixuan on 2017/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class SurveyTest {

    @Autowired
    private SurveyService surveyService;

    @Test
    public void testAddPaper() throws Exception {
        SurveyPaper paper = new SurveyPaper();
        paper.setCategory("骨外科");
        paper.setCreateTime(new Date());
        paper.setOwner(14);
        paper.setPaperName("测试问卷调查卷一");
        Integer paperId = surveyService.addSurveyPaper(paper);

        File file = new File("D:\\lixuan\\test\\survey_template.xlsx");
        List<Object[]> datas = ExcelUtils.readExcel(file);
        List<SurveyQuestion> list = surveyService.parseSurveyQuestions(datas);
        surveyService.executeImport(paperId,list);
    }


    @Test
    public void testFindPaper(){
        MeetSurvey survey = surveyService.findMeetSurvey("17042512131640894904",10);
        System.out.println(survey.getMeetId());
        if(survey.getSurveyPaper()!=null){
            System.out.println(survey.getSurveyPaper().getPaperName());
            for(SurveyQuestion question:survey.getSurveyPaper().getQuestionList()){
                System.out.println(question.getTitle());
            }
        }
    }


    @Test
    public void testSubmitSurvey(){
        SurveyHistory history = new SurveyHistory();
        history.setPaperId(2);
        history.setModuleId(10);
        history.setMeetId("17042512131640894904");
        history.setUserId(7);
        history.setSurveyId(1);
        List<SurveyHistoryItem> items = Lists.newArrayList();
        for(int i = 11; i <= 20;i++){
            SurveyHistoryItem item = new SurveyHistoryItem();
            item.setQuestionId(i);
            item.setAnswer(LetterUtils.numberToLetter(new Random().nextInt(5)+1));
            items.add(item);
        }
        history.setItems(items);
        surveyService.executeSubmit(history);
    }
}
