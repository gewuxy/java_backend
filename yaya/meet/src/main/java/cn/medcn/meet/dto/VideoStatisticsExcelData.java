package cn.medcn.meet.dto;

import cn.medcn.common.supports.ExcelField;
import lombok.Data;

/**
 * Created by Liuchangling on 2017/7/21.
 * 视频观看时长统计 excel字段
 */
@Data
public class VideoStatisticsExcelData {
    @ExcelField(columnIndex = 0,title = "视频名称")
    private String videoTitle;

    @ExcelField(columnIndex = 1,title = "视频时长")
    private Integer videoDuration;

    @ExcelField(columnIndex = 2,title = "观看人数")
    private Integer viewNubmer;
}
