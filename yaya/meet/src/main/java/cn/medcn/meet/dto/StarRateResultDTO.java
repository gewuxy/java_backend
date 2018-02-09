package cn.medcn.meet.dto;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.SpringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 星评统计结果
 * Created by lixuan on 2018/1/15.
 */
@Data
@NoArgsConstructor
public class StarRateResultDTO implements Serializable {

    public static final String MULTIPLE_RATE_LOCAL_KEY = "page.meeting.multiple.score";

    //课件id
    protected Integer id;
    //评分项ID
    protected int optionId = 0;
    //评分项名称
    protected String title;
    //平均得分
    protected float avgScore;
    //评分次数
    protected int scoreCount;
    //简介
    protected String info;

    //是否有星评
    protected Boolean starStatus;

    //星评二维码地址
    protected String startCodeUrl;

    protected Date serverTime = new Date();

    protected Date expireDate;


    public float getAvgScore(){
        if (this.avgScore == 0) {
            return 0;
        } else {
            DecimalFormat format = new DecimalFormat( "#0.0");
            return Float.valueOf(format.format(this.avgScore));
        }
    }


    public String getTitle(){
        if (CheckUtils.isEmpty(title)) {
            return SpringUtils.getMessage(MULTIPLE_RATE_LOCAL_KEY);
        } else {
            return this.title;
        }
    }

}
