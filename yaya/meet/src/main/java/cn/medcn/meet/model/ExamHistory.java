package cn.medcn.meet.model;

import cn.medcn.meet.dto.ExamHistoryStateDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_exam_history")
public class ExamHistory implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**试卷ID*/
    private Integer paperId;
    /**考试ID*/
    private Integer examId;
    /**用户ID*/
    private Integer userId;
    /**会议ID*/
    private String meetId;
    /**所属模块ID*/
    private Integer moduleId;
    private Date submitTime;
    /**试卷总分*/
    private Integer totalPoint;
    /**实际得分*/
    private Integer score;
    /**是否已经完成*/
    private Boolean finished;
    /**考试用时*/
    private Integer usedtime;
    /**完成次数*/
    private Integer finishTimes;

    @Transient
    private List<ExamHistoryItem> items = Lists.newArrayList();

    @Transient
    private String itemJson;

    @Transient
    private ExamHistoryStateDTO historyState;

    public void initItems(){
        if(!StringUtils.isEmpty(itemJson)){
            this.items = JSON.parseArray(this.itemJson, ExamHistoryItem.class);
        }
    }
}
