package cn.medcn.user.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.sys.model.SystemNotify;
import cn.medcn.user.dto.CspNewlyStaticDTO;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.UserRegionDTO;
import cn.medcn.user.dto.VideoLiveRecordDTO;
import cn.medcn.user.model.*;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/9/26.
 */
public interface CspUserService extends BaseService<CspUserInfo>{
    // 处理用户地理位置信息队列
    String USER_REGION_TOPIC_KEY = "user_region_queue";

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

    void yayaBindUpdate(String userId);

    /**
     * 解绑第三方账号
     * @param thirdPartId
     * @param userId
     * @return
     */
    Integer doUnbindThirdAccount(Integer thirdPartId, String userId) throws SystemException, ParseException;

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
    MyPage<CspUserInfoDTO> findCspUserList(Pageable pageable);

    CspUserInfo selectByUserName(String userName);

    MyPage<CspUserInfo> findUserList(Pageable pageable);

    CspUserInfo selectByMobile(String mobile);

    /**
     * 获取昨天的注册用户数量
     * @param location
     * @return
     */
    int selectRegisterCount(int location);

    /**
     * 新注册的用户列表
     * @param map
     * @return
     */
    List<ReportRegister> findNewlyRegisterList(Map<String, Object> map);

    /**
     * 将用户地理位置信息放入队列中
     * @param region
     */
    void pushToUserRegionQueue(UserRegionDTO region);

    /**
     * 修改用户地理位置信息
     * @param region
     */
    void updateUserRegion(UserRegionDTO region);

    UserRegionDTO brPopUserRegion();

    CspUserInfo selectByEmail(String username);

    int selectNewUser(Integer location);

    int selectAllUserCount(Integer location);

    int selectByProvince(String name);

    MyPage<CspUserInfoDTO> findNewDayMoney(Pageable pageable);

    /**
     * 缓存更新用户套餐信息(用户套餐更改，会议数量更改，套餐基础信息变更 使用)
     *
     * @param userId
     * @return
     */
    Principal updatePackagePrincipal(String userId);
}
