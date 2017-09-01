package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.DoctorReadOnly;

/**
 * Created by Liuchangling on 2017/6/19.
 */
public class AppUserDetail {
    private Integer id;
    // 医院
    private String unitName;
    // 科室
    private String subUnitName;
    // 用户ID
    private Integer userId;
    // 执业资格证号
    private String licence;
    // 职业资格证图片
    private String licenceImg;
    //
    private String major;
    //
    private String allergic;
    // 血型
    private String bloodType;
    // 职称
    private String title;
    // 职位
    private String place;
    // 医院ID
    private Integer hosId;
    // 医院等级
    private String hosLevel;

    private Integer cmeId;

    // 关注公众号奖励象数
    private Integer attentionGiveCredits;

    // 公众号类型 1：医学会  2：医院 3：药企
    private Integer userType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubUnitName() {
        return subUnitName;
    }

    public void setSubUnitName(String subUnitName) {
        this.subUnitName = subUnitName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicenceImg() {
        return licenceImg;
    }

    public void setLicenceImg(String licenceImg) {
        this.licenceImg = licenceImg;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAllergic() {
        return allergic;
    }

    public void setAllergic(String allergic) {
        this.allergic = allergic;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getHosId() {
        return hosId;
    }

    public void setHosId(Integer hosId) {
        this.hosId = hosId;
    }

    public String getHosLevel() {
        return hosLevel;
    }

    public void setHosLevel(String hosLevel) {
        this.hosLevel = hosLevel;
    }

    public Integer getCmeId() {
        return cmeId;
    }

    public void setCmeId(Integer cmeId) {
        this.cmeId = cmeId;
    }

    public Integer getAttentionGiveCredits() {
        return attentionGiveCredits;
    }

    public void setAttentionGiveCredits(Integer attentionGiveCredits) {
        this.attentionGiveCredits = attentionGiveCredits;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public static AppUserDetail build(DoctorReadOnly doctorReadOnly, Integer userId){
        AppUserDetail detail = new AppUserDetail();
        if (doctorReadOnly!=null){
            detail.setUserId(userId);
            detail.setHosId(doctorReadOnly.getD_hid()==null?0:doctorReadOnly.getD_hid().intValue());
            detail.setTitle(doctorReadOnly.getD_zc());
            detail.setPlace(doctorReadOnly.getD_zw());
            detail.setLicence(doctorReadOnly.getD_license());
            detail.setUnitName(doctorReadOnly.getUser_hos());
            detail.setSubUnitName(doctorReadOnly.getUser_ks());
            detail.setHosLevel(doctorReadOnly.getHoslevel());
            detail.setUserType(doctorReadOnly.getPubType());
            detail.setAttentionGiveCredits(doctorReadOnly.getAttentionGiveCredits());
        }
        return detail;
    }
}
