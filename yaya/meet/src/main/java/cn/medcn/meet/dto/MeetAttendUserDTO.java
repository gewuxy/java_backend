package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/6.
 */
@Data
@NoArgsConstructor
public class MeetAttendUserDTO implements Serializable {

    private Integer id;

    // 参与日期
    private Date attendTime;

    // 姓名
    private String nickName;

    // 医院
    private String unitName;

    // 科室
    private String subUnitName;

    // 手机
    private String mobile;

    // 联系邮箱
    private String username;

    // 医院等级
    private String level;

    // 职称
    private String title;

    // 省份
    private String province;

    // 城市
    private String city;

    // 分组
    private String groupName;

    // 进入会议时间
    private Date startTime;

    // 退出会议时间
    private Date endTime;

    // 进入会议时长
    private Integer useTime;

    // 总学习时长(时：分：秒)
    private String times;

    // 会议名称
    private String meetName;

    // CME卡号
    private String cmeId;

    // 学习记录
    private String learnRecord;

}
