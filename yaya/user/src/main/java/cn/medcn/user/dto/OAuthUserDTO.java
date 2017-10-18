package cn.medcn.user.dto;

import cn.medcn.user.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/9/25.
 */
@Data
@NoArgsConstructor
public class OAuthUserDTO {

    protected String uid;

    protected String nickName;

    protected String avatar;

    protected String province;

    protected String city;

    protected int gender;

    protected String email;

    protected String mobile;


    public static OAuthUserDTO build(String fileBase, AppUser user){
        OAuthUserDTO dto = new OAuthUserDTO();
        if (user != null) {
            dto.setAvatar(fileBase + user.getHeadimg());
            dto.setNickName(user.getNickname());
            dto.setEmail(user.getUsername());
            dto.setMobile(user.getMobile());
            dto.setGender(user.getGender() == null ? 0 : user.getGender());
            dto.setUid(String.valueOf(user.getId()));
            dto.setProvince(user.getProvince());
            dto.setCity(user.getCity());
        }
        return dto;
    }
}
