package cn.medcn.meet.dto;

import cn.medcn.meet.model.InfinityTree;
import cn.medcn.meet.model.Lecturer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by Liuchangling on 2017/8/3.
 */
@Data
@NoArgsConstructor
public class MeetFolderDTO {
    // 文件夹ID/会议ID
    private String id;
    // 用户ID
    private Integer userId;
    // 目录名称
    private String meetName;
    // 单位号名称
    private String organizer;
    // 单位号头像
    private String headimg;
    // 会议科室类型
    private String meetType;
    // 会议状态
    private Integer state;
    // 开始时间
    private Date startTime;
    // 结束时间
    private Date endTime;
    //会议发布时间
    private Data publishTime;
    // 是否文件夹0 会议1
    private Integer type;
    // 会议数
    private Integer meetCount;
    // 是否奖励象数 true奖励 false不奖励或支付
    private boolean requiredXs;
    // 奖励或支付象数值
    private Integer xsCredits;
    // 是否奖励学分
    private boolean rewardCredit;
    // 奖励学分数
    private Integer eduCredits;
    // 省份
    private String province;
    // 城市
    private String city;
    // 学习进度
    private Integer completeProgress;
    //三个主讲者信息
    protected List<Lecturer> lecturerList;

    // ==== 单个会议的讲着信息
    /**会议主讲者*/
    private String lecturer;
    /**主讲者职称*/
    private String lecturerTitle;
    /**主讲者医院*/
    private String lecturerHos;
    /**主讲者科室*/
    private String lecturerDepart;
    /**主讲者头像*/
    private String lecturerHead;


    public enum FolderType{
        folder,
        meet;
    }
}
