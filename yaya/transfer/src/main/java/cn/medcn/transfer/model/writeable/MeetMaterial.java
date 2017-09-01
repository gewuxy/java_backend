package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetMaterialReadOnly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/21.
 */
public class MeetMaterial {

    private Integer id;

    private String name;

    private String fileType;

    private Integer userId;

    private Date createTime;

    private Long fileSize;

    private String meetId;

    private String fileUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public static MeetMaterial build(MeetMaterialReadOnly meetMaterialReadOnly, Meet meet, Long fileSize, String fileUrl){
        MeetMaterial meetMaterial = new MeetMaterial();
        if(meetMaterialReadOnly != null){
            meetMaterial.setName(meetMaterialReadOnly.getMaterialName());
            meetMaterial.setCreateTime(meet.getCreateTime());
            meetMaterial.setFileSize(fileSize);
            meetMaterial.setMeetId(meet.getId());
            meetMaterial.setFileType(meetMaterialReadOnly.getMaterialType());
            meetMaterial.setUserId(meet.getOwnerId());
            meetMaterial.setFileUrl(fileUrl);
        }
        return meetMaterial;
    }
}
