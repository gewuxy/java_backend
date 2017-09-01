package cn.medcn.meet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/8/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetAttendDetailDTO {

    private String attendDate;

    private int count;

    private long attendTime;
}
