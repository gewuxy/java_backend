package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.email.CSPEmailHelper;
import cn.medcn.common.email.MailBean;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.service.SMSService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.sys.dao.SystemNotifyDAO;
import cn.medcn.user.dao.BindInfoDAO;
import cn.medcn.user.dao.CspUserInfoDAO;
import cn.medcn.user.dao.EmailTemplateDAO;
import cn.medcn.user.dao.UserFluxDAO;
import cn.medcn.user.dto.Captcha;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.VideoLiveRecordDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.EmailTempService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.medcn.common.Constants.DEFAULT_LOCAL;

/**
 * Created by Liuchangling on 2017/9/26.
 */
@Service
public class CspUserServiceImpl extends BaseServiceImpl<CspUserInfo> implements CspUserService {

    @Value("${app.csp.base}")
    protected String appBase;

    @Value("${app.file.upload.base}")
    protected String uploadBase;

    @Value("${app.file.base}")
    protected String fileBase;

    @Value("${csp_mail_username}")
    private String sender;

    @Value("${csp_mail_password}")
    private String password;

    @Value("${csp_mail_server_host}")
    private String serverHost;

    @Autowired
    private EmailTempService tempService;


    @Autowired
    protected CspUserInfoDAO cspUserInfoDAO;

    @Autowired
    protected BindInfoDAO bindInfoDAO;

    @Autowired
    protected SystemNotifyDAO systemNotifyDAO;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;


    @Autowired
    protected CSPEmailHelper emailHelper;

    @Autowired
    protected UserFluxDAO userFluxDAO;

    @Autowired
    protected EmailTemplateDAO templateDAO;

    @Autowired
    protected JavaMailSenderImpl cspMailSender;

    @Override
    public Mapper<CspUserInfo> getBaseMapper() {
        return cspUserInfoDAO;
    }

    @Override
    public CspUserInfo findUserInfoById(String userId) {
        return cspUserInfoDAO.findCspUserById(userId);
    }

    @Override
    public CspUserInfo findBindUserByUniqueId(String uniqueId) {
        return cspUserInfoDAO.findBindUserByUniqueId(uniqueId);
    }

    /**
     * 查询用户绑定的第三方平台列表
     * @param userId
     * @return
     */
    @Override
    public List<BindInfo> findBindListByUserId(String userId) {
        return bindInfoDAO.findBindUserList(userId);
    }

    @Override
    public CspUserInfo findByLoginName(String username) {
        return cspUserInfoDAO.findByLoginName(username);
    }

    @Override
    public String register(CspUserInfo userInfo,EmailTemplate template) throws SystemException {
        if (userInfo == null) {
            return APIUtils.error(local("user.param.empty"));
        }
        String username = userInfo.getEmail();

        // 检查用户邮箱是否已经注册过
        CspUserInfo cspUser = cspUserInfoDAO.findByLoginName(username);
        if (cspUser != null) {
            // 检查是否有激活
            if (cspUser.getActive()) {
                return APIUtils.error(APIUtils.USER_EXIST_CODE,local("user.username.existed"));
            } else {
                // 发送激活邮箱链接
                sendMail(username, userInfo.getId(), template);
                return APIUtils.success(local("user.success.register"));
            }
        }

        // 获取当前语言
        String local = LocalUtils.getLocalStr();
        // 是否海外用户
        if (!local.equals(DEFAULT_LOCAL)) { // 海外
            userInfo.setAbroad(true);
        } else {
            userInfo.setAbroad(false);
        }
        userInfo.setId(StringUtils.nowStr());
        String password = userInfo.getPassword();
        userInfo.setPassword(MD5Utils.md5(password));
        userInfo.setRegisterTime(new Date());
        userInfo.setActive(false);//未激活
        cspUserInfoDAO.insert(userInfo);

        // 发送激活邮箱链接
        sendMail(username, userInfo.getId(), template);

        return APIUtils.success(local("user.success.register"));
    }


    /**
     * 添加第三方平台用户及绑定用户信息
     * @param userDTO
     * @return
     */
    public CspUserInfo saveThirdPartyUserInfo(CspUserInfoDTO userDTO) {
        // 将第三方用户信息 保存到csp用户表
        CspUserInfo userInfo = CspUserInfo.buildToUserInfo(userDTO);
        cspUserInfoDAO.insert(userInfo);

        // 添加绑定第三方平台用户信息
        userDTO.setUid(userInfo.getId());
        BindInfo bindUser = BindInfo.buildToBindInfo(userDTO);
        bindInfoDAO.insert(bindUser);

        return userInfo;
    }

