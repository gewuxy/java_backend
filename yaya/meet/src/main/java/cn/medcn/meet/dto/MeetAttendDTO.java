package cn.medcn.meet.dto;

import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.CheckUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/8/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetAttendDTO {

    private int totalCount;

    private int unitCount;

    private List<MeetAttendDetailDTO> detailList;

    private String[] dateArray;

    private int[] countArray;

    private long[] timeArray;

    public List<MeetAttendDetailDTO> build(){
        List<MeetAttendDetailDTO> list = new ArrayList<>();
        for(int index = 0; index < timeArray.length; index++){
            MeetAttendDetailDTO detailDTO = new MeetAttendDetailDTO();
            detailDTO.setAttendTime(timeArray[index]);
            detailDTO.setAttendDate(null);
            detailDTO.setCount(countArray[index]);
            list.add(detailDTO);
        }
        return list;
    }

    public void initCountArray(){
        countArray = new int[dateArray.length];
        if (!CheckUtils.isEmpty(this.getDetailList())) {
            for (int index = 0 ; index < countArray.length; index++){
                for (MeetAttendDetailDTO detailDTO : this.getDetailList()){
                    if (dateArray[index].equals(detailDTO.getAttendDate())){
                        countArray[index] = detailDTO.getCount();
                        break;
                    }
                }
            }
        }
    }
}
