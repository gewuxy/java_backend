package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/5/18.
 */
@Data
@NoArgsConstructor
public class UserAttendDTO  implements Serializable {

    // 本周新增关注数
    private Integer weekAttendCount;

    // 本月新增关注数
    private Integer monthAttendCount;

    // 总关注数
    private Integer totalAttendCount;


}
