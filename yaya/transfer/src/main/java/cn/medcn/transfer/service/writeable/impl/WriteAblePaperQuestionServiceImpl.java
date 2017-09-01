package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.ExamPaperQuestion;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAblePaperQuestionService;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAblePaperQuestionServiceImpl extends WriteAbleBaseServiceImpl<ExamPaperQuestion> implements WriteAblePaperQuestionService {
    @Override
    public String getTable() {
        return "t_exam_paper_question";
    }


}
