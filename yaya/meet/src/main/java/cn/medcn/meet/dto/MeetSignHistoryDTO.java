package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/13.
 */
@Data
@NoArgsConstructor
public class MeetSignHistoryDTO implements Serializable {
    // 用户ID
    private Integer userId;

    // 用户姓名
    private String nickname;

    // 医院
    private String unitName;

    // 科室
    private String subUnitName;

    // 签到时间
    private Date signTime;

    // 是否签到成功
    private Boolean signFlag;
}
