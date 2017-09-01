import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.common.utils.LetterUtils;
import cn.medcn.meet.dto.ExamDTO;
import cn.medcn.meet.dto.QuestionDTO;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.ExamService;
import com.alibaba.fastjson.JSONArray;
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
 * Created by lixuan on 2017/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class ExamTest {

    @Autowired
    private ExamService examService;

    @Test
    public void testImportQuestions() throws Exception {
        File file = new File("D:\\lixuan\\test\\试题批量导入模板.xlsx");
        List<Object[]> datas = ExcelUtils.readExcel(file);
        List<ExamQuestion> questionList = examService.parseExamQuestions(datas);
        ExamPaper paper = new ExamPaper();
        paper.setOwner(14);
        paper.setCategory("儿科");
        paper.setCreateTime(new Date());
        paper.setPaperName("2017卫生资格《初级护士》模考一");
        Integer paperId = examService.addPaper(paper);
        examService.executeImportPaper(paperId, questionList);
    }

    @Test
    public void testAddExam(){
        MeetExam exam = new MeetExam();
        exam.setMeetId("17042512131640894904");
        exam.setModuleId(5);
        exam.setUsetime(50);
        exam.setPaperId(1);
        examService.insert(exam);
    }

    @Test
    public void testAddPaper(){
        ExamPaper paper = new ExamPaper();
        paper.setOwner(14);
        paper.setCategory("儿科");
        paper.setCreateTime(new Date());
        paper.setPaperName("2017卫生资格《初级护士》模考一");
        examService.addPaper(paper);
    }


    @Test
    public void testFindExam(){
        ExamDTO exam = examService.findMeetExam("17042512131640894904", 8);
        System.out.println(exam.getPaper().getName());
        for(QuestionDTO question:exam.getPaper().getQuestions()){
            System.out.println(question.getTitle());
        }
    }

    @Test
    public void testFindQuestion(){
//        ExamQuestion question = examService.findQuestion(4);
//        for(KeyValuePair pair:question.getOptionList()){
//            System.out.println("key:"+pair.getKey()+" - value:"+pair.getValue());
//        }
    }


    @Test
    public void testSubmit(){
        for(int index=1; index < 100; index++){
            ExamHistory history = new ExamHistory();
            history.setSubmitTime(new Date());
            history.setFinished(true);
            history.setMeetId("17042512131640894904");
            history.setModuleId(8);
            history.setUserId(index);
            history.setExamId(2);
            history.setPaperId(2);
            List<ExamHistoryItem> items = Lists.newArrayList();
            for(int i = 61; i<= 79; i++){
                ExamHistoryItem item = new ExamHistoryItem();
                item.setQuestionId(i);
                item.setAnswer(LetterUtils.numberToLetter(new Random().nextInt(5)+1));
                items.add(item);
            }
            history.setItems(items);
            examService.submitToCache(history);

        }
    }



}
