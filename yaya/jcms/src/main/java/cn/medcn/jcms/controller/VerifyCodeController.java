package cn.medcn.jcms.controller;


import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.geetest.GeetestConfig;
import cn.medcn.common.geetest.GeetestLib;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static cn.medcn.common.utils.APIUtils.success;

/**
 * Created by lixuan on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/web/verify")
public class VerifyCodeController extends BaseController {


    @Autowired
    private JSmsService jSmsService;


    /**
     * 根据手机号码生成一个验证码并发送给用户
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/gencode")
    @ResponseBody
    public String generateCode(String mobile, HttpServletRequest request) {
        String generateCodeKey = "generateCode";
        HttpSession session = request.getSession();
        Long lastGenTime = (Long) session.getAttribute(generateCodeKey);
        if (lastGenTime != null && System.currentTimeMillis() - lastGenTime < 60 * 1000) {
            return APIUtils.error("获取验证码太频繁");
        }
        if(geecheck(request)){
            try {
                String msgId = jSmsService.send(mobile, JSmsService.DEFAULT_TEMPLATE_ID);
                request.getSession().setAttribute(Constants.JSMS_MSG_ID_KEY, msgId);
                session.setAttribute(generateCodeKey, System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
                return APIUtils.error(e.getMessage());
            }
        }else{
            return APIUtils.error("get code error");
        }
        return success();
    }


    @RequestMapping(value = "/geetest")
    @ResponseBody
    public String geetest(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
        String resStr = "{}";
        //自定义userid
        String userid = request.getSession().getId();
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);
        resStr = gtSdk.getResponseStr();
        return resStr;
    }


    private Boolean geecheck(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        //从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");
        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
            //System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            //System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
        }
        // 验证成功
        return gtResult == 1;
    }
}
