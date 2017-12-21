import cn.medcn.common.email.EmailHelper;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import com.google.common.collect.Lists;
import org.jdom.JDOMException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by lixuan on 2017/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class EmailTest {

    @Autowired
    private EmailHelper emailHelper;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CspUserService cspUserService;

    @Autowired
    private EmailTempService tempService;

    @Test
    public void testSendVersionUpdateNotification() throws JDOMException, MessagingException, IOException {
        //String[] userNames = {"357488564@qq.com","tongyipeng@medcn.cn","594055335@qq.com","changling_liu@medcn.cn","1031427702@qq.com", "848083860@qq.com","huoyuansui@medcn.cn","zhenghang@medcn.cn", "wucaixiang@medcn.cn","584479243@qq.com","lixuan@medcn.cn"};
        //emailHelper.sendVersionUpdateNotifycation("【YaYa医师升级啦】花60秒更新版本，享受更好的服务", userNames);
        String emailTitle = "【YaYa医师升级啦】花60秒更新版本，享受更好的服务";
        Pageable pageable = new Pageable(1,1000);
        pageable.getParams().put("role_id", 2);
        MyPage<AppUser> page = null;
        int endPageNo = 1000;
        long start = System.currentTimeMillis();
        for(int pageNo = 16; pageNo < endPageNo; pageNo++){
            List<String> emailList = Lists.newArrayList();
            pageable.setPageNum(pageNo);
            page = appUserService.pageUsers(pageable);
            if(page.getDataList() == null || page.getDataList().size() == 0){
                break;
            }
            for (AppUser appUser:page.getDataList()){
                if(appUser.getUsername().contains("@")){
                    emailList.add(appUser.getUsername());
                }
            }
            System.out.println("正在发送第"+pageNo+"批邮件");
            if(!emailList.isEmpty()){
                emailHelper.sendVersionUpdateNotification(emailTitle, emailList);
            }
            System.out.println("发送第 "+ pageNo + "批邮件成功 共"+emailList.size()+"人");
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("操作完成,共耗时= %d 秒", (end-start)/1000));

    }


    @Test
    public void testCheckEmailValidate(){
        String email = "2932923082@qq.com";
       // System.out.println(emailHelper.isAddressValid(email));
    }


    @Test
    public void testSend(){
        String userName = "2932923082@qq.com";
        String emailTitle = "【YaYa医师升级啦】花60秒更新版本，享受更好的服务";
        List<String> emailList = Lists.newArrayList();
        emailList.add(userName);
        emailHelper.sendVersionUpdateNotification(emailTitle, emailList);
    }

    @Test
    public void testEmail() throws SystemException {
        EmailTemplate template = tempService.getTemplate("zh_CN",EmailTemplate.Type.REGISTER.getLabelId(),EmailTemplate.UseType.CSP.getLabelId());
        cspUserService.sendMail("1047923274@qq.com",null,template);
    }
}

