package cn.medcn.user.dto;

import cn.medcn.user.model.Advert;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**验证码
 * Created by LiuLP on 2017/7/11.
 */

@NoArgsConstructor
@Data
public class Captcha {

  private Date firstTime;

  private String msgId;

  private Integer count ;


}
