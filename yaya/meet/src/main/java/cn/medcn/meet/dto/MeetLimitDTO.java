package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/7/16.
 * 会议指定分组用户参加
 */
@Data
@NoArgsConstructor
public class MeetLimitDTO implements Serializable {
    private String id;
    // 会议名称
    private String meetName;
    // 分组ID
    private Integer groupId;

}
