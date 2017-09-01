package cn.medcn.jcms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.BaiduApiService;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.supports.baidu.NearbySearchDTO;
import cn.medcn.common.supports.baidu.SearchResultDTO;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.common.utils.RegexUtils;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.TitleDTO;
import cn.medcn.user.model.*;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.HospitalService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by lixuan on 2017/5/15.
 */
@Controller
@RequestMapping(value="/register")
public class RegisterController extends BaseController {


    @Autowired
    private AppUserService appUserService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private SystemRegionService systemRegionService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private JSmsService jSmsService;

    @Autowired
    private BaiduApiService baiduApiService;



    /**
     * 注册时生成短信验证码给前端
     * @param mobile
     * @return
     */
    @RequestMapping("/get_captcha")
    @ResponseBody
    public String getCode(String mobile){

        if(!RegexUtils.checkMobile(mobile)){
            return error("手机格式不正确");
        }

        AppUser user = new AppUser();
        user.setMobile(mobile);

        //10分钟内最多允许获取3次验证码
        Captcha captcha = (Captcha)redisCacheUtils.getCacheObject(mobile);
        if(captcha == null){ //第一次获取
            String msgId = null;
            try {
                msgId = jSmsService.send(mobile, Constants.DEFAULT_TEMPLATE_ID);
            } catch (Exception e) {
                return error("发送短信失败");
            }
            Captcha firstCaptcha = new Captcha();
            firstCaptcha.setFirstTime(new Date());
            firstCaptcha.setCount(0);
            firstCaptcha.setMsgId(msgId);
            redisCacheUtils.setCacheObject(mobile,firstCaptcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME);
            return success();

        }else {
            Long between = System.currentTimeMillis() - captcha.getFirstTime().getTime();
            if(captcha.getCount() == 2 && between < 10*60*1000){
                return error("获取验证码次数频繁，请稍后");
            }
            String msgId = null;
            try {
                msgId = jSmsService.send(mobile, Constants.DEFAULT_TEMPLATE_ID);
            } catch (Exception e) {
                return error("发送短信失败");
            }
            captcha.setMsgId(msgId);
            captcha.setCount(captcha.getCount() + 1);
            redisCacheUtils.setCacheObject(mobile,captcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME);
        }

        return success();
    }


    /**
     * 获取职称
     * @return
     */
    @RequestMapping("/title")
    @ResponseBody
    public String getTitleList(){
        List<TitleDTO> dtoList = new ArrayList<>();
        List<String> gradeList = new ArrayList<>();
        gradeList.add("医师");
        gradeList.add("药师");
        gradeList.add("护师");
        gradeList.add("技师");

        TitleDTO dto1 = new TitleDTO();
        dto1.setTitle("高级职称");
        dto1.setGrade(gradeList);

        TitleDTO dto2 = new TitleDTO();
        dto2.setTitle("中级职称");
        dto2.setGrade(gradeList);

        TitleDTO dto3 = new TitleDTO();
        dto3.setTitle("初级职称");
        dto3.setGrade(gradeList);

        TitleDTO dto4 = new TitleDTO();
        dto4.setTitle("其他");
        List<String> list = new ArrayList<>();
        list.add("其他职称");
        dto4.setGrade(list);

        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        dtoList.add(dto4);
        return APIUtils.success(dtoList);
    }


    @RequestMapping("/specialties")
    @ResponseBody
    public String specialty(){
        List<Department> list = hospitalService.findAllDepart();
        Map<String,List<String>> map = new HashMap<>();
        for(Department department:list){
            List<String> nameList = new ArrayList<>();
            map.put(department.getCategory(),nameList);
        }
        Set<String> set = map.keySet();
        for(String category:set){
            for(Department department:list){
                if(category.equals(department.getCategory())){
                    map.get(category).add(department.getName());
                }
            }
        }
        return APIUtils.success(map);
    }

