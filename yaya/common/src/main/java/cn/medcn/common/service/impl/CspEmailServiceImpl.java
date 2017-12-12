package cn.medcn.common.service.impl;

import cn.medcn.common.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * csp邮件发送实现类
 * Created by lixuan on 2017/12/12.
 */
@Service("cspEmailService")
public class CspEmailServiceImpl extends EmailCommonServiceImpl implements EmailService {

    @Value("${csp_mail_server_host}")
    protected String mailServerHost;

    @Value("${csp_mail_username}")
    protected String mailUserName;

    @Value("${csp_mail_password}")
    protected String mailPassword;

    @Value("${csp_mail_company_name}")
    protected String mailCompanyName;

    /**
     * 获取邮箱服务地址
     *
     * @return
     */
    @Override
    public String getEmailServerHost() {
        return mailServerHost;
    }

    /**
     * 获取邮箱用户名
     *
     * @return
     */
    @Override
    public String getEmailUserName() {
        return mailUserName;
    }

    /**
     * 获取邮箱服务密码
     *
     * @return
     */
    @Override
    public String getEmailPassword() {
        return mailPassword;
    }

    /**
     * 获取邮箱服务端口
     *
     * @return
     */
    @Override
    public int getEmailServerPort() {
        return 25;
    }

    /**
     * 获取公司名称
     *
     * @return
     */
    @Override
    public String getEmailCompanyName() {
        return mailCompanyName;
    }
}
