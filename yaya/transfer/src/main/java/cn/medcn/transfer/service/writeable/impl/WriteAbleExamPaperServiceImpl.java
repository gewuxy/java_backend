package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.ExamPaper;
import cn.medcn.transfer.model.writeable.ExamPaperQuestion;
import cn.medcn.transfer.model.writeable.ExamQuestion;
import cn.medcn.transfer.model.writeable.MeetExam;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleExamPaperService;
import cn.medcn.transfer.service.writeable.WriteAbleExamQuestionService;
import cn.medcn.transfer.service.writeable.WriteAblePaperQuestionService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleExamPaperServiceImpl extends WriteAbleBaseServiceImpl<ExamPaper> implements WriteAbleExamPaperService {
    @Override
    public String getTable() {
        return "t_exam_paper";
    }

    private WriteAbleExamQuestionService writeAbleExamQuestionService = new WriteAbleExamQuestionServiceImpl();

    private WriteAblePaperQuestionService writeAblePaperQuestionService = new WriteAblePaperQuestionServiceImpl();

    @Override
    public Integer addExamPaper(ExamPaper paper) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Long idLong = (Long) insert(paper);
        Integer id = idLong.intValue();
        paper.setId(id);

        int sort = 1;
        Map<Integer, Integer> scoreMap = getScoreMap(paper);
        for(ExamQuestion question : paper.getQuestionList()){
            Integer qid = writeAbleExamQuestionService.addQuestion(question);
            ExamPaperQuestion paperQuestion = new ExamPaperQuestion();
            paperQuestion.setPaperId(id);
            paperQuestion.setQuestionId(qid);
            paperQuestion.setSort(sort);
            paperQuestion.setPoint(scoreMap.get(sort));
            writeAblePaperQuestionService.insert(paperQuestion);
            sort ++;
        }
        return id;
    }


    private Map<Integer, Integer> getScoreMap(ExamPaper examPaper){
        int totalPoint = examPaper.getTotalPoint();
        Map<Integer, Integer> scoreMap = new HashMap<>();
        if(examPaper.getQuestionList() != null && examPaper.getQuestionList().size() > 0){
            Integer qsize = examPaper.getQuestionList().size();
            int avgScore = totalPoint/qsize;
            int leftScore = totalPoint%qsize;
            for(int i = qsize ; i >0; i -- ){
                if(leftScore>0){
                    scoreMap.put(i, avgScore+1);
                    leftScore -- ;
                }else{
                    scoreMap.put(i, avgScore);
                }
            }
        }
        return scoreMap;
    }


    public static void main(String[] args) {
        Integer totalPoint = 100;
        Integer qsize = 51;
        Integer avgScore = totalPoint/qsize;
        Integer lastScore = avgScore+totalPoint%qsize;
        System.out.println("avgScore = "+avgScore+" - lastScore = "+lastScore);

    }
}
