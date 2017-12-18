package cn.medcn.user.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemNotify;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.VideoLiveRecordDTO;
import cn.medcn.user.model.BindInfo;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.model.UserFluxUsage;
import org.jdom.JDOMException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Liuchangling on 2017/9/26.
 */
public interface CspUserService extends BaseService<CspUserInfo>{

    CspUserInfo findUserInfoById(String userId);

    /**
     * 根据uniqueId 查询用户是否存在
     * @param uniqueId
     * @return
     */
    CspUserInfo findBindUserByUniqueId(String uniqueId);

    /**
     * 查询绑定的第三方平台列表
     * @param userId
     * @return
     */
    List<BindInfo> findBindListByUserId(String userId);

    /**
     * 根据邮箱或者手机号码检查csp账号 是否存在
     * @param username
     * @return
     */
    CspUserInfo findByLoginName(String username);

    /**
     * 注册用户
     * @param userInfo
     */
    String register(CspUserInfo userInfo,EmailTemplate template) throws SystemException;


    /**
     * 添加第三方平台用户及绑定用户信息
     * @param userDTO
     * @return
     */
    CspUserInfo saveThirdPartyUserInfo(CspUserInfoDTO userDTO);


    /**
     * 绑定手机号
     * @param mobile
     * @param captcha
     * @param userId
     */
    void doBindMobile(String mobile, String captcha, String userId) throws SystemException;

    /**
     * 解绑邮箱或手机
     * @param type
     * @param userId
     * @return
     */
    void doUnbindEmailOrMobile(Integer type, String userId) throws SystemException;

    /**
     * 绑定第三方账号
     * @param info
     * @param userId
     * @return
     */
    void doBindThirdAccount(BindInfo info, String userId) throws SystemException;

    /**
     * 解绑第三方账号
     * @param thirdPartId
     * @param userId
     * @return
     */
    void doUnbindThirdAccount(Integer thirdPartId, String userId) throws SystemException;

    /**
     * 绑定邮箱
     * @param email
     * @param userId
     */
    void doBindMail(String email, String userId,String key) throws SystemException;

    /**
     * 修改头像
     * @param file
     * @return 头像地址
     */
    String updateAvatar(MultipartFile file,String userId) throws SystemException;

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    void resetPwd(String userId, String oldPwd, String newPwd) throws SystemException;

    /**
     * cspweb端查找用户信息
     * @param userId
     * @return
     */
    CspUserInfoDTO findCSPUserInfo(String userId);

    void insertPassword(String email, String password, String userId) throws SystemException;


    /**
     * 视频直播记录
     * @param pageable
     * @return
     */
    MyPage<VideoLiveRecordDTO> findVideoLiveRecord(Pageable pageable);

    /**
     * 用户流量
     * @param userId
     * @return
     */
    int findFlux(String userId);


    /**
     * 发送邮件
     * @param email
     * @param userId
     * @param template
     * @throws SystemException
     */
    void sendMail(String email, String userId, EmailTemplate template) throws SystemException;

    /**
     * 发送绑定邮件
     * @param email
     * @param password
     * @param userId
     * @param localStr
     */
    void sendBindMail(String email, String password, String userId, String localStr) throws SystemException;


    /**
     * 获取用户列表
     * @param pageable
     * @return
     */
    MyPage<CspUserInfo> findCspUserList(Pageable pageable);

    CspUserInfo selectByUserName(String userName);

    MyPage<CspUserInfo> findUserList(Pageable pageable);

    /**
     * 查出所有在上线之前的用户
     * @return
     */
    List<CspUserInfo> selectRegisterTime();
}
