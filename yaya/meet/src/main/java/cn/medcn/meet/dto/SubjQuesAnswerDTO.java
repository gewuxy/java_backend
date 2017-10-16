package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/10/13.
 */
@Data
@NoArgsConstructor
public class SubjQuesAnswerDTO implements Serializable {

    protected Integer userId;

    protected String nickName;

    protected String answer;


}
