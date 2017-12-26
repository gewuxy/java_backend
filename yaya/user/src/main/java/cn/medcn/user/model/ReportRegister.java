package cn.medcn.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用戶注冊統計表
 * Created by LiuLP on 2017/12/25/025.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_report_register")
public class ReportRegister {

    @Id
    private Integer id;

    //微博注册人数
    private Integer weiBoCount = 0;

    //微信注册人数
    private Integer weiXinCount = 0;

    private Integer emailCount = 0;

    private Integer mobileCount = 0;

    private Integer yaYaCount = 0;

    private Integer facebookCount = 0;

    private Integer twitterCount = 0;

    private Boolean abroad;

    private Date registerTime;
}
