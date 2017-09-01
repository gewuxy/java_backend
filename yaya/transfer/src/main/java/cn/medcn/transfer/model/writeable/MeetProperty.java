package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.utils.DateUtils;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/19.
 */
public class MeetProperty {

    private static final String UNLIMIT = "不限";

    private Integer id;
    /**会议ID*/
    private String meetId;

    private Date startTime;

    private Date endTime;
    /**参与人数限制*/
    private Integer attendLimit;
    /**联系人*/
    private String linkman;
    /**需求或奖励学分 0表示需求 1表示奖励*/
    private Integer eduCredits;
    /**需求或者奖励学分值*/
    private Integer xsCredits;
    /**奖励人数限制*/
    private Integer awardLimit;
    /**是否禁言会议*/
    private Boolean talkForbid;
    /**指定省份参与*/
    private String specifyProvince;
    /**指定城市参与*/
    private String specifyCity;
    /**指定组参与*/
    private Integer groupId;
    /**参与人员限制类型 0表示不限制 1表示按地域科室 2表示按用户群组*/
    private Integer memberLimitType;
    /**指定科室参与*/
    private String specifyDepart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getAttendLimit() {
        return attendLimit;
    }

    public void setAttendLimit(Integer attendLimit) {
        this.attendLimit = attendLimit;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Integer getEduCredits() {
        return eduCredits;
    }

    public void setEduCredits(Integer eduCredits) {
        this.eduCredits = eduCredits;
    }

    public Integer getXsCredits() {
        return xsCredits;
    }

    public void setXsCredits(Integer xsCredits) {
        this.xsCredits = xsCredits;
    }

    public Integer getAwardLimit() {
        return awardLimit;
    }

    public void setAwardLimit(Integer awardLimit) {
        this.awardLimit = awardLimit;
    }

    public Boolean getTalkForbid() {
        return talkForbid;
    }

    public void setTalkForbid(Boolean talkForbid) {
        this.talkForbid = talkForbid;
    }

    public String getSpecifyProvince() {
        return specifyProvince;
    }

    public void setSpecifyProvince(String specifyProvince) {
        this.specifyProvince = specifyProvince;
    }

    public String getSpecifyCity() {
        return specifyCity;
    }

    public void setSpecifyCity(String specifyCity) {
        this.specifyCity = specifyCity;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getMemberLimitType() {
        return memberLimitType;
    }

    public void setMemberLimitType(Integer memberLimitType) {
        this.memberLimitType = memberLimitType;
    }

    public String getSpecifyDepart() {
        return specifyDepart;
    }

    public void setSpecifyDepart(String specifyDepart) {
        this.specifyDepart = specifyDepart;
    }



    public static MeetProperty build(MeetSourceReadOnly source){
        MeetProperty meetProperty = new MeetProperty();
        if(source != null){
            meetProperty.setStartTime(DateUtils.parseDate(source.getStartTime()));
            meetProperty.setEndTime(DateUtils.parseDate(source.getEndTime()));
            String xsCredits = source.getXsCredits();
            if(xsCredits == null || "".equals(xsCredits)){
                xsCredits = "0";
            }

            meetProperty.setEduCredits(Integer.parseInt(xsCredits) <= 0?0:1);
            meetProperty.setXsCredits(Math.abs(Integer.parseInt(xsCredits)));
            meetProperty.setTalkForbid(1 == source.getForbidTalk());
            meetProperty.setLinkman(source.getLinkman());
            String maxNumber = "0";
            meetProperty.setAttendLimit(Integer.parseInt(maxNumber));
            meetProperty.setAwardLimit(source.getMaxGiveNumber());
            meetProperty.setSpecifyProvince(UNLIMIT.equals(source.getProvince())||"全国".equals(source.getProvince())?"":source.getProvince());
            meetProperty.setSpecifyCity(UNLIMIT.equals(source.getCity())?"":source.getCity());
            meetProperty.setSpecifyDepart(UNLIMIT.equals(source.getDepartment())?"":source.getDepartment());
            if((meetProperty.getSpecifyProvince() == null || "".equals(meetProperty.getSpecifyProvince()))
                    &&(meetProperty.getSpecifyCity() == null || "".equals(meetProperty.getSpecifyCity()))
                    &&(meetProperty.getSpecifyDepart() == null || "".equals(meetProperty.getSpecifyDepart()))
                    &&(source.getNamelist() == null || "".equals(source.getNamelist()))){
                meetProperty.setMemberLimitType(0);//不限制参与
            }else if(meetProperty.getSpecifyProvince()!= null && !"".equals(meetProperty.getSpecifyProvince())){
                meetProperty.setMemberLimitType(1);
            }else if(source.getNamelist() != null && !"".equals(source.getNamelist())){
                //这里还需要未该会议的用户分组
                meetProperty.setMemberLimitType(2);
            }
        }
        return meetProperty;
    }

}
