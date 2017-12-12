package cn.medcn.common.service.impl;

import cn.medcn.common.email.MailBean;
import cn.medcn.common.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;


/**
 * Created by lixuan on 2017/12/12.
 */
public abstract class EmailCommonServiceImpl implements EmailService {



    /**
     * 发送邮件
     *
     * @param mailBean
     */
    @Override
    public void send(MailBean mailBean) {
        Session session = Session.getDefaultInstance(getEmailProperties());
        Transport transport = null;

        try {
            transport = session.getTransport();

            transport.connect(getEmailUserName(), getEmailPassword());
            MimeMessage message = createMimeMessage(session, mailBean);

            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected InternetAddress[] emailsToAddressArray(String[] emails){
        InternetAddress[] addresses = new InternetAddress[emails.length];
        for(int i=0; i< emails.length; i++){
            try {
                addresses[i] = new InternetAddress(emails[i], emails[i], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return addresses;
    }

    protected MimeMessage createMimeMessage(Session session, MailBean mailBean) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(getEmailUserName(), getEmailCompanyName(), "UTF-8"));
        message.setRecipients(MimeMessage.RecipientType.TO, emailsToAddressArray(mailBean.getToEmails()));
        message.setSubject(mailBean.getSubject(), "UTF-8");
        message.setContent(mailBean.getContext(), "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    protected Properties getEmailProperties() {
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", getEmailServerHost());   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        return props;
    }
}
