package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/14.
 */
public class MeetSourceReadOnly {

    public static final String YES = "Y";

    public static final String NO = "N";

    private Long meetingId;   //会议id
    private String meetingName;  //会议名称
    private String owner;		 //会议发起人 、申请人
    private String startTime;	 //会议开始时间
    private String endTime;		 //会议结束时间
    private String kind;		//会议学科  类型
    private String province;	//省份
    private String city;		//城市
    private String maxNumber;	//最大参与人数
    private Integer state;		//会议 状态 0：草稿  	1：未开始(已通过) 2：进行中  3：已结束 4：已撤销 5:未通过 6:关闭
    private String reason;      //会议未通过的原因
    private String department;  //科室
    private String introduction;   //会议介绍
    private String organizer;	//主办单位
    private String meetingAgenda; //会议议程
    private String linkman;      //联系人
    private String linktel;     //联系电话
    private String email;    //Email
    private String address;   //联系地址
    private String namelist;  //名单
    private String havaEduCredits; //是否有继续教育学分 Y：有  N：没有
    private Integer eduCredits;  //继续学分数
    //private Integer xsCredits;  //所需象数 、赠送积分数
    private String xsCredits;
    private Integer credit_type;	//需要或奖励积分
    private Integer edu_type;	//学分类型
    private Integer maxGiveNumber; //最多可赠送的人数
    private Integer giveCount;  //已经赠送的人数  计数器
    private String paperFlag;  //是否有考题
    private String lecturer; //演讲者
    private String signFlag;  //是否有签到
    private Integer forbidTalk;  //是否禁言  0：开放发言  1：禁言（关闭发言）讨论
    private Integer shareFlag ; //是否分享标志，默认值为0，表示不分享；1表示分享
    private Integer meetingOwner;	//版权所有者，默认值为0，表示原创发布；非0值表示获取分享会议发布的分享方的公众号id
    private Integer shareId;	//外键，关联会议分享信息表t_meeting_share_info的share_id字段；默认值为0，表示原创发布,非0值表示转发分享会议的分享id
    private Integer meetingCategory;//会议类别，默认值为0，表示普通会议；1表示直播会议


    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getMeetingAgenda() {
        return meetingAgenda;
    }

    public void setMeetingAgenda(String meetingAgenda) {
        this.meetingAgenda = meetingAgenda;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNamelist() {
        return namelist;
    }

    public void setNamelist(String namelist) {
        this.namelist = namelist;
    }

    public String getHavaEduCredits() {
        return havaEduCredits;
    }

    public void setHavaEduCredits(String havaEduCredits) {
        this.havaEduCredits = havaEduCredits;
    }

    public Integer getEduCredits() {
        return eduCredits;
    }

    public void setEduCredits(Integer eduCredits) {
        this.eduCredits = eduCredits;
    }

    public String getXsCredits() {
        return xsCredits;
    }

    public void setXsCredits(String xsCredits) {
        this.xsCredits = xsCredits;
    }

    public Integer getCredit_type() {
        return credit_type;
    }

    public void setCredit_type(Integer credit_type) {
        this.credit_type = credit_type;
    }

    public Integer getEdu_type() {
        return edu_type;
    }

    public void setEdu_type(Integer edu_type) {
        this.edu_type = edu_type;
    }

    public Integer getMaxGiveNumber() {
        return maxGiveNumber;
    }

    public void setMaxGiveNumber(Integer maxGiveNumber) {
        this.maxGiveNumber = maxGiveNumber;
    }

    public Integer getGiveCount() {
        return giveCount;
    }

    public void setGiveCount(Integer giveCount) {
        this.giveCount = giveCount;
    }

    public String getPaperFlag() {
        return paperFlag;
    }

    public void setPaperFlag(String paperFlag) {
        this.paperFlag = paperFlag;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }

    public Integer getForbidTalk() {
        return forbidTalk;
    }

    public void setForbidTalk(Integer forbidTalk) {
        this.forbidTalk = forbidTalk;
    }

    public Integer getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(Integer shareFlag) {
        this.shareFlag = shareFlag;
    }

    public Integer getMeetingOwner() {
        return meetingOwner;
    }

    public void setMeetingOwner(Integer meetingOwner) {
        this.meetingOwner = meetingOwner;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public Integer getMeetingCategory() {
        return meetingCategory;
    }

    public void setMeetingCategory(Integer meetingCategory) {
        this.meetingCategory = meetingCategory;
    }
}
