package cn.medcn.user.dto;

import cn.medcn.common.supports.Validate;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.user.model.AppDoctor;
import cn.medcn.user.model.AppRole;
import cn.medcn.user.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import cn.medcn.sys.model.SystemProperties;

import java.util.Date;

/**
 * Created by lixuan on 2017/5/3.
 */
@Data
@NoArgsConstructor
public class AppUserDTO {

    private Integer id;
    @Validate(filedName = "用户名", notNull = true, notBlank = true, maxLength = 50)
    private String username;
    @Validate(filedName = "密码", notNull = true, notBlank = true, maxLength = 24)
    private String password;

    private String nickname;

    //微信昵称
    private String wxNickname;

    private String linkman;

    private String headimg;
    @Validate(filedName = "密码", maxLength = 11, pattern = "^[\\d]{11}")
    private String mobile;
    @Validate(filedName = "省份", notBlank = true)
    private String province;
    @Validate(filedName = "城市", notBlank = true)
    private String city;

    private String zone;

    private String licence;

    private String major;

    private String place;

    private String title;

    //医院名称
    @Validate(filedName = "医院", notBlank = true)
    private String hospital;

    //科室名称
    private String department;

    //专科一级名称
    @Validate(filedName = "专科", notBlank = true)
    private String category;

    //专科二级名称
    @Validate(filedName = "专科", notBlank = true)
    private String name;


    private String licenceImg;

    private String address;

    private String token;

    private Integer gender;

    private String degree;

    private String cmeId;
    /**医院级别*/
    private String hosLevel;

    //医院等级,1:一级，2:二级，3:三级，4:社区服务中心(站),5:卫生院,6:诊所,7:其他
    private Integer hospitalLevel;

    private Integer credits;

    private SystemProperties systemProperties;

    //个人资料完整度
    private Integer integrity ;

    private Date registerDate;

    public static AppUserDTO buildFromDoctor(AppUser user){
        AppUserDTO dto = new AppUserDTO();
        if(user!=null){
            dto.setId(user.getId());
            dto.setNickname(user.getNickname());
            dto.setCity(user.getCity());
            dto.setAddress(user.getAddress());
            dto.setLinkman(user.getLinkman());
            dto.setMobile(user.getMobile());
            dto.setProvince(user.getProvince());
            dto.setHeadimg(user.getHeadimg());
            dto.setUsername(user.getUsername());
            dto.setToken(user.getToken());
            dto.setZone(user.getZone());
            dto.setGender(user.getGender());
            dto.setDegree(user.getDegree());
            dto.setCredits(user.getCredits());
            if(user.getUserDetail() != null){
                AppDoctor doctor = (AppDoctor) user.getUserDetail();
                dto.setHospital(doctor.getUnitName());
                dto.setDepartment(doctor.getSubUnitName());
                dto.setLicence(doctor.getLicence());
                dto.setMajor(doctor.getMajor());
                dto.setTitle(doctor.getTitle());
                dto.setPlace(doctor.getPlace());
                dto.setCmeId(doctor.getCmeId());
                dto.setHosLevel(doctor.getHosLevel());
                if(!CheckUtils.isEmpty(doctor.getSpecialtyName())){
                    String[] arr = doctor.getSpecialtyName().split(" ");
                    dto.setCategory(arr.length>0?arr[0]:null);
                    dto.setName(arr.length>1?arr[1]:null);
                }
            }
        }
        return dto;
    }

    public static AppUser rebuildToDoctor(AppUserDTO dto){
        AppUser user = new AppUser();
        if(dto != null){
            user.setId(dto.getId());
            user.setNickname(dto.getNickname());
            user.setMobile(dto.getMobile());
            user.setHeadimg(dto.getHeadimg());
            user.setAddress(dto.getAddress());
            user.setProvince(dto.getProvince());
            user.setCity(dto.getCity());
            user.setLinkman(dto.getLinkman());
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setToken(dto.getToken());
            user.setDegree(dto.getDegree());
            user.setGender(dto.getGender());
            user.setZone(dto.getZone());
            user.setRoleId(AppRole.AppRoleType.DOCTOR.getId());
            AppDoctor doctor = new AppDoctor();
            doctor.setUserId(dto.getId());
            doctor.setCmeId(dto.getCmeId());
            doctor.setHosLevel(dto.getHosLevel());
            doctor.setMajor(dto.getMajor());
            doctor.setPlace(dto.getPlace());
            doctor.setTitle(dto.getTitle());
            doctor.setLicence(dto.getLicence());
            doctor.setUnitName(dto.getHospital());
            doctor.setSubUnitName(dto.getDepartment());
            doctor.setLicenceImg(dto.getLicenceImg());
            if(!StringUtils.isEmpty(dto.getCategory()) && !StringUtils.isEmpty(dto.getName())){
                doctor.setSpecialtyName(dto.getCategory()+" "+dto.getName());
            }
            user.setUserDetail(doctor);
        }
        return user;
    }

    public void setIntegrity(){
        integrity = 0;

        if(!StringUtils.isEmpty(headimg)){

            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(linkman)){
            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(hospital) && !StringUtils.isEmpty(hosLevel)){
            this.integrity += 10;
        }

        if(!StringUtils.isEmpty(department)){
            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city)){
            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(category) && !StringUtils.isEmpty(name)){
            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(cmeId)){
            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(licence)){
            this.integrity += 10;
        }

        if(!StringUtils.isEmpty(title)){
            this.integrity += 10;
        }
        if(!StringUtils.isEmpty(major)){
            this.integrity += 10;
        }

    }

    public Integer getIntegrity(){
        int itg = 0;
        if(!StringUtils.isEmpty(headimg)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(linkman)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(hospital) && !StringUtils.isEmpty(hosLevel)){
            itg += 10;
        }

        if(!StringUtils.isEmpty(department)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(category) && !StringUtils.isEmpty(name)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(cmeId)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(licence)){
            itg += 10;
        }

        if(!StringUtils.isEmpty(title)){
            itg += 10;
        }
        if(!StringUtils.isEmpty(major)){
            itg += 10;
        }
        return itg;
    }




}
