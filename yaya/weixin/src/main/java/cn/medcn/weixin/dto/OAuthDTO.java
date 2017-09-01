package cn.medcn.weixin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/7/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthDTO {

    private String openid;

    private String unionid;

    private String access_token;
}
