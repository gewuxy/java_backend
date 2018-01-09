package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.weixin.config.MiniProgramConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序接口
 * Created by LiuLP on 2018/1/9/009.
 */
@Controller
@RequestMapping("/mini/")
public class MiniProgramController extends BaseController {


    @Value("${mini.appid}")
    private String appId;

    @Value("${mini.secret}")
    private String secret;



    @RequestMapping("/get/unionid")
    @ResponseBody
    public String getUnionId(String code){
        if(StringUtils.isEmpty(code)){
            return error("code不能为空");
        }
        Map<String,Object> map = new HashMap<>();
        map.put(MiniProgramConfig.MINI_APPID_KEY,appId);
        map.put(MiniProgramConfig.MINI_SECRET_KEY,secret);
        map.put(MiniProgramConfig.JS_CODE_KEY,code);
        map.put(MiniProgramConfig.GRANT_TYPE_KEY,MiniProgramConfig.GRANT_TYPE_VALUE);
        String result = HttpUtils.get(MiniProgramConfig.UNIONID_URL,map);
        return success(result);
    }

}
