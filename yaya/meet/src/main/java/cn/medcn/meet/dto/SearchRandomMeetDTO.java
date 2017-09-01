package cn.medcn.meet.dto;


import cn.medcn.common.Constants;
import cn.medcn.common.utils.CheckUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/8/3.
 */
@Data
@NoArgsConstructor
public class SearchRandomMeetDTO {

    private String depart ;

    private String province ;

    private String city ;

    private Integer randLimit ;

    private Integer escapeUserId = Constants.YAYA_TESTING_USER_ID;

    private static final String OTHER = "其他";

    public void check(){
        if (CheckUtils.isEmpty(depart)){
            depart = OTHER;
        }
        if (CheckUtils.isEmpty(province)){
            province = OTHER;
        }
        if (CheckUtils.isEmpty(city)){
            city = OTHER;
        }
    }


    public void clear() {
        depart = null;
        province = null;
        city = null;
    }

}
