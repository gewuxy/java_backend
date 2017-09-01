package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_survey_history_item")
public class SurveyHistoryItem implements Serializable{

    @Id
    private Integer id;

    private String answer;

    private Integer historyId;

    private Integer questionId;
}
