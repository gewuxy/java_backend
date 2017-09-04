package cn.medcn.user.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.user.dto.*;
import cn.medcn.user.model.*;
import cn.medcn.weixin.model.WXUserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/20.
 */
public interface AppUserService extends BaseService<AppUser> {

    /**
     * 注册用户
     * @param user
     * @param invite
     */
    void executeRegist(AppUser user, String invite,Integer[] masterId) throws SystemException;


    /**
     * 批量注册医生用户
     * @param user
     * @param masterId
     */
    void executeBatchRegister(AppUser user,Integer masterId);
    /**
     * 添加角色
     * @param role
     * @return
     */
    int insertRole(AppRole role);

    /**
     * 添加用户角色关系
     * @param userRole
     * @return
     */
    int insertUserRole(AppUserRole userRole);

    /**
     * 删除用户角色
     * @param userId
     * @param roleId
     * @return
     */
    int deleteUserRole(Integer userId, Integer roleId);

    /**
     * 关注
     * @param slaverId
     * @param masterId
     */
    void executeAttention(Integer slaverId, Integer masterId);

    /**
     * 取消关注
     * @param slaverId
     * @param maserId
     */
    void executeCancleAttention(Integer slaverId, Integer maserId);


    /**
     * 关注的公众号
     * @param pageable
     * @return
     */
    MyPage<PublicAccountDTO> mySubscribe(Pageable pageable);

    /**
     * 模糊搜索公众号
     * @param pageable
     * @return
     */
    MyPage<PublicAccountDTO> searchAccount( Pageable pageable);


    void executeRechargeOrder(RechargeOrder rechargeOrder);

    RechargeOrder findRechargeOrder(RechargeOrder rechargeOrder);

    int updateRechargeOrder(RechargeOrder order);

    /**
     * 获取用户明细
     * @param userId
     * @param roleId
     * @return
     */
    AppUserDetail findUserDetail(Integer userId, Integer roleId);

    /**
     * 更新用户信息
     * @param user
     */
    void updateDoctor(AppUser user) throws Exception;

    UnitInfoDTO findUnitInfo(Pageable pageable);

    /**
     * 按条件查询用户
     * @param pageable
     * @return
     */
    MyPage<AppUser> pageUsers(Pageable pageable);

    List<AppRole> findAppRoles();

    /**
     * 公众号资料列表
     * @return
     */
    MyPage<MaterialDTO> findMaterialList(Pageable pageable);

    /**
     * 注册单位号
     * @param appUser
     */
    void exectueRegistUnit(AppUser appUser);

    Integer deleteMaterial(String materialId);


    MyPage<RecommendDTO> selectRecommend(Pageable pageable);

    /**
     * 检测用户是否关注过公众号
     * @param pubUserId
     * @param userId
     * @return
     */
    boolean checkAttention(Integer pubUserId, Integer userId);


    void insertMaterial(Material entity);

    Material findMaterial(String materialId);

    /**
     * 查询所有的已关注用户
     * @param userId
     * @return
     */
    List<AppUserSimpleDTO> findAllAttation(Integer userId, Integer groupId);


    /**
     * 获取弹窗信息
     * @param docId
     * @param ownerId
     * @return
     */
    DoctorDTO getPopUpInfo(Integer docId, Integer ownerId);

    /**
     * 根据省份城市查询医院
     * @param params
     * @return
     */
    List<Hospital> findHospitalByCity(Map<String, Object> params);



    /**
     * 手机端扫描二维码注册
     * @param user
     */
    void executeScanInviteRegist(AppUser user,Integer[] masterId) throws SystemException;

    /**
     * 获取用户的邀请码
     * @param id
     * @return
     */
    ActiveStore getActiveStore(Integer id);



    /**
     * 如果有微信用户信息，绑定微信信息；否则只做更新用户信息操作,返回微信用户昵称
     * @param user
     */
    String updateUserInfo(AppUser user) ;



    /**
     * 根据邮箱或者手机号获取用户信息
     * @param loginName
     * @return
     */
    AppUser findAppUserByLoginName(String loginName);

    AppUser findUserByUnoinId(String unionid);

    void doBindUserAndAttention(AppUser appUser, Integer masterId);

    MyPage<TestAccountDTO> findTestAccount(Pageable pageable);

    void deleteDoctor(Integer id);

    /**
     * 解绑微信号
     * @param user
     */
    void doUnBindWeiXin(AppUser user);

    /**
     * 绑定微信号
     * @param user
     * @return
     */
    String doBindWeiXin(AppUser user);

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    String sendCaptcha(String mobile);

    /**
     * 获取医院等级
     * @param version
     * @return
     */
    String getHosLevel(Integer version,String appFileBaseUrl);

    /**
     * 获取医生职称
     * @return
     */
    List<TitleDTO> getTitle();

    /**
     * 执行测试用户和正式用户注册
     * 医院名:敬信药草园,邀请码:2603 为测试用户注册
     * @param dto
     * @param invite
     * @param masterId
     * @param user
     * @return
     */
    String executeUserRegister(AppUserDTO dto, String invite, Integer[] masterId, AppUser user);
}
