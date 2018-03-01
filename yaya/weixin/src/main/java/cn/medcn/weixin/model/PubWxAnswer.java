package cn.medcn.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author：jianliang
 * @Date: Create in 16:34 2018/2/28
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_pub_wx_answer")
public class PubWxAnswer {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    //问题
    private String question;

    //
    private String answer;

    private Integer parentId;
}
