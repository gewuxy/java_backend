package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/15.
 */
public class DoctorReadOnly {
    private Long d_id;
    private Long d_did;
    private Long d_hid;
    private String d_name;
    private String d_degree;
    private String d_gender;
    private String d_age;
    private String d_zc;
    private String d_password;
    private String d_uname;
    private String d_email;
    private String d_province;
    private String d_license;
    private String d_city;
    private String d_mobile;
    private String permission; //设置 资料权限
    private String d_zw;
    private String d_sign; 		//验证标记 true：验证通过  false：验证不通过
    private Integer d_credit;
    private String d_ic_cardnum;  //IC卡 记录学分
    private String d_identifynum;	//身份证号
    private Date zc_time;   //用户注册时间
    private String user_hos;  //用户自己输入的医院名称  则对应的医院的id为0
    private String user_ks;	//用户自己输入的科室名称  则对应的科室的id为0
    private String nick_name;//用户昵称
    private Integer invite_count;//邀请注册的人数
    private Integer user_role; // 用户角色 公众号 医生
    private String district;

    private String hoslevel; //医院等级
    private String pubInfo; // 公众号简介
    private Integer attentionGiveCredits;//关注公众号奖励象数
    private Integer pubType; // 公众号类型 1：医学会 2：医院 3：企业
    private String linkman; // 联系人

    public Long getD_id() {
        return d_id;
    }

    public void setD_id(Long d_id) {
        this.d_id = d_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getD_did() {
        return d_did;
    }

    public void setD_did(Long d_did) {
        this.d_did = d_did;
    }

    public Long getD_hid() {
        return d_hid;
    }

    public void setD_hid(Long d_hid) {
        this.d_hid = d_hid;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_degree() {
        return d_degree;
    }

    public void setD_degree(String d_degree) {
        this.d_degree = d_degree;
    }

    public String getD_gender() {
        return d_gender;
    }

    public void setD_gender(String d_gender) {
        this.d_gender = d_gender;
    }

    public String getD_age() {
        return d_age;
    }

    public void setD_age(String d_age) {
        this.d_age = d_age;
    }

    public String getD_zc() {
        return d_zc;
    }

    public void setD_zc(String d_zc) {
        this.d_zc = d_zc;
    }

    public String getD_password() {
        return d_password;
    }

    public void setD_password(String d_password) {
        this.d_password = d_password;
    }

    public String getD_uname() {
        return d_uname;
    }

    public void setD_uname(String d_uname) {
        this.d_uname = d_uname;
    }

    public String getD_email() {
        return d_email;
    }

    public void setD_email(String d_email) {
        this.d_email = d_email;
    }

    public String getD_province() {
        return d_province;
    }

    public void setD_province(String d_province) {
        this.d_province = d_province;
    }

    public String getD_license() {
        return d_license;
    }

    public void setD_license(String d_license) {
        this.d_license = d_license;
    }

    public String getD_city() {
        return d_city;
    }

    public void setD_city(String d_city) {
        this.d_city = d_city;
    }

    public String getD_mobile() {
        return d_mobile;
    }

    public void setD_mobile(String d_mobile) {
        this.d_mobile = d_mobile;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getD_zw() {
        return d_zw;
    }

    public void setD_zw(String d_zw) {
        this.d_zw = d_zw;
    }

    public String getD_sign() {
        return d_sign;
    }

    public void setD_sign(String d_sign) {
        this.d_sign = d_sign;
    }

    public Integer getD_credit() {
        return d_credit;
    }

    public void setD_credit(Integer d_credit) {
        this.d_credit = d_credit;
    }

    public String getD_ic_cardnum() {
        return d_ic_cardnum;
    }

    public void setD_ic_cardnum(String d_ic_cardnum) {
        this.d_ic_cardnum = d_ic_cardnum;
    }

    public String getD_identifynum() {
        return d_identifynum;
    }

    public void setD_identifynum(String d_identifynum) {
        this.d_identifynum = d_identifynum;
    }

    public Date getZc_time() {
        return zc_time;
    }

    public void setZc_time(Date zc_time) {
        this.zc_time = zc_time;
    }

    public String getUser_hos() {
        return user_hos;
    }

    public void setUser_hos(String user_hos) {
        this.user_hos = user_hos;
    }

    public String getUser_ks() {
        return user_ks;
    }

    public void setUser_ks(String user_ks) {
        this.user_ks = user_ks;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getInvite_count() {
        return invite_count;
    }

    public void setInvite_count(Integer invite_count) {
        this.invite_count = invite_count;
    }

    public Integer getUser_role() {
        return user_role;
    }

    public void setUser_role(Integer user_role) {
        this.user_role = user_role;
    }

    public String getHoslevel() {
        return hoslevel;
    }

    public void setHoslevel(String hoslevel) {
        this.hoslevel = hoslevel;
    }

    public String getPubInfo() {
        return pubInfo;
    }

    public void setPubInfo(String pubInfo) {
        this.pubInfo = pubInfo;
    }

    public Integer getAttentionGiveCredits() {
        return attentionGiveCredits;
    }

    public void setAttentionGiveCredits(Integer attentionGiveCredits) {
        this.attentionGiveCredits = attentionGiveCredits;
    }

    public Integer getPubType() {
        return pubType;
    }

    public void setPubType(Integer pubType) {
        this.pubType = pubType;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
}