    /**
     * 获取附近医院
     * @param searchDTO
     * @return
     */
    @RequestMapping(value = "/nearby/hospital")
    @ResponseBody
    public String nearbyHospital(NearbySearchDTO searchDTO){
        searchDTO.setQuery(NearbySearchDTO.QUERY_HOSPITAL+","+searchDTO.getQuery());
        SearchResultDTO searchResult = baiduApiService.search(searchDTO);
        return success(searchResult);
    }

    @RequestMapping(value = "/regions")
    @ResponseBody
    public String allRegion(){
        List<SystemRegion> regions = systemRegionService.findAll();
        List<SystemRegion> result = Lists.newArrayList();
        for(SystemRegion region : regions){
            if (region.getLevel() == 1){
                setSubList(region, regions);
                result.add(region);
            }
        }

        return success(result);
    }

    /**
     * 校验有无邀请码
     * @param masterId
     * @param model
     * @return
     */
    @RequestMapping(value="/reg", method = RequestMethod.GET)
    public String reg(Integer[] masterId,Model model){
        if(masterId == null){
            return "/regist/register";
        }
        List<String> masterList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        for(Integer id:masterId){
            AppUser master = appUserService.selectByPrimaryKey(id);
            if(master != null){
                ActiveStore store = appUserService.getActiveStore(id);
                if(store != null && store.getStore() > Constants.NUMBER_ZERO){
                    idList.add(master.getId());
                    masterList.add(master.getNickname());
                }

            }
        }
            if(masterList.size() == Constants.NUMBER_ZERO ){

                return "/regist/registerFail";
            }
                model.addAttribute("nameList",masterList);
                model.addAttribute("masterId",idList);
                return "/regist/register";
    }






    @RequestMapping(value="/do_register", method = RequestMethod.POST)
    @ResponseBody
    public String register(AppUserDTO dto,String captcha,Integer[] masterId,String invite) {

            String data = checkData(dto,captcha,invite,masterId);
            if( data != null){
                return data;
            }
        AppUser user = AppUserDTO.rebuildToDoctor(dto);
        AppUser searchUser = new AppUser();
        searchUser.setMobile(user.getMobile());
        if(appUserService.selectOne(searchUser) != null){
            return error("该用户已存在");
        }
        //设置属性
        user.setRoleId(AppRole.AppRoleType.DOCTOR.getId());
        if(StringUtils.isEmpty(user.getNickname())){
            user.setNickname(user.getLinkman());
        }
        user.setRegistDate(new Date());
        user.setPubFlag(false);
        Captcha captcha1 = (Captcha)redisCacheUtils.getCacheObject(dto.getMobile());
        try {
            if(!jSmsService.verify(captcha1.getMsgId(),captcha)){
                return error("验证码不正确");
            }
        } catch (Exception e) {
            return error("验证码已被校验，请重新获取验证码");
        }
        //执行注册
        try{
            //检查是否是测试用邀请码,测试邀请码返回false
            if(hadCheckInvite(dto.getHospital(), invite)){
                appUserService.executeRegist(user, invite,masterId);//真实用户注册
            }else{
                appUserService.executeRegist(user, null,null);  //测试用户注册
            }
        }catch (Exception e){
            return error(e.getMessage());
        }
        return success();

    }

    /**
     * 检查是否是测试用邀请码,测试邀请码返回false
     * @param hosName
     * @param invite
     * @return
     */
    private boolean hadCheckInvite(String hosName, String invite){
        if(Constants.DEFAULT_HOS_NAME.equals(hosName) && Constants.DEFAULT_INVITE.equals(invite)){
            return false;
        }
        return true;
    }



