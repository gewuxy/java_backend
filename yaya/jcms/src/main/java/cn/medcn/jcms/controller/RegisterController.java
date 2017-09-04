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

        return appUserService.sendCaptcha(mobile);
    }


    /**
     * 获取职称列表
     * @return
     */
    @RequestMapping("/title")
    @ResponseBody
    public String getTitleList(){
        List<TitleDTO> list =  appUserService.getTitle();
        return success(list);
    }


    @RequestMapping("/specialties")
    @ResponseBody
    public String specialty(){
        //获取医院科室，用map接收，科室第一级为key,科室第二级为value
        Map<String,List<String>> map =  hospitalService.getAllDepart();
        return success(map);
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
        List<SystemRegion> result = systemRegionService.getPCZRelationList();

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
        //执行测试用户和正式用户的注册流程,医院名:敬信药草园,邀请码:2603 为测试用户注册
        String err = appUserService.executeUserRegister(dto, invite, masterId, user);
        if (err != null){
            return err;
        }
        return success();

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

}
