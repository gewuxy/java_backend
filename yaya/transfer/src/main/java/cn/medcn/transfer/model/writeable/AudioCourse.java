package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PptReadOnly;
import cn.medcn.transfer.utils.DateUtils;

import java.util.*;

/**
 * Created by lixuan on 2017/6/16.
 */
public class AudioCourse {

    private Integer id;

    private String title;
    /**资源类型*/
    private String category;
    /**是否发布*/
    private Boolean published;

    private Date createTime;
    /**所有者ID*/
    private Integer owner;
    /**是否共享*/
    private Boolean shared;
    /**共享类型 0表示免费 1表示收费 2表示奖励*/
    private Integer shareType;
    /**转载需要的象数*/
    private Integer credits;
    /**原始资料ID 转载来的资源才有的属性 非转载资源为0*/
    private Integer primitiveId;

    private List<AudioCourseDetail> details;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getPrimitiveId() {
        return primitiveId;
    }

    public void setPrimitiveId(Integer primitiveId) {
        this.primitiveId = primitiveId;
    }

    public List<AudioCourseDetail> getDetails() {
        return details;
    }

    public void setDetails(List<AudioCourseDetail> details) {
        this.details = details;
    }


    public static AudioCourse build(MeetSourceReadOnly source, List<PptReadOnly> pptList, Integer owerId){
        AudioCourse course = new AudioCourse();
        course.setId(source.getMeetingId().intValue());
        course.setTitle(source.getMeetingName());
        course.setCategory(source.getKind());
        course.setCreateTime(DateUtils.parseDate(source.getStartTime()));
        course.setShared(false);
        course.setPrimitiveId(0);
        course.setOwner(owerId);
        course.setPublished(true);
        //处理明细
//        course.setDetails(new ArrayList<AudioCourseDetail>());
//        Map<String, List<PptReadOnly>> map = new HashMap<>();
//        if(pptList != null && pptList.size() > 0){
//            for(PptReadOnly ppt : pptList){
//                if(map.get(ppt.getMsgGroupid()) != null){
//                    map.get(ppt.getMsgGroupid()).add(ppt);
//                }else{
//                    map.put(ppt.getMsgGroupid(), new ArrayList<PptReadOnly>());
//                    map.get(ppt.getMsgGroupid()).add(ppt);
//                }
//            }
//        }
//        List<AudioCourseDetailSupport> supports = new ArrayList<>();
//        for(String key : map.keySet() ){
//            AudioCourseDetailSupport support = new AudioCourseDetailSupport();
//            support.setMsgGroupId(Integer.parseInt(key));
//            support.setPptList(map.get(key));
//            supports.add(support);
//        }
//        List<AudioCourseDetail> details = AudioCourseDetail.build(supports);
        return course;
    }

}
