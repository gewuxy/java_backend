package cn.medcn.jcms.controller.manage;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.MeetExamDetailDTO;
import cn.medcn.meet.dto.MeetSurveyDetailDTO;
import cn.medcn.meet.model.ExamQuestion;
import cn.medcn.meet.model.SurveyQuestion;
import cn.medcn.meet.service.ExamService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.SurveyService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/6/12/012.
 */
@Controller
@RequestMapping("/mng/doctor/")
public class ExamSurveyInfoController {

    @Autowired
    private MeetService meetService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ExamService examService;

    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 查看考试明细
     * @return
     */
    @RequestMapping("/examDetail")
    public String examInfo(Integer historyId, Model model ) throws SystemException{
        Map<String,Object> map = new HashedMap<>();
        Integer ownerId = SubjectUtils.getCurrentUserid();
        map.put("ownerId",ownerId);
        map.put("historyId", historyId);
        MeetExamDetailDTO dto = meetService.getUserInfo(map);
        if(dto == null){
            throw new SystemException("非法考试id");
        }
        //查找试卷题目
        List<ExamQuestion> examList =  examService.findExamPaper(dto.getPaperId()).getQuestionList();
        for(ExamQuestion question:examList){
            String answer = examService.findAnswer(question.getId(), historyId);
            question.setAnswer(answer);
        }
        dto.setHeadimg(appFileBase+dto.getHeadimg());
        model.addAttribute("detail",dto);
        model.addAttribute("examList",examList);
        return "/manage/meetExamDetail";
    }


    /**
     * 问卷明细
     * @param historyId
     * @param model
     * @return
     */
    @RequestMapping("/surveyDetail")
    public String surveyInfo(Integer historyId, Model model) throws SystemException{
        Map<String,Object> map = new HashedMap<>();
        Integer ownerId = SubjectUtils.getCurrentUserid();
        map.put("ownerId",ownerId);
        map.put("historyId", historyId);
        //查找用户的信息
        MeetSurveyDetailDTO dto = meetService.findUserInfo(map);
        if(dto == null){
            throw new SystemException("非法问卷id");
        }
        //查找问卷题目
       List<SurveyQuestion> questionList = surveyService.findQuestions(dto.getPaperId());
        for(SurveyQuestion question :questionList){
            String answer = surveyService.findAnswer(question.getId(), historyId);
            question.setAnswer(answer);
        }
        dto.setHeadimg(appFileBase+dto.getHeadimg());
        model.addAttribute("detail",dto);
        model.addAttribute("questionList",questionList);
        return "/manage/meetSurveyDetail";
    }


}
