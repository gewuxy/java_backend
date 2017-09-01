package cn.medcn.common.email;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.LogUtils;
import cn.medcn.common.utils.XMLUtils;
import com.sun.mail.smtp.SMTPTransport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSendException;
import org.springframework.util.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by lixuan on 2017/1/10.
 */
@Component
public class EmailHelper {
    private static final Log log = LogFactory.getLog(EmailHelper.class);

    @Value("${mail_company_name}")
    private String mailCompanyName;

    @Value("${mail_username}")
    private String mailUserName;

    private String REPLACE_HOLDER_CONTENT = "@content";
    private String REPLACE_HOLDER_PRODUCT = "@product";
    private String REPLACE_HOLDER_NICKNAME = "@nickName";
    private String REPLACE_HOLDER_USER = "@user";
    private String REPLACE_HOLDER_URL = "@url";
    private String REPLACE_HOLDER_QQ = "@qq";
    private String REPLACE_HOLDER_EMAIL = "@email";
    private String REPLACE_HOLDER_DATE = "@date";

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送
     * @param mailBean
     */
    public void sendFeedback(MailBean mailBean) throws UnsupportedEncodingException, MessagingException {
        mailBean.setFrom(mailUserName);
        mailBean.setFromName(mailCompanyName);
        javaMailSender.send(createMimeMessage(mailBean));
    }


    public void sendRestPwd(MailBean mailBean, String email, String url) throws IOException, MessagingException, JDOMException {
        mailBean.setFrom(mailUserName);
        mailBean.setFromName(mailCompanyName);
        mailBean.setContext(formatPwdReset(email, url));
        mailBean.setToEmails(new String[]{email});
        javaMailSender.send(createMimeMessage(mailBean));
    }

    /**
     * 发送重置密码或者你绑定邮箱邮件
     * @param mailBean
     * @param email
     * @param url
     * @param type
     * @throws IOException
     * @throws MessagingException
     * @throws JDOMException
     */
    public void sendMailByType(MailBean mailBean, String email, String url,Integer type) throws IOException, MessagingException, JDOMException {
        mailBean.setFrom(mailUserName);
        mailBean.setFromName(mailCompanyName);
        mailBean.setContext(formatByType(email, url,type));
        mailBean.setToEmails(new String[]{email});
        javaMailSender.send(createMimeMessage(mailBean));
    }


    private MimeMessage createMimeMessage(MailBean mailBean) throws MessagingException, UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
        messageHelper.setSubject(mailBean.getSubject());
        messageHelper.setTo(mailBean.getToEmails());
        messageHelper.setText(mailBean.getContext(), true);
        return mimeMessage;
    }


    public String formatFeedBack(String content, String app,String nickName, String qq, String email) throws JDOMException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("email.xml");
        String emailContent = XMLUtils.doParseXML(inputStream,"feedback", "load");
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_CONTENT, StringUtils.isEmpty(content)?" -- ":content);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_PRODUCT, StringUtils.isEmpty(app)?" -- ":app);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_NICKNAME, StringUtils.isEmpty(nickName)?" -- ":nickName);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_QQ, StringUtils.isEmpty(qq)?" -- ":qq);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_EMAIL, StringUtils.isEmpty(email)?" -- ":email);
        DateFormat format= new SimpleDateFormat("yyyy年MM月dd日");
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_DATE,format.format(new Date()));
        return emailContent;
    }


    public String formatPwdReset(String username, String url) throws JDOMException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("email.xml");
        String emailContent = XMLUtils.doParseXML(inputStream,"pwdRest", "load");
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_USER, StringUtils.isEmpty(username)?" -- ":username);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_URL, StringUtils.isEmpty(url)?" -- ":url);
        DateFormat format= new SimpleDateFormat("yyyy年MM月dd日");
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_DATE,format.format(new Date()));
        return emailContent;
    }

    public String formatByType(String username, String url,Integer type) throws JDOMException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("email.xml");
        String emailContent = null;
        if(type == Constants.NUMBER_ZERO){
             emailContent = XMLUtils.doParseXML(inputStream,"pwdRest", "load");
        }else if(type == Constants.NUMBER_ONE){
            emailContent = XMLUtils.doParseXML(inputStream,"bindAccount", "load");
        }
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_USER, StringUtils.isEmpty(username)?" -- ":username);
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_URL, StringUtils.isEmpty(url)?" -- ":url);
        DateFormat format= new SimpleDateFormat("yyyy年MM月dd日");
        emailContent = emailContent.replaceAll(REPLACE_HOLDER_DATE,format.format(new Date()));
        return emailContent;
    }



    public void sendVersionUpdateNotification(String subject, List<String> userNames) {
        MailBean mailBean = new MailBean();
        mailBean.setFrom(mailUserName);
        mailBean.setFromName(mailCompanyName);
        mailBean.setSubject(subject);
        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("email.xml");
            mailBean.setContext(XMLUtils.doParseXML(inputStream, "versionUpdateNotify", "load"));
            for(String email:userNames){
                    mailBean.setToEmails(new String[]{email});
                    try {
                        javaMailSender.send(createMimeMessage(mailBean));
                        LogUtils.debug(log, email+" 发送成功!!");
                    } catch (MessagingException e) {
                        LogUtils.error(log, email+" 发送邮件失败!");
                    } catch (MailSendException e){
                        LogUtils.error(log, email+" 发送邮件失败!");
                    }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
