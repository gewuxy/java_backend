package cn.medcn.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

/**
 * 用戶注冊統計表
 * Created by LiuLP on 2017/12/25/025.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRegister {

    @Id
    private Integer id;

    //微博注册人数
    private Integer weiBoCount;

    //微信注册人数
    private Integer weiXinCount;

    private Integer emailCount;

    private Integer mobileCount;

    private Integer yaYaCount;

    private Integer facebookCount;

    private Integer twitterCount;

    private Boolean abroad;

    private Date registerTime;
}
