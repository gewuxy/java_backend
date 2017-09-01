package cn.medcn.meet.dao;

import cn.medcn.meet.model.ExamPaperQuestion;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
public interface ExamPaperQuestionDAO extends Mapper<ExamPaperQuestion> {
    /**
     * 计算试卷总分
     * @param paperId
     * @return
     */
    Integer findPaperTotalPoint(@Param("paperId") Integer paperId);

    /**
     * 查找试卷的所有试题 按sort排序
     * @return
     */
    List<ExamPaperQuestion> findPaperQuestion(@Param("paperId") Integer paperId);
}