    private String checkData(AppUserDTO dto,String captcha,String invite,Integer[] masterId){

        if(StringUtils.isEmpty(dto.getMobile())){
            return error("手机号码不能为空");
        }
        if(!RegexUtils.checkMobile(dto.getMobile())){
            return error("手机格式不正确");
        }

        if(StringUtils.isEmpty(captcha)){
            return error("验证码不能为空");
        }

        if(StringUtils.isEmpty(dto.getPassword())){
            return error("密码不能为空");
        }
        if(StringUtils.isEmpty(dto.getLinkman())){
            return error("真实姓名不能为空");
        }

        if(StringUtils.isEmpty(dto.getProvince())){
            return error("省份不能为空");
        }

        if(StringUtils.isEmpty(dto.getCity())){
            return error("城市不能为空");
        }

        if(StringUtils.isEmpty(dto.getHospital())){
            return error("医院不能为空");
        }
        if(StringUtils.isEmpty(dto.getCategory()) || StringUtils.isEmpty(dto.getName())){
            return error("专科名称不能为空");
        }

        if(StringUtils.isEmpty(dto.getTitle())){
            return error("职称不能为空");
        }

        if(StringUtils.isEmpty(invite) && StringUtils.isEmpty(masterId)){
            return error("请提供激活码");
        }

        return null;
    }




//    @RequestMapping(value = "/checkUser")
//    @ResponseBody
//    public String checkUser(String username){
//        AppUser searchUser = new AppUser();
//        searchUser.setUsername(username);
//        if(appUserService.selectOne(searchUser) != null){
//            return error("用户名已被使用");
//        }
//        return success();
//    }




//    @RequestMapping(value="/cities",method = RequestMethod.POST)
//    @ResponseBody
//    public String cities(String  province) throws UnsupportedEncodingException {
//        province = URLDecoder.decode(province, "UTF-8");
//        if(province == null){
//            return error("省份不能为空");
//        }
//        List<SystemRegion> list = systemRegionService.findRegionByPreName(province);
//        return success(list);
//    }
//
//    @RequestMapping(value="/zones",method = RequestMethod.POST)
//    @ResponseBody
//    public String zones(String city) throws UnsupportedEncodingException {
//        city = URLDecoder.decode(city, "UTF-8");
//        if(city == null){
//            return error("城市不能为空");
//        }
//        List<SystemRegion> list = systemRegionService.findRegionByPreName(city);
//        return success(list);
//    }
//


//    /**
//     * 根据省市获取医院
//     * @param province
//     * @param city
//     * @return
//     */
//    @RequestMapping(value = "/hospital",method = RequestMethod.POST)
//    @ResponseBody
//    public String getHospitalByCity(String province, String city) throws UnsupportedEncodingException {
//        Map<String,Object> params = new HashMap();
//        province = URLDecoder.decode(province, "UTF-8");
//        city = URLDecoder.decode(city, "UTF-8");
//        params.put("province",province);
//        params.put("city",city);
//        List<Hospital> hosList = appUserService.findHospitalByCity(params);
//        return success(hosList);
//    }


    /**
     * 获取科室列表
     * @return
     */
    @RequestMapping(value="/department")
    @ResponseBody
    public String getSubUnitList(){
        List<Department> list = hospitalService.findAllDepart();
        return success(list);
    }


    @RequestMapping("/registSuccess")
    public String registSuccess(String mobile,Model model){
        model.addAttribute("mobile",mobile);
        return "/regist/register_success";
    }

//    @RequestMapping("/checkMobile")
//    @ResponseBody
//    public String checkMobile(String mobile){
//        AppUser user = new AppUser();
//        user.setMobile(mobile);
//        int count = appUserService.selectCount(user);
//        if(count > 0){
//            return error("该手机已被注册，请使用新手机号码");
//        }
//        return success();
//    }


    private void setSubList(SystemRegion region, List<SystemRegion> regions){
        for(SystemRegion sub:regions){
            if(region.getId().intValue() == sub.getPreId().intValue()){
                if(region.getLevel() < 3){
                    setSubList(sub, regions);
                }
                region.getDetails().add(sub);
            }
        }
    }
}
