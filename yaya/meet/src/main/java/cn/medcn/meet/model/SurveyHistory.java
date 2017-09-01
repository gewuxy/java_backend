package cn.medcn.meet.model;

import com.alibaba.fastjson.JSON;
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
@Table(name = "t_survey_history")
public class SurveyHistory implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer paperId;

    private String meetId;

    private Integer moduleId;

    private Integer surveyId;
    /**提交时间*/
    private Date submitTime;
    /**用户id*/
    private Integer userId;
    /**作答用时*/
    private Integer usedtime;

    @Transient
    private String itemJson;

    @Transient
    private List<SurveyHistoryItem> items;


    public void initItems(){
        if(!StringUtils.isEmpty(itemJson)){
            this.items = JSON.parseArray(this.itemJson, SurveyHistoryItem.class);
        }
    }
}
