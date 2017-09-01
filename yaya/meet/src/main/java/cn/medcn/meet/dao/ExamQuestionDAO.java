package cn.medcn.meet.dao;

import cn.medcn.meet.model.ExamQuestion;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
public interface ExamQuestionDAO extends Mapper<ExamQuestion> {

    List<ExamQuestion> findPaperQuestions(@Param("paperId")Integer paperId);

    ExamQuestion findQuestion(@Param("paperId") Integer paperId, @Param("questionId")Integer questionId);

    List<Integer> findQuestionSort(@Param("meetId")String meetId);
}
