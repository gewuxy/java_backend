package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 用户观看视频记录 导出excel字段
 */
@Data
public class SeeVideoRecordExcelData {
    @ExcelField(columnIndex = 0,title = "视频名称")
    private String videoTitle;

    @ExcelField(columnIndex = 1,title = "视频时长")
    private String videoDuration;

    @ExcelField(columnIndex = 2,title = "姓名")
    private String name;

    @ExcelField(columnIndex = 3,title = "观看时长（秒）")
    private Integer watchTime;

    @ExcelField(columnIndex = 4,title = "观看进度")
    private String watchProgress;

}
