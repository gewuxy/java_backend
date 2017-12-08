package cn.medcn.official.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.email.EmailHelper;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/3/7.
 */
@Controller
@RequestMapping(value="/help")
public class HelpController extends BaseController {

    @Value("${mail_receive_username}")
    private String receiveUserName;

    @Autowired
    private EmailHelper emailHelper;

    @RequestMapping(value="/")
    public String index(){
        return "redirect:/help/solution";
    }

    @RequestMapping(value="/solution")
    public String solution(){
        return "/help/solution";
    }

    @RequestMapping(value="/service")
    public String service(){
        return "/help/service";
    }

    @RequestMapping(value="/feedback", method = RequestMethod.GET)
    public String feedback(){
        return "/help/feedback";
    }

    @RequestMapping(value="/contribute")
    public String contribute(){
        return "/help/contribute";
    }

    @RequestMapping(value="/feedback", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(String content,  String app, String nickName, String qq, String email){
        MailBean mailBean = new MailBean();
        mailBean.setContext(content);
        mailBean.setToEmails(new String[]{receiveUserName});
        mailBean.setSubject("意见反馈 - "+app);
        try {
            mailBean.setContext(emailHelper.formatFeedBack(content, app, nickName, qq, email));
            emailHelper.sendFeedback(mailBean);
        } catch (Exception e) {
            e.printStackTrace();
            return error("发送邮件失败，原因："+e.getMessage());
        }
        return success();
    }

    @RequestMapping(value="/disclaimer")
    public String disclaimer(){
        return "/help/disclaimer";
    }

    @RequestMapping(value="/updatelog")
    public String updatelog(){
        return "/help/updatelog";
    }
}
