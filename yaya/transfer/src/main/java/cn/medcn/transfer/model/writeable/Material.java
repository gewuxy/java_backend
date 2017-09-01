package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.PubUserMaterialReadOnly;
import cn.medcn.transfer.utils.StringUtils;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class Material {
    private String id;

    private  String materialName;

    private String materialType;

    private String materialUrl;

    private String materialDesc;

    // 是否需要支付象数
    private Boolean needXs;

    // 需要支付的象数
    private Integer xsCost;

    //时长 音视频资料需填写时长属性
    private Integer duration;

    private String infinityId;

    private Integer userId;

    private Date createTime;

    private Long fileSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public Boolean getNeedXs() {
        return needXs;
    }

    public void setNeedXs(Boolean needXs) {
        this.needXs = needXs;
    }

    public Integer getXsCost() {
        return xsCost;
    }

    public void setXsCost(Integer xsCost) {
        this.xsCost = xsCost;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getInfinityId() {
        return infinityId;
    }

    public void setInfinityId(String infinityId) {
        this.infinityId = infinityId;
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

    public static Material build(PubUserMaterialReadOnly pubUserMaterialReadOnly){
        Material material = new Material();
        material.setId(StringUtils.getNowStringID());
        material.setInfinityId("0");
        material.setUserId(pubUserMaterialReadOnly.getPub_user_id());
        material.setMaterialName(pubUserMaterialReadOnly.getFile_name());
        material.setMaterialType(pubUserMaterialReadOnly.getFile_type());

        String filesize = pubUserMaterialReadOnly.getFile_size();

        Float s ;
        String filesz = filesize.substring(0,filesize.length()-1) ;
        s = Float.parseFloat(filesz);
        if (filesize!=null && filesize.endsWith("K")){
            s = s * 1024;
        }else if (filesize.endsWith("M")){
            s = s * 1024 * 1024;
        }
        long size = Math.round(s);
        material.setFileSize(size);
        material.setMaterialUrl(pubUserMaterialReadOnly.getFile_url());
        if(pubUserMaterialReadOnly.getPay_type()==0){
            material.setNeedXs(false);
        }else if (pubUserMaterialReadOnly.getPay_type()==1){
            material.setNeedXs(true);
        }else if (pubUserMaterialReadOnly.getPay_type()==2){
            material.setNeedXs(true);
        }
        material.setXsCost(pubUserMaterialReadOnly.getPay_credits());
        return material;
    }

}
