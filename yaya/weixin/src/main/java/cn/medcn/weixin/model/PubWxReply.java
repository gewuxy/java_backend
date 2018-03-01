package cn.medcn.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author：jianliang
 * @Date: Create in 16:30 2018/2/28
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_pub_wx_reply")
public class PubWxReply {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    //父级的问题
    private String content;

    //标识
    private Integer parentQuestionFlag;

    @Transient
    private Integer answerId;

    @Transient
    private String question;

    @Transient
    private String answer;

    @Transient
    private Integer parentId;

}
