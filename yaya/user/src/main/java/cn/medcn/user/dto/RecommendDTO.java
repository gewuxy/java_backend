package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/5/22/022.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendDTO {
    private Integer id;

    private String nickname;

    private String headimg;

    //此推荐公众号用户是否已关注，0表示未关注，1表示已关注
    private Integer attention;
}
