package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.jbms.dto.AppDebugDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by lixuan on 2017/5/4.
 */
@Controller
@RequestMapping(value="/sys/app")
public class AppController extends BaseController {

    @Value("${app.yaya.base}")
    private String yayaBaseUrl;

    @RequestMapping(value="/debug")
    public String debug(Integer type, Model model){
        if(type == null){
            type = 0;
        }
        if(type == 0){
            model.addAttribute("baseUrl", yayaBaseUrl);
        }
        return "/sys/appDebug";
    }


    @RequestMapping(value="/doDebug", method = RequestMethod.POST)
    @ResponseBody
    public String doDebug(AppDebugDTO dto){
        String result = null;
        Map<String, Object> headers = Maps.newHashMap();
        if(dto.getToken() != null){
            headers.put("token", dto.getToken());
        }
        if("getMethod".equals(dto.getType())){
            JSONObject jsonObject = JSON.parseObject(dto.getParams());
            result = HttpUtils.get(dto.getUrl(),jsonObject, headers);
        }else if("postMethod".equals(dto.getType())){
            JSONObject jsonObjectPost = JSON.parseObject(dto.getParams());
            result = HttpUtils.post(dto.getUrl(),jsonObjectPost, headers);
        }
        return result;
    }
}