    /**
     * 缓存信息和发送绑定或找回密码邮件
     * @param email
     * @param userId
     * @param template
     * @return
     */
    @Override
    public void sendMail(String email, String userId,EmailTemplate template) throws SystemException {
        String code = StringUtils.uniqueStr();
        String url = null;
        // 发送注册激活邮箱邮件
        if (template.getTempType() == EmailTemplate.Type.REGISTER.getLabelId()) {
            redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY + code, email, (int) TimeUnit.DAYS.toSeconds(1));
            url = appBase + "/api/email/active?code=" + code;

            // 发送找回密码邮件
        } else if (template.getTempType() == EmailTemplate.Type.FIND_PWD.getLabelId()) {
            redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY + code, email, (int) TimeUnit.DAYS.toSeconds(1));
            url = appBase + "/api/email/toReset?code=" + code;

            // 发送绑定邮箱邮件
        } else if (template.getTempType() == EmailTemplate.Type.BIND.getLabelId()) {
            redisCacheUtils.setCacheObject(Constants.EMAIL_LINK_PREFIX_KEY + code, email + "," + userId, (int) TimeUnit.DAYS.toSeconds(1));
            url = appBase + "/api/email/bindEmail?code=" + code;
        }


        MailBean bean = new MailBean();
        bean.setFrom(template.getSender());
        bean.setFromName(template.getSenderName());
        bean.setSubject(template.getSubject());
        bean.setLocalStr(template.getLangType());
        bean.setToEmails(new String[]{email});
        try {
            cspMailSender.setUsername(sender);
            cspMailSender.setPassword(password);
            cspMailSender.setHost(serverHost);
            emailHelper.sendMail(url,template.getContent(),cspMailSender,bean);
        } catch (JDOMException e) {
            e.printStackTrace();
            throw new SystemException(local("email.address.error"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(local("email.error.send"));
        }
    }

    /**
     * 发送绑定邮件
     * @param email
     * @param password
     * @param userId
     * @param localStr
     */
    @Override
    public void sendBindMail(String email, String password, String userId, String localStr) throws SystemException {
        //将密码插入到数据库
        insertPassword(email,password,userId);
        //获取邮件模板对象
        EmailTemplate template = tempService.getTemplate(localStr,EmailTemplate.Type.BIND.getLabelId());
        sendMail(email,userId, template);
    }


    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @param userId
     */
    @Override
    public void doBindMobile(String mobile, String captcha, String userId) throws SystemException {
        CspUserInfo result = findByLoginName(mobile);
        //该手机号已被绑定
        if(result != null){
            throw new SystemException(local("mobile.was.bound"));
        }
        CspUserInfo info = selectByPrimaryKey(userId);
        //用户已绑定手机号
        if(!StringUtils.isEmpty(info.getMobile())){
            throw new SystemException(local("user.has.mobile"));
        }
        info.setMobile(mobile);
        updateByPrimaryKey(info);

    }

    /**
     * 解绑邮箱或手机
     * @param type
     * @param userId
     * @return
     */
    @Override
    public void doUnbindEmailOrMobile(Integer type, String userId) throws SystemException {
        if(type != BindInfo.Type.MOBILE.getTypeId() && type != BindInfo.Type.EMAIL.getTypeId()){
            throw new SystemException(local("error.param"));
        }
        CspUserInfo info = selectByPrimaryKey(userId);
        if(type == BindInfo.Type.EMAIL.getTypeId()){
            //用户没有绑定邮箱
            if(StringUtils.isEmpty(info.getEmail())){
                throw new SystemException(local("user.not.exist.email"));
            }
        }else{
            //用户没有绑定手机
            if(StringUtils.isEmpty(info.getMobile())){
                throw new SystemException(local("user.not.exist.mobile"));
            }
        }

        BindInfo bindInfo = new BindInfo();
        bindInfo.setUserId(userId);
        int count = bindInfoDAO.selectCount(bindInfo);
        //用户没有绑定第三方账号，并且只绑定了手机或邮箱的情况下不能解绑
        if(count < 1 && (StringUtils.isEmpty(info.getEmail())|| StringUtils.isEmpty(info.getMobile()))){
            throw new SystemException(local("user.only.one.account"));
        }
        if(type ==  BindInfo.Type.EMAIL.getTypeId()){
            info.setEmail("");
        }else{
            info.setMobile("");
        }
        updateByPrimaryKeySelective(info);

    }

    /**
     * 绑定第三方账号
     * @param info
     * @param userId
     * @return
     */
    @Override
    public void doBindThirdAccount(BindInfo info, String userId) throws SystemException {
        BindInfo condition = new BindInfo();
        condition.setUniqueId(info.getUniqueId());
        condition.setThirdPartyId(info.getThirdPartyId());
        condition = bindInfoDAO.selectOne(condition);
        if(condition != null){  //该第三方账号已被使用
            throw new SystemException(local("user.exist.account"));
        }

        BindInfo condition2 = new BindInfo();
        condition2.setUserId(userId);
        condition2.setThirdPartyId(info.getThirdPartyId());
        condition2 = bindInfoDAO.selectOne(condition2);
        if(condition2 != null){  //当前的账号已绑定其他第三方账号
            throw new SystemException(local("user.exist.ThirdAccount"));
        }

        //插入到数据库
        info.setId(UUIDUtil.getNowStringID());
        info.setUserId(userId);
        info.setBindDate(new Date());
        bindInfoDAO.insert(info);


    }

    /**
     * 解绑第三方账号
     * @param thirdPartId
     * @param userId
     * @return
     */
    @Override
    public void doUnbindThirdAccount(Integer thirdPartId, String userId) throws SystemException {
        BindInfo condition = new BindInfo();
        condition.setUserId(userId);
        condition.setThirdPartyId(thirdPartId);
        condition = bindInfoDAO.selectOne(condition);
        if(condition == null){  //用户没绑定此第三方账号，不能解绑
            throw new SystemException(local("user.notExist.ThirdAccount"));
        }

        CspUserInfo user = selectByPrimaryKey(userId);
        String email = user.getEmail();
        String mobile = user.getMobile();
        //没有绑定手机和邮箱
        if(StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)){
            BindInfo condition2 = new BindInfo();
            condition2.setUserId(userId);
            int count = bindInfoDAO.selectCount(condition2);
            if(count <= 1){  //没有绑定其他第三方账号，不能解绑
                throw new SystemException(local("user.only.one.account"));
            }
        }

        bindInfoDAO.delete(condition);
    }

    @Override
    public void doBindMail(String email, String userId,String key) throws SystemException {

        CspUserInfo info = selectByPrimaryKey(userId);
        if (info == null) { //用户不存在
            throw new SystemException(local("user.error.nonentity"));
        }
        info.setEmail(email);
        updateByPrimaryKeySelective(info);
        redisCacheUtils.delete(key);

    }


    /**
     * 修改头像
     * @param file
     * @return 头像地址
     */
    @Override
    public String updateAvatar(MultipartFile file,String userId) throws SystemException {
        //相对路径
        String relativePath = FilePath.PORTRAIT.path + File.separator;
        //文件保存路径
        String savePath = uploadBase + relativePath;
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //头像后缀
        String suffix = FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
        String fileName = UUIDUtil.getNowStringID() + "." + suffix;
        String absoluteName = savePath + fileName;
        File saveFile = new File(absoluteName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new SystemException(local("upload.avatar.err"));
        }

        //更新用户头像
        CspUserInfo info = new CspUserInfo();
        info.setId(userId);
        info.setAvatar(relativePath + fileName);
        updateByPrimaryKeySelective(info);
        return fileBase + relativePath + fileName;
    }

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    @Override
    public void resetPwd(String userId, String oldPwd, String newPwd) throws SystemException {
        CspUserInfo result = selectByPrimaryKey(userId);
        if(!MD5Utils.md5(oldPwd).equals(result.getPassword())){
            throw new SystemException(local("user.error.old.password"));
        }
        result.setPassword(MD5Utils.md5(newPwd));
        updateByPrimaryKeySelective(result);
    }

    /**
     * csp web端查找用户信息
     * @param userId
     * @return
     */
    @Override
    public CspUserInfoDTO findCSPUserInfo(String userId) {
        return cspUserInfoDAO.findCSPUserInfo(userId);
    }

    /**
     * 将用户密码插入到数据库
     * @param email
     * @param password
     * @param userId
     */
    @Override
    public void insertPassword(String email, String password, String userId) throws SystemException {
        if(!StringUtils.isEmail(email)){
            throw  new SystemException(local("user.error.email.format"));
        }
        if(StringUtils.isEmpty(password)){
            throw  new SystemException(local("user.password.notnull"));
        }

        CspUserInfo user = selectByPrimaryKey(userId);
        if (!StringUtils.isEmpty(user.getEmail())) {  //当前账号已绑定邮箱
            throw  new SystemException(local("user.has.email"));
        }
        CspUserInfo info = findByLoginName(email);
        if (info != null) { //当前邮箱已被绑定
            throw new SystemException(local("user.exist.email"));
        }
        //将密码插入到数据库
        user.setId(userId);
        user.setPassword(MD5Utils.md5(password));
        updateByPrimaryKey(user);
    }

    /**
     * 视频直播记录
     * @param pageable
     * @return
     */
    @Override
    public MyPage<VideoLiveRecordDTO> findVideoLiveRecord(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        List<VideoLiveRecordDTO> list = cspUserInfoDAO.findVideoLiveRecord(pageable.getParams());
        return MyPage.page2Mypage((Page)list);
    }

    /**
     * 用户流量
     * @param userId
     * @return
     */
    @Override
    public int findFlux(String userId) {
        UserFlux flux = userFluxDAO.selectByPrimaryKey(userId);
        return flux == null? 0:flux.getFlux();
    }



    @Override
    public MyPage<CspUserInfo> findCspUserList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CspUserInfo> page = MyPage.page2Mypage((Page) cspUserInfoDAO.findCspUserList(pageable.getParams()));
        return page;
    }

}
