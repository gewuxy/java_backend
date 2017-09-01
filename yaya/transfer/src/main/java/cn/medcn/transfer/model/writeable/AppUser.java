package cn.medcn.transfer.model.writeable;


import cn.medcn.transfer.model.readonly.DoctorReadOnly;
import cn.medcn.transfer.utils.StringUtils;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/16.
 */
public class AppUser {

    private Integer id;
    /**用户名*/
    private String username;
    /**昵称*/
    private String nickname;
    /**真实姓名*/
    private String linkman;
    /**用户头像*/
    private String headimg;
    /**联系电话*/
    private String mobile;
    /**密码*/
    private String password;
    /**微信OPENID*/
    private String openid;
    /**历史遗留ID*/
    private Integer oldId;
    /**最后登录时间*/
    private Date lastLoginTime;
    /**最后登录IP*/
    private String lastLoginIp;
    /**是否审核通过*/
    private Boolean authed;
    /**是否是公众号*/
    private Boolean pubFlag;
    /**个性签名*/
    private String sign;
    /**注册时间*/
    private Date registDate;
    /**审核人ID*/
    private Integer authedBy;
    /**是否是推荐公众号 只有公众号才有此属性*/
    private Boolean tuijian;

    private String token;

    /**省份*/
    protected String province;
    /**城市*/
    protected String city;
    /**区*/
    protected String zone;

    /**身份证号*/
    protected String identify;
    /**年龄*/
    protected Integer age;
    /**角色ID*/
    protected Integer roleId;

    protected String address;

    protected String degree;

    protected Integer gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Boolean getAuthed() {
        return authed;
    }

    public void setAuthed(Boolean authed) {
        this.authed = authed;
    }

    public Boolean getPubFlag() {
        return pubFlag;
    }

    public void setPubFlag(Boolean pubFlag) {
        this.pubFlag = pubFlag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public Integer getAuthedBy() {
        return authedBy;
    }

    public void setAuthedBy(Integer authedBy) {
        this.authedBy = authedBy;
    }

    public Boolean getTuijian() {
        return tuijian;
    }

    public void setTuijian(Boolean tuijian) {
        this.tuijian = tuijian;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public static AppUser build(DoctorReadOnly doctorReadOnly,String headimg){
        AppUser appUser = new AppUser();
        if(doctorReadOnly!=null){

            appUser.setId(doctorReadOnly.getD_id().intValue());
            appUser.setOldId(doctorReadOnly.getD_id().intValue());
            appUser.setHeadimg(headimg);
            if(doctorReadOnly.getD_name()!=null && !doctorReadOnly.getD_name().equals("")
                && !doctorReadOnly.getD_name().equals(" ")&& !doctorReadOnly.getD_name().equals("null")){
                appUser.setNickname(doctorReadOnly.getD_name());
                appUser.setLinkman(doctorReadOnly.getD_name());
            }else{
                appUser.setNickname("");
            }
            if (doctorReadOnly.getD_uname()!=null && !doctorReadOnly.getD_uname().equals("")
                && !doctorReadOnly.getD_uname().equals(" ")&& !doctorReadOnly.getD_uname().equals("null")){
                appUser.setUsername(doctorReadOnly.getD_uname());
            }else {
                appUser.setUsername("");
            }
            appUser.setZone(doctorReadOnly.getDistrict());
            appUser.setPassword(doctorReadOnly.getD_password());
           // appUser.setGender((Integer.parseInt(doctorReadOnly.getD_gender())));
            /*if(doctorReadOnly.getD_age()!=null && !doctorReadOnly.getD_age().equals("")
                && !doctorReadOnly.getD_age().equals(" ")&& !doctorReadOnly.getD_age().equals("null")){
                appUser.setAge((Integer.parseInt(doctorReadOnly.getD_age())));
            }else {*/
                appUser.setAge(0);
            //}
            if(doctorReadOnly.getD_degree()!=null && !doctorReadOnly.getD_degree().equals("")
                && doctorReadOnly.getD_degree().equals(" ")&& doctorReadOnly.getD_degree().equals("null")){
                appUser.setDegree(doctorReadOnly.getD_degree());
            }else {
                appUser.setDegree("");
            }
            appUser.setProvince(doctorReadOnly.getD_province());
            appUser.setCity(doctorReadOnly.getD_city());
            appUser.setIdentify(doctorReadOnly.getD_identifynum());
            appUser.setMobile(doctorReadOnly.getD_mobile());
            appUser.setAuthed(true);
            if(doctorReadOnly.getUser_role()==1){
                appUser.setPubFlag(true);
                appUser.setRoleId(1);
            }else {
                appUser.setPubFlag(false);
                appUser.setRoleId(2);
            }
            appUser.setSign(doctorReadOnly.getPubInfo());
            appUser.setRegistDate(doctorReadOnly.getZc_time());
        }
        return appUser;
    }
}
