package cn.medcn.meet.dto;

import cn.medcn.meet.model.InfinityTree;
import cn.medcn.meet.model.MeetMaterial;
import cn.medcn.meet.model.MeetModule;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
public class MeetInfoDTO implements Serializable{

    private String id;
    /**会议名称*/
    private String meetName;
    /**主办方头像*/
    private String headimg;
    /**会议主办方*/
    private String organizer;
    /**会议科室类型*/
    private String meetType;
    /**会议简介*/
    private String introduction;
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
    /**开始时间*/
    private Date startTime;
    /**结束时间*/
    private Date endTime;
    /**会议封面*/
    private String coverUrl;

    /**是否奖励象数*/
    private Boolean requiredXs;

    /**需求或者奖励象数值*/
    private Integer xsCredits;
    /**奖励限制人数*/
    private Integer awardLimit;

    // 是否有学分奖励
    private Boolean rewardCredit;
    /**奖励学分*/
    private Integer eduCredits;
    // 奖励学分限制人数
    private Integer awardCreditLimit;

    private Integer state;
    /**是否收藏*/
    private Integer stored;
    /**所属单位号的ID*/
    private Integer pubUserId;
    /**是否关注*/
    private Boolean attention;
    @Transient
    private List<MeetModule> modules;
    /**会议资料*/
    private List<MeetMaterial> materials;
    /**资料总数*/
    private Integer materialCount;

    private Boolean attendAble = false;
    /**不能参与会议理由*/
    private String reason;
    /**剩余奖励象数人数*/
    private Integer remainAwardXsCount;
    /**剩余奖励学分人数*/
    private Integer remainAwardCreditCount;

    /**是否参加过会议*/
    private Boolean attended;


    // 完成进度
    private Integer completeProgress;
    // 获得奖励象数
    private Integer receiveAwardXs;
    // 获得奖励学分
    private Integer receiveAwardCredit;

    // 会议直播类型 0表示录播 1表示PPT直播 2表示视频直播
    private Integer playType;

    // 转载自单位号id
    private Integer primitiveId;
    // 转载自单位号
    private String reprintFromUnitUser;



}
