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

  public enum Type{
    LOGIN(0, "登录"),
    BIND(1, "绑定");

    private Integer typeId;
    private String label;

    public Integer getTypeId() {
      return typeId;
    }

    public void setTypeId(Integer typeId) {
      this.typeId = typeId;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    Type(Integer typeId, String label) {
      this.typeId = typeId;
      this.label = label;
    }
  }
}
