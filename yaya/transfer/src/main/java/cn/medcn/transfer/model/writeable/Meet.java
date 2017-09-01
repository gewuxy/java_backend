package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetMenuReadOnly;
import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/14.
 */
public class Meet {

    private String id;
    /**会议名称*/
    private String meetName;
    /**会议拥有者*/
    private Integer ownerId;
    /**会议拥有者登录名*/
    private String owner;
    /**会议拥有者真实姓名 如公众号名称*/
    private String organizer;
    /**会议科室*/
    private String meetType;
    /**会议状态0表示草稿 1表示未开始 2表示进行中 3表示已结束 4表示已关闭*/
    private Integer state;
    /**会议简介*/
    private String introduction;
    /**历史遗留数据ID*/
    private Integer oldId;
    /**创建时间*/
    private Date createTime;
    /**发布时间*/
    private Date publishTime;
    /**会议封面*/
    private String coverUrl;
    /**是否是推荐会议*/
    private Boolean tuijian;

    private List<MeetModule> modules;

    public List<MeetModule> getModules() {
        return modules;
    }

    public void setModules(List<MeetModule> modules) {
        this.modules = modules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getMeetType() {
        return meetType;
    }

    public void setMeetType(String meetType) {
        this.meetType = meetType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Boolean getTuijian() {
        return tuijian;
    }

    public void setTuijian(Boolean tuijian) {
        this.tuijian = tuijian;
    }

    /**
     * 根据原来的会议实体类和菜单类 构建现有的会议对象
     * @param source
     * @return
     */
    public static Meet build(MeetSourceReadOnly source){
        Meet meet = new Meet();
        if(source != null){
            meet.setOldId(source.getMeetingId().intValue());
            meet.setCreateTime(DateUtils.parseDate(source.getStartTime()));
            meet.setIntroduction(source.getIntroduction());
            meet.setMeetName(source.getMeetingName());
            meet.setMeetType(source.getKind());
            meet.setState(source.getState());
            meet.setOrganizer(source.getOrganizer());
            meet.setOwner(source.getOwner());
            meet.setPublishTime(DateUtils.parseDate(source.getStartTime()));
        }
        return meet;
    }


    public static void main(String[] args) {
        String dateStr = "2017/06/02 05:10:21";
        System.out.println(DateUtils.parseDate(dateStr));
    }
}
