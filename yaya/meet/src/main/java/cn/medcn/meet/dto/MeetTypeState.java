package cn.medcn.meet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 * 会议科室
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetTypeState implements Serializable{

    // 科室名称
    private String name;
    // 会议数
    private Integer count;
    // 科室图标
    private String icon;
}
