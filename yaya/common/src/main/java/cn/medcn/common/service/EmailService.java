package cn.medcn.common.service;

import cn.medcn.common.email.MailBean;

/**
 * Created by lixuan on 2017/12/12.
 */
public interface EmailService {
    /**
     * 获取邮箱服务地址
     * @return
     */
    String getEmailServerHost();

    /**
     * 获取邮箱用户名
     * @return
     */
    String getEmailUserName();

    /**
     * 获取邮箱服务密码
     * @return
     */
    String getEmailPassword();

    /**
     * 获取邮箱服务端口
     * @return
     */
    int getEmailServerPort();

    /**
     * 获取公司名称
     * @return
     */
    String getEmailCompanyName();

    /**
     * 发送邮件
     * @param mailBean
     */
    void send(MailBean mailBean);
}
