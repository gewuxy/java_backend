package cn.medcn.common.email;

import cn.medcn.common.utils.LocalUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lixuan on 2017/1/10.
 */
@Component
public class CSPEmailHelper {
    private static final Log log = LogFactory.getLog(CSPEmailHelper.class);



    private String REPLACE_HOLDER_USER = "@user";
    private String REPLACE_HOLDER_URL = "@url";
    private String REPLACE_HOLDER_DATE = "@date";



    private MimeMessage createMimeMessage(MailBean mailBean,JavaMailSender sender) throws  UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
        messageHelper.setSubject(mailBean.getSubject());
        messageHelper.setTo(mailBean.getToEmails());
        messageHelper.setText(mailBean.getContext(), true);
        return mimeMessage;
    }





    public void sendMail(String url,String emailContent,JavaMailSender sender,MailBean bean) throws JDOMException, IOException, MessagingException {
        bean.setContext(formatContent(bean,url,emailContent));
        sender.send(createMimeMessage(bean,sender));

    }

    public String formatContent(MailBean bean, String url, String emailContent) throws JDOMException, IOException {
        String username = bean.getToEmails()[0];
        String local = bean.getLocalStr();
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_USER, StringUtils.isEmpty(username)?" -- ":username);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_URL, StringUtils.isEmpty(url)?" -- ":url);
        DateFormat format = null;
        //转换英文日期
       if(LocalUtils.Local.en_US.name().equals(local)){
             format = new SimpleDateFormat("MMM.d,yyyy",Locale.ENGLISH);
       }else{
             format= new SimpleDateFormat("yyyy年MM月dd日");
        }
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_DATE,format.format(new Date()));
        return emailContent;
    }



}
