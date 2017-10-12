package cn.medcn.user.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.JSmsService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.*;
import cn.medcn.goods.dao.TradeDetailDAO;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.model.TradeDetail;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.sys.model.SystemProperties;
import cn.medcn.sys.service.SysPropertiesService;
import cn.medcn.user.dao.*;
import cn.medcn.user.dto.*;
import cn.medcn.user.model.*;
import cn.medcn.user.service.AppUserService;
import cn.medcn.weixin.dao.WXUserInfoDAO;
import cn.medcn.weixin.model.WXUserInfo;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/4/20.
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUser> implements AppUserService{

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppUserRoleDAO appUserRoleDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Autowired
    private AppDoctorDAO appDoctorDAO;

    @Autowired
    private AppUnitDAO appUnitDAO;

    @Autowired
    private AppAttentionDAO appAttentionDAO;

    @Autowired
    private ActiveCodeDAO activeCodeDAO;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private RechargeOrderDAO rechargeOrderDAO;

    @Autowired
    private TradeDetailDAO tradeDetailDAO;

    @Autowired
    private MaterialDAO materialDAO;

    @Autowired
    private HospitalDAO hospitalDAO;

    @Autowired
    private ActiveStoreDAO activeStoreDAO;

    @Autowired
    private PubUserDocGroupDAO pubUserDocGroupDAO;


    @Autowired
    private WXUserInfoDAO wxUserInfoDAO;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private SysPropertiesService sysPropertiesService;

    @Autowired
    private JSmsService jSmsService;

    @Override
    public Mapper<AppUser> getBaseMapper() {
        return appUserDAO;
    }

    /**
     * 注册医生用户，Controller层已检查invite,masterId，测试时才会同时为空
     *
     * @param user
     */
    @Override
    public void executeRegist(AppUser user, String invite,Integer[] masterId) throws SystemException{

        Integer roleId = user.getRoleId();
        if (roleId == null){
            roleId = AppRole.AppRoleType.DOCTOR.getId();
        }
        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
        user.setAuthed(true);
        ActiveCode code = null;
        if(!StringUtils.isEmpty(invite)) {
            code = checkInvite(user, invite);
            if(code == null){
                throw new SystemException("邀请码不正确");
            }
            code.setUsed(true);
            code.setActiveTime(new Date());
            code.setUsedId(user.getId());
            activeCodeDAO.updateByPrimaryKeySelective(code);
            appUserDAO.insert(user);
            executeAttention(user.getId(), code.getOnwerid());
        }else{
            appUserDAO.insert(user);
        }

        if(masterId != null){   //扫描邀请码注册
            //减去提供方邀请码数量，并关注提供方
            subtractInviteStoreAndAddAttention(masterId,user);
        }

        //添加关注Yaya讲堂
        executeAttention(user.getId(), Constants.DEFAULT_ATTENTION_PUBLIC_ACCOUNT);

        //给用户创建象数信息
        Credits credits = new Credits();
        credits.setUserId(user.getId());
        credits.setCredit(0);
        creditsService.insert(credits);
        insertAppUserDetail(user,roleId);
    }

    /**
     * 批量注册医生用户
     * @param user
     * @param masterId
     */
    @Override
    public void executeBatchRegister(AppUser user, Integer masterId) {
        Integer roleId = AppRole.AppRoleType.DOCTOR.getId();
        user.setRoleId(roleId);
        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
        user.setAuthed(true);
        appUserDAO.insertSelective(user);
        //添加关注敬信公众号
        executeAttention(user.getId(), Constants.DEFAULT_ATTENTION_PUBLIC_ACCOUNT);
        //关注公众号
        executeAttention(user.getId(),masterId);

        //给用户创建象数信息
        Credits credits = new Credits();
        credits.setUserId(user.getId());
        credits.setCredit(0);
        creditsService.insert(credits);
        insertAppUserDetail(user,roleId);

    }


    /**
     * 检测验证码
     * @param user
     * @param invite
     * @return
     */
    protected ActiveCode checkInvite(AppUser user,String invite) throws SystemException{
        ActiveCode activeCode = new ActiveCode();
        activeCode.setActived(true);
        activeCode.setCode(invite);
        //activeCode.setToName(user.getLinkman());
        activeCode.setUsed(false);
        ActiveCode code = activeCodeDAO.selectOne(activeCode);
        return code;
    }

    protected void insertAppUserDetail(AppUser user, Integer roleId){
        switch (roleId){
            case 1://公众号
                AppUnit unit = (AppUnit) user.getUserDetail();
                unit.setUserId(user.getId());
                appUnitDAO.insert(unit);
                break;
            case 2://医生
                AppDoctor doctor = (AppDoctor) user.getUserDetail();
                if(!StringUtils.isEmpty(doctor.getUnitName())){
                    Hospital condition = new Hospital();
                    condition.setName(doctor.getUnitName());
                    List<Hospital> list = hospitalDAO.select(condition);
                    if(list!=null && list.size()>0){
                        doctor.setHosId(list.get(0).getId());
                    }
                }
                doctor.setUserId(user.getId());
                appDoctorDAO.insert(doctor);
                break;
        }
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @Override
    public int insertRole(AppRole role) {
        return appRoleDAO.insert(role);
    }

    /**
     * 添加用户角色关系
     *
     * @param userRole
     * @return
     */
    @Override
    public int insertUserRole(AppUserRole userRole) {
        return appUserRoleDAO.insert(userRole);
    }

    /**
     * 删除用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public int deleteUserRole(Integer userId, Integer roleId) {
        AppUserRole userRole = new AppUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return appUserRoleDAO.delete(userRole);
    }

    /**
     * 关注
     *
     * @param slaverId
     * @param masterId
     */
    @Override
    public void executeAttention(Integer slaverId, Integer masterId) {
        AppAttention attention = new AppAttention(slaverId, masterId);
        AppAttention existed = appAttentionDAO.selectOne(attention);
        if(existed == null){
            attention.setAttentionTime(new Date());
            appAttentionDAO.insert(attention);
        }
    }

    /**
     * 取消关注
     *
     * @param slaverId
     * @param masterId
     */
    @Override
    public void executeCancleAttention(Integer slaverId, Integer masterId) {
        AppAttention attention = new AppAttention(slaverId, masterId);
        appAttentionDAO.delete(attention);
        PubUserDocGroup group = new PubUserDocGroup();
        group.setDoctorId(slaverId);
        group.setPubUserId(masterId);
        pubUserDocGroupDAO.delete(group);
    }


    /**
     * 关注的公众号
     * @param pageable
     * @return
     */
    @Override
    public MyPage<PublicAccountDTO> mySubscribe(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(), Pageable.countPage);
        Page<PublicAccountDTO> page = (Page<PublicAccountDTO>)appUserDAO.mySubscribe(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 公众号搜索
     * @param pageable
     * @return
     */
    @Override
    public MyPage<PublicAccountDTO> searchAccount( Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(), Pageable.countPage);
        Page<PublicAccountDTO> page = (Page<PublicAccountDTO>)appUserDAO.searchAccount(pageable.getParams());

        return MyPage.page2Mypage(page);
    }



    /**
     * 公众号资料列表
     * @return
     */
    @Override
    public MyPage<MaterialDTO> findMaterialList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(), Pageable.countPage);
        Page<MaterialDTO> page = (Page<MaterialDTO>) appUserDAO.findAllMaterialList(pageable.getParams());
        return MyPage.page2Mypage(page);
    }


    /**
     * 创建订单
     * @param rechargeOrder
     */
    @Override
    public void executeRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrderDAO.insertSelective(rechargeOrder);
    }

    /**
     *
     * @param rechargeOrder
     * @return
     */
    @Override
    public RechargeOrder findRechargeOrder(RechargeOrder rechargeOrder) {
        return rechargeOrderDAO.selectOne(rechargeOrder);
    }

    /**
     * 更新订单状态，更改用户象数，创建象数交易明细
     * @param order
     */
    @Override
    public int updateRechargeOrder(RechargeOrder order) {
        //更新订单状态
        int count1 = rechargeOrderDAO.updateByPrimaryKeySelective(order);
        //更改用户象数
        Credits cre = new Credits();
        cre.setUserId(order.getUserId());
        Credits credits = creditsService.selectOne(cre);  //获取原来的credits
        int count2 = 0;
        if(credits == null){
            credits = new Credits();
            credits.setUserId(order.getUserId());
            credits.setCredit(order.getPrice().intValue()*10);
            count2 =  creditsService.insert(credits);
        }else {
            credits.setCredit(order.getPrice().intValue() * 10+credits.getCredit());
            count2 = creditsService.updateByPrimaryKeySelective(credits);
        }
        //创建象数交易明细
        TradeDetail tradeDetail = new TradeDetail();
        tradeDetail.setCost(order.getPrice().intValue()*10);
        tradeDetail.setCostTime(order.getCreateTime());
        tradeDetail.setCode(UUIDUtil.getNowStringID());
        tradeDetail.setUserId(order.getUserId());
        tradeDetail.setType(1);
        tradeDetail.setDescription("您充值了"+order.getPrice().intValue() * 10+"象数");
        int count3 = tradeDetailDAO.insertSelective(tradeDetail);
        return count1+count2+count3;

    }

    @Override
    public AppUserDetail findUserDetail(Integer userId, Integer roleId) {
        AppUserDetail userDetail = null;
        switch (roleId){
            case 1:
                break;
            case 2:
                AppDoctor condition = new AppDoctor();
                condition.setUserId(userId);
                AppDoctor doctor = appDoctorDAO.selectOne(condition);
                userDetail = doctor;
                break;
            case 3:
                break;
            default:break;
        }
        return userDetail;
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    @Override
    public void updateDoctor(AppUser user)  throws Exception{
        user.setAuthed(true);  //防止user所有字段为null导致抛出异常
        if(!StringUtils.isEmpty(user.getProvince()) && !StringUtils.isEmpty(user.getCity())){ //防止zone为空没有更新
            if(user.getZone() == null){
                user.setZone("");
            }
        }

        appUserDAO.updateByPrimaryKeySelective(user);
        AppDoctor doctor = (AppDoctor) user.getUserDetail();
        doctor.setUserId(user.getId());
        AppDoctor condition = new AppDoctor();
        condition.setUserId(user.getId());
        AppDoctor selectedDoc = appDoctorDAO.selectOne(condition);
        doctor.setId(selectedDoc.getId());
        appDoctorDAO.updateByPrimaryKeySelective(doctor);
    }


    /**
     * 按条件查询用户
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<AppUser> pageUsers(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), false);
        MyPage<AppUser> page = MyPage.page2Mypage((Page) appUserDAO.findAppUsers(pageable.getParams()));
        return page;
    }

    @Override
    public List<AppRole> findAppRoles() {
        return appRoleDAO.select(new AppRole());
    }



    @Override
    public UnitInfoDTO findUnitInfo(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        //获取用户信息
        UnitInfoDTO unitInfoDTO = appUserDAO.findUnitInfo(pageable.getParams());
        if (unitInfoDTO != null) {
            //获取资料列表
            List<MaterialDTO> materialDTOList = appUserDAO.findMaterialDTOList(pageable.getParams());
            if (!CheckUtils.isEmpty(materialDTOList)){
                unitInfoDTO.setMaterialList(materialDTOList);
            }
        }
        return unitInfoDTO;
    }

    @Override
    public Integer deleteMaterial(String materialId) {
        return materialDAO.deleteByPrimaryKey(materialId);
    }


    /**
     * 推荐公众号
     * @param pageable
     * @return
     */
    @Override
    public MyPage<RecommendDTO> selectRecommend(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<RecommendDTO> page = (Page<RecommendDTO>)appUserDAO.selectRecommend(pageable.getParams());
        return MyPage.page2Mypage(page);
    }


    /**
     * 注册单位号
     *
     * @param appUser
     */
    @Override
    public void exectueRegistUnit(AppUser appUser) {
        appUser.setRoleId(AppRole.AppRoleType.PUB_USER.getId());
        appUser.setAuthed(false);//公众号需要审核
        appUser.setRegistDate(new Date());
        appUserDAO.insert(appUser);
        AppUnit unit = new AppUnit();
    }

    /**
     * 检测用户是否关注过公众号
     *
     * @param pubUserId
     * @param userId
     * @return
     */
    @Override
    public boolean checkAttention(Integer pubUserId, Integer userId) {
        AppAttention condition = new AppAttention();
        condition.setMasterId(pubUserId);
        condition.setSlaverId(userId);
        int count = appAttentionDAO.selectCount(condition);
        return count > 0;
    }


    public static void main(String[] args) {
        String pwd = MD5Utils.MD5Encode("123");
        System.out.println(pwd);
    }


    /**
     * 插入新资料
     * @param entity
     */
    @Override
    public void insertMaterial(Material entity) {
        materialDAO.insertSelective(entity);
    }

    /**
     * 查找资料
     * @param materialId
     * @return
     */
    @Override
    public Material findMaterial(String materialId) {

        return materialDAO.selectByPrimaryKey(materialId);
    }

    /**
     * 查询所有的已关注用户
     *
     * @param userId
     * @param groupId
     * @return
     */
    @Override
    public List<AppUserSimpleDTO> findAllAttation(Integer userId, Integer groupId) {
        List<AppUserSimpleDTO> list = null;
        if(groupId == null || groupId == 0){
            list = appAttentionDAO.findAttentions(userId);
        }else{
            list = appAttentionDAO.findAttentionsByGroupId(userId, groupId);
        }
        return list;
    }

    /**
     * 获取弹窗信息
     * @param docId
     * @param ownerId
     * @return
     */
    @Override
    public DoctorDTO getPopUpInfo(Integer docId, Integer ownerId) {
        DoctorDTO dto = appDoctorDAO.getPopUpInfo(docId,ownerId);
        return dto;
    }

    /**
     * 根据省市获取医院
     * @param params
     * @return
     */
    public List<Hospital> findHospitalByCity(Map<String, Object> params){
        List<Hospital> list = hospitalDAO.pageHospitals(params);
        return list;
    }


    /**
     * 获取用户邀请码
     * @param id
     * @return
     */
    @Override
    public ActiveStore getActiveStore(Integer id) {
        return activeStoreDAO.selectByPrimaryKey(id);
    }

    /**
     * 如果有微信用户信息，绑定微信信息；否则只做更新用户信息操作,返回微信用户昵称
     * @param user
     */
    @Override
    public String updateUserInfo(AppUser user){
        appUserDAO.updateByPrimaryKeySelective(user);

        WXUserInfo wxUserInfo = user.getWxUserInfo();
        if(wxUserInfo != null){ //绑定微信
            WXUserInfo result = wxUserInfoDAO.selectByPrimaryKey(wxUserInfo.getUnionid());
            if(result == null){
                wxUserInfoDAO.insert(wxUserInfo);
                return wxUserInfo.getNickname();
            }
        }else if(!StringUtils.isEmpty(user.getUnionid())){  //非绑定微信操作，查找用户是否有绑定微信，有的话，获取微信昵称
            WXUserInfo result = wxUserInfoDAO.selectByPrimaryKey(user.getUnionid());
            if(result != null){
                return result.getNickname();
            }
        }
        return null;
    }


    /**
     * 手机端扫描二维码注册,7.0时创建
     * @param user
     */
    @Override
    public void executeScanInviteRegist(AppUser user,Integer[] masterId) throws SystemException {
        Integer roleId = user.getRoleId();
        if (roleId == null){
            roleId = AppRole.AppRoleType.DOCTOR.getId();
        }
        user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
        user.setAuthed(true);

        appUserDAO.insert(user);
        subtractInviteStoreAndAddAttention(masterId,user);
        //添加关注
        executeAttention(user.getId(), Constants.DEFAULT_ATTENTION_PUBLIC_ACCOUNT);

        //给用户创建象数信息
        Credits credits = new Credits();
        credits.setUserId(user.getId());
        credits.setCredit(0);
        creditsService.insert(credits);
        insertAppUserDetail(user,roleId);
    }

    private void subtractInviteStoreAndAddAttention(Integer[] masterId,AppUser user) throws SystemException {
        Boolean flag = false;
        for(Integer id:masterId){
            AppUser master = selectByPrimaryKey(id);
            if(master != null){
                ActiveStore store = getActiveStore(id);
                if(store != null && store.getStore() > 0){
                    flag = true;
                    //减去邀请码数量
                    store.setStore(store.getStore() - 1);
                    activeStoreDAO.updateByPrimaryKey(store);
                    //关注公众号
                    executeAttention(user.getId(),id);
                }

            }
        }
        if(!flag){
            throw new SystemException("邀请码数量不足");
        }

    }


    /**
     * 根据邮箱或者手机号获取用户信息
     *
     * @param loginName
     * @return
     */
    @Override
    public AppUser findAppUserByLoginName(String loginName) {
        return appUserDAO.findAppUserByLoginName(loginName);
    }


    @Override
    public AppUser findUserByUnoinId(String unionid) {
        AppUser condition = new AppUser();
        condition.setUnionid(unionid);
        AppUser appUser = selectOne(condition);
        return appUser;
    }

    @Override
    public void doBindUserAndAttention(AppUser appUser, Integer masterId) {
        //appUser.setUnionid(appUser.getWxUserInfo().getUnionid());
        appUserDAO.updateByPrimaryKeySelective(appUser);
        WXUserInfo existed = wxUserInfoDAO.selectByPrimaryKey(appUser.getUnionid());
        if(existed == null){
            wxUserInfoDAO.insert(appUser.getWxUserInfo());
        }
        if (masterId != null && masterId != 0){
            executeAttention(appUser.getId(), masterId);
        }
    }

    @Override
    public MyPage<TestAccountDTO> findTestAccount(Pageable pageable) {
        pageable.put("unitName", Constants.DEFAULT_HOS_NAME);
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<TestAccountDTO> page = MyPage.page2Mypage((Page) appUserDAO.findTestAccounts(pageable.getParams()));
        return page;
    }

    @Override
    public void deleteDoctor(Integer id) {
        appUserDAO.deleteByPrimaryKey(id);

        //删除医生信息
        AppDoctor appDoctor = new AppDoctor();
        appDoctor.setUserId(id);
        appDoctorDAO.delete(appDoctor);

        //删除关注信息
        AppAttention attention = new AppAttention();
        attention.setSlaverId(id);
        appAttentionDAO.delete(attention);

        //删除群组信息
        PubUserDocGroup pubUserDocGroup = new PubUserDocGroup();
        pubUserDocGroup.setDoctorId(id);
        pubUserDocGroupDAO.delete(pubUserDocGroup);
    }

    /**
     * 解绑微信号
     * @param user
     */
    @Override
    public void doUnBindWeiXin(AppUser user) {
        wxUserInfoDAO.deleteByPrimaryKey(user.getUnionid());
        user.setUnionid(null);
        appUserDAO.updateByPrimaryKey(user);
    }

    /**
     * 绑定微信号
     * @param user
     * @return
     */
    @Override
    public String doBindWeiXin(AppUser user) {
        appUserDAO.updateByPrimaryKey(user);
        WXUserInfo result = wxUserInfoDAO.selectByPrimaryKey(user.getUnionid());
        if(result == null){
            WXUserInfo userInfo = user.getWxUserInfo();
            wxUserInfoDAO.insert(userInfo);
        }
        return null;
    }

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @Override
    public String sendCaptcha(String mobile) {

        if(!RegexUtils.checkMobile(mobile)){
            return APIUtils.error("手机格式不正确");
        }

        AppUser user = new AppUser();
        user.setMobile(mobile);


        //10分钟内最多允许获取3次验证码
        Captcha captcha = (Captcha)redisCacheUtils.getCacheObject(mobile);
        if(captcha == null){ //第一次获取
            String msgId = null;
            try {
                msgId = jSmsService.send(mobile, Constants.DEFAULT_TEMPLATE_ID);
            } catch (Exception e) {
                return APIUtils.error("发送短信失败");
            }
            Captcha firstCaptcha = new Captcha();
            firstCaptcha.setFirstTime(new Date());
            firstCaptcha.setCount(Constants.NUMBER_ZERO);
            firstCaptcha.setMsgId(msgId);
            redisCacheUtils.setCacheObject(mobile,firstCaptcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME); //15分钟有效期
            return APIUtils.success();

        }else {
            Long between = System.currentTimeMillis() - captcha.getFirstTime().getTime();
            if(captcha.getCount() == 2 && between < TimeUnit.MINUTES.toMillis(10)){
                return APIUtils.error("获取验证码次数频繁，请稍后");
            }
            String msgId = null;
            try {
                msgId = jSmsService.send(mobile, Constants.DEFAULT_TEMPLATE_ID);
            } catch (Exception e) {
                return APIUtils.error("发送短信失败");
            }
            captcha.setMsgId(msgId);
            captcha.setCount(captcha.getCount() + 1);
            redisCacheUtils.setCacheObject(mobile,captcha,Constants.CAPTCHA_CACHE_EXPIRE_TIME);
        }

        return APIUtils.success();
    }

    /**
     * 获取医院等级
     * @param version
     * @return
     */
    @Override
    public String getHosLevel(Integer version,String appFileBaseUrl) {
        SystemProperties properties = new SystemProperties();
        List<SystemProperties> list = null;
        list = sysPropertiesService.select(properties);
        Integer newVersion = list.get(0).getVersion();
        for(int i=1;i<list.size();i++){
            if(list.get(i).getVersion() > newVersion){
                newVersion = list.get(i).getVersion();
            }
        }

        Map<String,Object> map = new HashedMap();
        map.put("version",newVersion);
        if(version != null && version >= newVersion){  //版本无变化
            return APIUtils.success(map);
        }
        //第一次获取或者版本有变化,返回所有的数据

        for(SystemProperties properties1:list){
            if(!StringUtils.isEmpty(properties1.getPicture())){
                properties1.setPicture(appFileBaseUrl + properties1.getPicture());
            }
        }
        map.put("propList",list);
        return APIUtils.success(map);
    }

    /**
     * 获取医生职称
     * @return
     */
    @Override
    public List<TitleDTO> getTitle() {
        List<TitleDTO> dtoList = new ArrayList<>();
        List<String> gradeList = new ArrayList<>();
        gradeList.add("医师");
        gradeList.add("药师");
        gradeList.add("护师");
        gradeList.add("技师");

        TitleDTO dto1 = new TitleDTO();
        dto1.setTitle("高级职称");
        dto1.setGrade(gradeList);

        TitleDTO dto2 = new TitleDTO();
        dto2.setTitle("中级职称");
        dto2.setGrade(gradeList);

        TitleDTO dto3 = new TitleDTO();
        dto3.setTitle("初级职称");
        dto3.setGrade(gradeList);

        TitleDTO dto4 = new TitleDTO();
        dto4.setTitle("其他");
        List<String> list = new ArrayList<>();
        list.add("其他职称");
        dto4.setGrade(list);

        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        dtoList.add(dto4);
        return dtoList;
    }

    /**
     * 执行测试用户和正式用户注册
     * @param dto
     * @param invite
     * @param masterId
     * @param user
     * @return
     */
    @Override
    public String executeUserRegister(AppUserDTO dto, String invite, Integer[] masterId, AppUser user) {
        try{
            //检查是否是测试用邀请码, 医院名:敬信药草园,邀请码:2603 为测试用户注册
            if(Constants.DEFAULT_HOS_NAME.equals(dto.getHospital()) && Constants.DEFAULT_INVITE.equals(invite)){
               executeRegist(user, null,null);  //测试用户注册
            }else{
                //已检查invite,masterId，不可能同时为空
                executeRegist(user, invite,masterId);//真实用户注册
            }
        }catch (Exception e){
            return APIUtils.error(e.getMessage());
        }
        return null;
    }

    /**
     * 关闭或开启投稿功能
     * @param userId
     * @param flag
     */
    @Override
    public void doChangeDelivery(Integer userId,Integer flag) {
        AppDoctor condition = new AppDoctor();
        condition.setUserId(userId);
        AppDoctor result = appDoctorDAO.selectOne(condition);
        if(result != null){
            result.setDeliveryFlag(flag);
            appDoctorDAO.updateByPrimaryKey(result);
        }
    }

    /**
     * 查询是否开启投稿功能
     * @param userId
     * @return
     */
    @Override
    public int findDeliveryFlag(Integer userId) {
        AppDoctor condition = new AppDoctor();
        condition.setUserId(userId);
        AppDoctor result = appDoctorDAO.selectOne(condition);
        if(result != null){
            return result.getDeliveryFlag();
        }
        return 0;
    }


    /**
     * 查询所有的已关注用户
     *
     * @param userId
     * @param groupIds
     * @return
     */
    @Override
    public List<AppUserSimpleDTO> findAllAttention(Integer userId, Integer[] groupIds) {
        List<AppUserSimpleDTO> list = new ArrayList<>();
        for (Integer groupId : groupIds) {
            list.addAll(findAllAttation(userId, groupId));
        }
        return list;
    }
}
