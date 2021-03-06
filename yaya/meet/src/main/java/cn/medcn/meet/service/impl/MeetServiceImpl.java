package cn.medcn.meet.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.goods.dto.CreditPayDTO;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.meet.dao.*;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.*;
import cn.medcn.meet.support.MeetCheckHelper;
import cn.medcn.user.dao.GroupDAO;
import cn.medcn.user.dao.PubUserDocGroupDAO;
import cn.medcn.user.model.*;
import cn.medcn.user.service.AppUserService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuan on 2017/4/20.
 */
@Service
public class MeetServiceImpl extends BaseServiceImpl<Meet> implements MeetService {

    @Value("${app.file.base}")
    private String appFileBase;

    @Autowired
    private MeetDAO meetDAO;

    @Autowired
    private MeetModuleDAO meetModuleDAO;

    @Autowired
    private MeetTuijianDAO meetTuijianDAO;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private MeetAttendDAO meetAttendDAO;

    @Autowired
    private MeetPropertyDAO meetPropertyDAO;

    @Autowired
    private MeetMemberDAO meetMemberDAO;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MeetExamDAO meetExamDAO;

    @Autowired
    private ExamPaperDAO examPaperDAO;

    @Autowired
    private MeetSurveyDAO meetSurveyDAO;

    @Autowired
    private MeetPositionDAO meetPositionDAO;

    @Autowired
    private MeetAudioDAO meetAudioDAO;

    @Autowired
    private SurveyPaperDAO surveyPaperDAO;

    @Autowired
    private SurveyQuestionDAO surveyQuestionDAO;

    @Autowired
    private MeetLecturerDAO meetLecturerDAO;

    @Autowired
    private MeetVideoDAO meetVideoDAO;

    @Autowired
    private MeetFavoriteDAO meetFavoriteDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private MeetMaterialDAO meetMaterialDAO;

    @Autowired
    private PubUserDocGroupDAO pubUserDocGroupDAO;

    @Autowired
    private MeetMaterialService meetMaterialService;

    @Autowired
    private MeetLearningRecordDAO meetLearningRecordDAO;

    @Autowired
    private MeetRewardHistoryDAO meetRewardHistoryDAO;


    @Autowired
    protected MeetNotifyService meetNotifyService;

    @Autowired
    private MeetSettingDAO meetSettingDAO;

    @Autowired
    private MeetFolderService meetFolderService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SignService signService;

    @Autowired
    private CourseDeliveryService courseDeliveryService;

    @Override
    public Mapper<Meet> getBaseMapper() {
        return meetDAO;
    }


    /**
     * 查询推荐会议信息 分页
     *
     * @param pageable
     * @param userId
     * @return
     */
    @Override

    public MyPage<MeetTuijianDTO> findMeetTuijian(Pageable pageable, Integer userId) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetTuijianDTO> page = (Page<MeetTuijianDTO>) meetDAO.findMeetTuijian(userId);

        return MyPage.page2Mypage(page);
    }

    /**
     * 已关注的会议类型统计
     *
     * @param userId
     * @return
     */
    @Override
    public List<MeetTypeState> findMeetTypes(Integer userId) {
        List<MeetTypeState> typeList =  meetDAO.findMeetTypes(userId);
        int totalCount = 0;
        for (MeetTypeState type : typeList){
            type.setIcon(appFileBase + type.getIcon());
            totalCount = totalCount + type.getCount();
        }
        MeetTypeState typeState = new MeetTypeState();
        typeState.setName("全部科室");
        typeState.setIcon(appFileBase+Constants.MEET_DEFAULT_DEPARTMENT_ICON);
        typeState.setCount(totalCount);
        typeList.add(typeState);
        return typeList;
    }

    @Override
    public void addMeetTuijian(MeetTuijian tuijian) {
        meetTuijianDAO.insert(tuijian);
    }

    /**
     * 添加会议模块
     *
     * @param module
     */
    @Override
    public void addMeetModule(MeetModule module) throws SystemException {
        if (module.getFunctionId() == MeetModule.ModuleFunction.PPT.getFunId()) {
            module.setMainFlag(true);
        }
        module.setModuleName(MeetModule.ModuleFunction.getFunctionById(module.getFunctionId()).getFunName());
        meetModuleDAO.insert(module);
        switch (module.getFunctionId()) {
            case 1:
                MeetAudio meetAudio = new MeetAudio();
                meetAudio.setMeetId(module.getMeetId());
                meetAudio.setModuleId(module.getId());
                meetAudioDAO.insert(meetAudio);
                break;
            case 2:
                MeetVideo meetVideo = new MeetVideo();
                meetVideo.setMeetId(module.getMeetId());
                meetVideo.setModuleId(module.getId());
                meetVideoDAO.insert(meetVideo);
                break;
            case 3:
                MeetExam exam = new MeetExam();
                exam.setMeetId(module.getMeetId());
                exam.setModuleId(module.getId());
                meetExamDAO.insert(exam);
                break;
            case 4:
                MeetSurvey survey = new MeetSurvey();
                survey.setMeetId(module.getMeetId());
                survey.setModuleId(module.getId());
                meetSurveyDAO.insert(survey);
                break;
            case 5:
                MeetPosition position = new MeetPosition();
                position.setModuleId(module.getId());
                position.setMeetId(module.getMeetId());
                meetPositionDAO.insert(position);
                break;
            default:
                break;
        }
    }

    /**
     * 查询功能模块的课程id
     * @param module
     * @return
     */
    public Integer findModuleCourseId(MeetModule module){
        Integer courseId = 0;
        switch (module.getFunctionId()) {
            case 1:
                MeetAudio meetAudio = new MeetAudio();
                meetAudio.setMeetId(module.getMeetId());
                meetAudio.setModuleId(module.getId());
                MeetAudio oldAudio = meetAudioDAO.selectOne(meetAudio);
                if (oldAudio != null) {
                    courseId = oldAudio.getCourseId();
                }
                break;
            case 2:
                MeetVideo meetVideo = new MeetVideo();
                meetVideo.setMeetId(module.getMeetId());
                meetVideo.setModuleId(module.getId());
                MeetVideo oldVideo = meetVideoDAO.selectOne(meetVideo);
                if (oldVideo != null) {
                    courseId = oldVideo.getCourseId();
                }
                break;
            case 3:
                MeetExam exam = new MeetExam();
                exam.setMeetId(module.getMeetId());
                exam.setModuleId(module.getId());
                MeetExam oldExam = meetExamDAO.selectOne(exam);
                if (oldExam != null) {
                    courseId = oldExam.getPaperId();
                }
                break;
            case 4:
                MeetSurvey survey = new MeetSurvey();
                survey.setMeetId(module.getMeetId());
                survey.setModuleId(module.getId());
                MeetSurvey oldSurvey = meetSurveyDAO.selectOne(survey);
                if (oldSurvey != null) {
                    courseId = oldSurvey.getPaperId();
                }
                break;
            case 5:
                MeetPosition position = new MeetPosition();
                position.setModuleId(module.getId());
                position.setMeetId(module.getMeetId());
                MeetPosition oldPos = meetPositionDAO.selectOne(position);
                if (oldPos != null) {
                    courseId = oldPos.getId();
                }
                break;
            default:
                break;
        }
        return courseId;
    }


    /**
     * 查询会议资料
     *
     * @param meetId
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'meetinfo_'+#meetId")
    public MeetInfoDTO findMeetInfo(String meetId) {
        MeetInfoDTO infoDTO = meetDAO.findMeetInfo(meetId);
        if (infoDTO != null) {
            List<MeetModule> modules = findModules(meetId);
            infoDTO.setModules(modules);
            //查询会议资料
            Pageable pageable = new Pageable(1, 3);
            pageable.getParams().put("meetId", meetId);
            MyPage<MeetMaterial> page = meetMaterialService.page(pageable);
            infoDTO.setMaterialCount(new Long(page.getTotal()).intValue());
            infoDTO.setMaterials(page.getDataList());
        }
        return infoDTO;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetInfoDTO> searchMeetInfo(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetInfoDTO> page = (Page<MeetInfoDTO>) meetDAO.searchMeetInfo(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 搜索会议文件夹及会议列表 分页
     * @param pageable
     * @return
     */
    public MyPage<MeetFolderDTO> searchMeetFolderInfo(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetFolderDTO> page = (Page<MeetFolderDTO>) meetDAO.searchMeetFolderInfo(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 分页查询我关注过的公众号的会议
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetInfoDTO> findMyMeets(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetInfoDTO> page = (Page<MeetInfoDTO>) meetDAO.findMyMeets(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 参与会议
     *
     * @param meetId
     * @param moduleId
     * @param userId
     */
    @Override
    public void executeAttend(String meetId, Integer moduleId, Integer userId) throws SystemException, NotEnoughCreditsException {
        Meet meet = selectByPrimaryKey(meetId);
        if (meet == null) {
            throw new SystemException("会议id=[" + meetId + "]不存在");
        }
        MeetModule module = meetModuleDAO.selectByPrimaryKey(moduleId);
        if (module == null) {
            throw new SystemException("模块id=[" + moduleId + "]不存在");
        }
        if (meet.getState() == 0 || meet.getState() >= 4) {
            throw new SystemException(SpringUtils.getMessage("meet.state.error"));
        }
        // 如果用户已经参加过会议则不再判断会议是否需要象数
        boolean attended = checkAttend(meetId, userId);
        MeetProperty meetProperty = findMeetProperty(meetId);
        checkStartTime(meetProperty);
        if (!attended) {
            //没有参加过会议则需要判断会议是否需要象数 并进行象数扣除或者奖励
            checkAttention(meet.getOwnerId(), userId);
            //没有参加过会先判断会议是否指定用户参加
            checkAttendLimit(meetProperty, meetId);
            Integer memberLimitType = meetProperty.getMemberLimitType();
            if (memberLimitType == null) {
                memberLimitType = MeetProperty.MemberLimit.NOT_LIMIT.getType();
            }
            if (memberLimitType == MeetProperty.MemberLimit.LIMIT_BY_LOCATION.getType()) {
                checkZone(meetProperty, userId);
            } else if (memberLimitType == MeetProperty.MemberLimit.LIMIT_BY_GROUP.getType()) {
                if (meetProperty.getGroupIds() != null && meetProperty.getGroupIds().length > 0) {
                    checkMember(meetProperty, meetId, userId);
                }
            }
            // payCredits(meetId, userId, meet.getOwnerId());
            requiredPayXs(meetId, userId, meet.getOwnerId());
            insertAttend(meetId, moduleId, userId);
        }
    }

    protected void checkAttention(Integer pubUserId, Integer userId) throws SystemException {
        if (!appUserService.checkAttention(pubUserId, userId)) {
            throw new SystemException(SpringUtils.getMessage("meet.notattention.error"));
        }
    }

    protected void checkMember(MeetProperty property, String meetId, Integer userId) throws SystemException {
        boolean userInGroup = userInGroup(property, meetId, userId);
        if (!userInGroup) {
            throw new SystemException(SpringUtils.getMessage("meet.member.notexisted"));
        }
    }

    protected boolean hasGroup(Integer groupId, Integer userId){
        PubUserDocGroup condition = new PubUserDocGroup();
        condition.setDoctorId(userId);
        condition.setGroupId(groupId);
        int memberCount = pubUserDocGroupDAO.selectCount(condition);
        return memberCount > 0;
    }

    protected boolean userInGroup(MeetProperty property, String meetId, Integer userId) {
        if (property.getGroupIds() != null && property.getGroupIds().length > 0) {
            for (Integer groupId : property.getGroupIds()) {
                if (hasGroup(groupId, userId)){
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    protected void checkAttendLimit(MeetProperty property, String meetId) throws SystemException {
        if (property.getAttendLimit() != null && property.getAttendLimit() > 0) {
            int attendCount = meetAttendDAO.findAttendCount(meetId);
            if (attendCount >= property.getAttendLimit()) {
                throw new SystemException(SpringUtils.getMessage("meet.attend.limited"));
            }
        }
    }

    protected void checkStartTime(MeetProperty property) throws SystemException {
        if (property.getStartTime() != null) {
            if (property.getStartTime().after(new Date())) {
                throw new SystemException(SpringUtils.getMessage("meet.starttime.error"));
            }
        }
    }

    protected void checkZone(MeetProperty property, Integer userId) throws SystemException {
        if (!StringUtils.isEmpty(property.getSpecifyProvince())) {
            AppUser user = appUserService.selectByPrimaryKey(userId);
            if (StringUtils.isEmpty(user.getProvince())) {
                throw new SystemException(SpringUtils.getMessage("meet.attend.zoneerror"));
            }
            if (!user.getProvince().substring(2).equals(property.getSpecifyProvince().substring(2))) {
                throw new SystemException(SpringUtils.getMessage("meet.attend.zoneerror"));
            }
            if (!StringUtils.isEmpty(property.getSpecifyCity())) {
                if (StringUtils.isEmpty(user.getCity())) {
                    throw new SystemException(SpringUtils.getMessage("meet.attend.zoneerror"));
                }
                if (!user.getProvince().substring(2).equals(property.getSpecifyProvince().substring(2))) {
                    throw new SystemException(SpringUtils.getMessage("meet.attend.zoneerror"));
                }
            }
            if (!StringUtils.isEmpty(property.getSpecifyDepart())) {
                AppDoctor doctor = (AppDoctor) appUserService.findUserDetail(userId, AppRole.AppRoleType.DOCTOR.getId());
                if (doctor == null || !property.getSpecifyDepart().equals(doctor.getSubUnitName())) {
                    throw new SystemException(SpringUtils.getMessage("meet.member.notexisted"));
                }
            }
        }
    }

    protected void insertAttend(String meetId, Integer moduleId, Integer userId) {
        MeetAttend attendCondition = new MeetAttend();
        attendCondition.setMeetId(meetId);
        //attendCondition.setModuleId(moduleId);
        attendCondition.setUserId(userId);

        MeetAttend meetAttend = meetAttendDAO.selectOne(attendCondition);
        if (meetAttend == null) {
            meetAttend = new MeetAttend();
            meetAttend.setMeetId(meetId);
            meetAttend.setStartTime(new Date());
            meetAttend.setUserId(userId);
            meetAttend.setModuleId(moduleId);
            meetAttendDAO.insert(meetAttend);
        }
    }

    /**
     * 支付象数
     * @param meetId
     * @param userId
     * @param accepter
     * @throws NotEnoughCreditsException
     */
    protected void requiredPayXs(String meetId, Integer userId, Integer accepter) throws NotEnoughCreditsException{
        Integer payXs = MeetSetting.modeLabel.PAY_XS.getPropMode();
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("meetId",meetId);
        conditionMap.put("propType",payXs);
        List<MeetSetting> settingList = findMeetSetting(conditionMap);
        if (!CheckUtils.isEmpty(settingList)) {
            for (MeetSetting setting : settingList) {
                // 需要支付象数的情况
                if (setting.getPropMode() == payXs) {
                    Meet meet = meetDAO.selectByPrimaryKey(meetId);
                    AppUser user = appUserService.selectByPrimaryKey(userId);
                    Integer payValue = setting.getPropValue();//支付象数
                    if (setting.getPropValue() > 0) {
                        CreditPayDTO payDTO = new CreditPayDTO();
                        payDTO.setAccepter(accepter);
                        payDTO.setPayer(userId);
                        payDTO.setCredits(payValue);
                        payDTO.setPayerDescrib("您支付了" + payValue + "个象数参加 " + "‘" + meet.getMeetName() + "’");
                        payDTO.setAccepterDescrib("用户 " + user.getNickname() + " 参加了您的会议,您获得" + payValue + "个象数");
                        creditsService.executePlayCredits(payDTO);
                    }
                }
            }
        }
    }


    /**
     * 支付或者奖励象数
     *
     * @param meetId
     * @param userId
     * @param accepter
     */
    protected void payCredits(String meetId, Integer userId, Integer accepter) throws NotEnoughCreditsException {
        MeetProperty condition = new MeetProperty();
        condition.setMeetId(meetId);
        MeetProperty meetProperty = findMeetProperty(meetId);
        if (meetProperty.getEduCredits() == null) {
            meetProperty.setEduCredits(0);
        }
        if (meetProperty.getXsCredits() == null) {
            meetProperty.setXsCredits(0);
        }
        if (meetProperty.getAwardLimit() == null) {
            meetProperty.setAwardLimit(0);
        }
        //需要支付象数的情况
        Integer requiredCredit = meetProperty.getXsCredits();
        Meet meet = meetDAO.selectByPrimaryKey(meetId);
        AppUser user = appUserService.selectByPrimaryKey(userId);
        if (meetProperty.getEduCredits() == 0) {
            if (requiredCredit > 0) {
                CreditPayDTO payDTO = new CreditPayDTO();
                payDTO.setAccepter(accepter);
                payDTO.setPayer(userId);
                payDTO.setCredits(requiredCredit);
                payDTO.setPayerDescrib("您支付了" + requiredCredit + "个象数参加 " + "‘" + meet.getMeetName() + "’");
                payDTO.setAccepterDescrib("用户 " + user.getNickname() + " 参加了您的会议,您获得" + requiredCredit + "个象数");
                creditsService.executePlayCredits(payDTO);
            }
        } else {//需要奖励象数的情况
            if (requiredCredit > 0) {
                MeetAttend attendCondition = new MeetAttend();
                attendCondition.setMeetId(meetId);
                int attendCount = meetAttendDAO.selectCount(attendCondition);
                if (meetProperty.getAwardLimit() > attendCount) {
                    CreditPayDTO payDTO = new CreditPayDTO();
                    payDTO.setAccepter(userId);
                    payDTO.setPayer(accepter);
                    payDTO.setCredits(requiredCredit);
                    payDTO.setPayerDescrib("用户 " + user.getNickname() + " 参加您的会议,您支付了" + requiredCredit + "个象数作为奖励");
                    payDTO.setAccepterDescrib("您参加了 " + "‘" + meet.getMeetName() + "’, " + meet.getOrganizer() + "奖励您" + requiredCredit + "个象数");
                    creditsService.executeAwardCredits(payDTO);
                }
            }
        }
    }

    /**
     * 判断用户是否已经参加过该会议
     *
     * @param meetId
     * @param userId
     * @return
     */
    public boolean checkAttend(String meetId, Integer userId) {
        MeetAttend condition = new MeetAttend();
        condition.setMeetId(meetId);
        condition.setUserId(userId);
        int count = meetAttendDAO.selectCount(condition);
        return count > 0;
    }


    /***
     * 获取会议属性
     * @param meetId
     * @return
     */
    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'meetproperty_'+#meetId")
    public MeetProperty findMeetProperty(String meetId) {
        MeetProperty condition = new MeetProperty();
        condition.setMeetId(meetId);
        MeetProperty meetProperty = meetPropertyDAO.selectOne(condition);
        return meetProperty;
    }

    /**
     * 增加会议限制属性
     *
     * @param meetProperty
     */
    @Override
    public void insertMeetProp(MeetProperty meetProperty) {
        meetPropertyDAO.insert(meetProperty);
    }

    /**
     * 修改会议限制属性
     *
     * @param meetProperty
     */
    @Override
    public void updateMeetProp(MeetProperty meetProperty) {
        meetPropertyDAO.updateByPrimaryKeySelective(meetProperty);
    }

    /**
     * 修改会议象数 学分设置
     * @param setting
     */
    @Override
    public void saveOrUpdateMeetSetting(MeetSetting setting,String meetId){
        setting.setMeetId(meetId);
        List<MeetSetting> settingList = meetSettingDAO.select(setting);
        if (!CheckUtils.isEmpty(settingList)) {
            Integer XS_TYPE = MeetSetting.propTypeLabel.XS.getPropType();
            Integer CREDIT_TYPE = MeetSetting.propTypeLabel.CREDIT.getPropType();
            Integer propMode = 0 ;
            Integer propValue = 0;
            Integer limit = 0;

            for (MeetSetting st : settingList) {
                // 象数设置
                if (setting.getRequiredXs() != null
                        && (st.getPropType().intValue() == XS_TYPE.intValue() )) {
                    propMode = setting.getRequiredXs();
                    propValue = setting.getXsCredits();
                    limit = setting.getAwardLimit();
                    if (limit == null) {
                        limit = 0;
                    }

                }
                // 学分设置
                if (setting.getRewardCredit() != null
                        && (st.getPropType().intValue() == CREDIT_TYPE.intValue() )) {
                    propMode = setting.getRewardCredit();
                    propValue = setting.getEduCredits();
                    limit = setting.getAwardCreditLimit();
                }

                st.setPropMode(propMode);
                st.setPropValue(propValue);
                st.setRewardLimit(limit);
                if (st.getPropType().intValue() == XS_TYPE.intValue()) {
                    if ((propMode == 0 || propMode ==1) && propValue != 0) {
                        meetSettingDAO.updateByPrimaryKey(st);
                    } else {
                        meetSettingDAO.deleteByPrimaryKey(st.getId());
                    }
                }

                if (st.getPropType().intValue() == CREDIT_TYPE.intValue()) {
                    if (propMode != null && propValue != null) {
                        meetSettingDAO.updateByPrimaryKey(st);
                    } else {
                        meetSettingDAO.deleteByPrimaryKey(st.getId());
                    }
                }
            }

        } else {
            if (setting != null ) {
                addMeetSetting(setting,meetId);
            }
        }
    }

    /**
     * 添加会议主讲者
     *
     * @param lecturer
     */
    @Override
    public int addMeetLecturer(Lecturer lecturer) {
        if (lecturer.getId() != null) {
            meetLecturerDAO.updateByPrimaryKeySelective(lecturer);
        } else {
            meetLecturerDAO.insert(lecturer);
        }
        return lecturer.getId();
    }

    /**
     * 修改会议信息
     *
     * @param meet
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'meetinfo_'+#meet.id")
    public void updateMeet(Meet meet) {
        //修改会议主讲者
        Lecturer lecturer = meet.getLecturer();
        if (lecturer != null) {
            if (lecturer.getId() == null) {
                lecturer.setMeetId(meet.getId());
                addMeetLecturer(lecturer);
            } else {
                meetLecturerDAO.updateByPrimaryKeySelective(lecturer);
            }
        }
        //修改会议属性
        MeetProperty property = meet.getMeetProperty();
        if (property != null) {
            if (property.getId() == null) {
                property.setMeetId(meet.getId());
                insertMeetProp(property);
            } else {
                updateMeetProp(property);
            }
        }
        // 修改会议象数 学分设置
        MeetSetting setting = meet.getMeetSetting();//页面上传过来的数据
        if (setting != null) {
            saveOrUpdateMeetSetting(setting,meet.getId());
        }

        super.updateByPrimaryKeySelective(meet);
    }


    private void copyMeetProperty(MeetProperty source, MeetProperty destation) {
        BeanUtils.copyProperties(source, destation);
    }


    @Override
    public int insert(Meet meet) {
        int result = super.insert(meet);
        MeetProperty property = meet.getMeetProperty();
        if (property != null) {
            property.setMeetId(meet.getId());
            insertMeetProp(property);
        }
        Lecturer lecturer = meet.getLecturer();
        if (lecturer != null) {
            lecturer.setMeetId(meet.getId());
            addMeetLecturer(lecturer);
        }
        return result;
    }


    /**
     * 会议收藏
     *
     * @param meetStore
     */
    @Override
    public void storeMeet(Favorite meetStore) {
        if (meetFavoriteDAO.selectCount(meetStore) == 0) {
            meetStore.setStoreTime(new Date());
            meetStore.setResourceType(0);
            meetFavoriteDAO.insertSelective(meetStore);
        }
    }

    /**
     * 取消收藏
     *
     * @param meetStore
     */
    @Override
    public void cancelFavorite(Favorite meetStore) {
        meetFavoriteDAO.delete(meetStore);
    }

    /**
     * 我收藏的会议
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetInfoDTO> findMyFavorite(Pageable pageable) {
        Integer userId = (Integer) pageable.get("userId");
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetInfoDTO> page = (Page<MeetInfoDTO>) meetDAO.findMyStore(pageable.getParams());
        if (!CheckUtils.isEmpty(page.getResult())) {
            Integer completeProgress;
            for (MeetInfoDTO meetInfoDTO : page.getResult()) {
                //设置完成进度
                completeProgress = findUserLearningRecord(meetInfoDTO.getId(), userId).intValue();
                if (completeProgress != null && completeProgress != 0) {
                    meetInfoDTO.setCompleteProgress(completeProgress);
                }
                MeetCheckHelper.checkMeetInfo(meetInfoDTO, appFileBase);
            }
        }
        return MyPage.page2Mypage(page);
    }

    @Override
    public MyPage<MeetListInfoDTO> findPublished(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), false);
        MyPage<MeetListInfoDTO> page = MyPage.page2Mypage((Page) meetDAO.findPublished(pageable.getParams()));
        return page;
    }

    @Override
    public MyPage<MeetListInfoDTO> findMeetForSend(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), false);
        MyPage<MeetListInfoDTO> page = MyPage.page2Mypage((Page) meetDAO.findMeetForSend(pageable.getParams()));
        return page;
    }

    @Override
    public MyPage<MeetListInfoDTO> findPublishedHasCount(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        MyPage<MeetListInfoDTO> page = MyPage.page2Mypage((Page) meetDAO.findPublished(pageable.getParams()));
        return page;
    }

    /**
     * 查询草稿箱列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetListInfoDTO> findDraft(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<MeetListInfoDTO> page = MyPage.page2Mypage((Page) meetDAO.findDraft(pageable.getParams()));
        return page;
    }

    /**
     * 添加会议
     *
     * @param meet
     */
    @Override
    public String addMeet(Meet meet) {
        meetDAO.insert(meet);
        // 会议属性设置
        MeetProperty property = meet.getMeetProperty();
        if (property != null) {
            property.setMeetId(meet.getId());
            meetPropertyDAO.insert(property);
        }
        // 会议象数、学分设置
        MeetSetting setting = meet.getMeetSetting();
        if (setting != null) {
            addMeetSetting(setting,meet.getId());
        }
        // 会议讲者信息设置
        Lecturer lecturer = meet.getLecturer();
        if (lecturer != null) {
            lecturer.setMeetId(meet.getId());
            meetLecturerDAO.insert(lecturer);
        }
        return meet.getId();
    }

    public String addMeetInfo(Meet meet) {
        meetDAO.insert(meet);
        // 会议属性设置
        MeetProperty property = meet.getMeetProperty();
        if (property != null) {
            property.setMeetId(meet.getId());
            property.setId(null);
            meetPropertyDAO.insert(property);
        }
        // 会议象数、学分设置
        MeetSetting setting = meet.getMeetSetting();
        if (setting != null) {
            setting.setMeetId(meet.getId());
            setting.setId(null);
            addMeetSetting(setting,meet.getId());
        }
        // 会议讲者信息设置
        Lecturer lecturer = meet.getLecturer();
        if (lecturer != null) {
            lecturer.setId(null);
            lecturer.setMeetId(meet.getId());
            meetLecturerDAO.insert(lecturer);
        }
        return meet.getId();
    }

    /**
     * 会议象数 学分设置
     * @param setting
     * @param meetId
     */
    private void addMeetSetting(MeetSetting setting, String meetId){
        Integer XS_TYPE = MeetSetting.propTypeLabel.XS.getPropType();
        Integer CREDIT_TYPE = MeetSetting.propTypeLabel.CREDIT.getPropType();
        // 象数设置
        MeetSetting sett = new MeetSetting();
        if (setting.getRequiredXs() != null){
            sett.setPropMode(setting.getRequiredXs());
            sett.setPropType(XS_TYPE);
            if (setting.getRequiredXs().intValue() == 0) {
                sett.setRewardLimit(0);
            }
        }
        if (setting.getAwardLimit() != null) {
            sett.setRewardLimit(setting.getAwardLimit());
        }
        if (setting.getXsCredits() != null) {
            sett.setPropValue(setting.getXsCredits());
        }
        if (sett != null && setting.getRequiredXs() != null) {
            sett.setMeetId(meetId);
            meetSettingDAO.insert(sett);
        }

        // 学分设置
        MeetSetting settingCredit = new MeetSetting();
        settingCredit.setMeetId(meetId);
        if (setting.getRewardCredit() != null && setting.getRewardCredit() != 0) {
            settingCredit.setPropMode(setting.getRewardCredit());
            settingCredit.setPropValue(setting.getEduCredits());
            settingCredit.setRewardLimit(setting.getAwardCreditLimit());
            settingCredit.setPropType(CREDIT_TYPE);
            meetSettingDAO.insert(settingCredit);
        }
    }


    /**
     * 查询会议信息 包括主讲人信息 以及会议属性
     *
     * @param meetId
     * @return
     */
    @Override
    public Meet findMeet(String meetId) {
        Meet meet = meetDAO.selectByPrimaryKey(meetId);
        Lecturer lecturerCondition = new Lecturer();
        lecturerCondition.setMeetId(meetId);
        Lecturer lecturer = meetLecturerDAO.selectOne(lecturerCondition);
        if (lecturer != null) {
            meet.setLecturer(lecturer);
        }
        MeetProperty propertyCondition = new MeetProperty();
        propertyCondition.setMeetId(meetId);
        MeetProperty property = meetPropertyDAO.selectOne(propertyCondition);
        if (property != null) {
            meet.setMeetProperty(property);
        }
        // 查询象数、学分设置
        MeetSetting settingCondition = new MeetSetting();
        settingCondition.setMeetId(meetId);
        List<MeetSetting> settingList = meetSettingDAO.select(settingCondition);
        if (!CheckUtils.isEmpty(settingList)) {
            meet.setMeetSetting(MeetSetting.buildMeetSetting(settingList));
        }
        return meet;
    }

    /**
     * 检测会议是否是我的
     *
     * @param userId
     * @param meetId
     * @return
     */
    @Override
    public Boolean checkMeetIsMine(Integer userId, String meetId) {
        if (userId == null || userId == 0 || StringUtils.isEmpty(meetId)) {
            return false;
        }
        Meet meet = meetDAO.selectByPrimaryKey(meetId);
        if (meet == null) {
            return false;
        }
        boolean isMine = meet.getOwnerId().intValue() == userId.intValue();
        return isMine;
    }

    /**
     * 获取会议已参会人员数
     *
     * @param meetId
     * @return
     */
    @Override
    public Integer getMeetAttendCount(String meetId) {
        MeetAttend condition = new MeetAttend();
        condition.setMeetId(meetId);
        Integer count = meetAttendDAO.selectCount(condition);
        return count;
    }


    /**
     * 获取医生的会议历史(医生详情)
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetHistoryDTO> getMeetHistory(Pageable pageable) {
        startPage(pageable, Pageable.countPage);
        Page<MeetHistoryDTO> page = (Page<MeetHistoryDTO>) meetDAO.getMeetHistory(pageable.getParams());
        return toMyPage(page);
    }


    /**
     * 查询用户参会记录
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public MeetAttend findMeetAttend(String meetId, Integer userId) {
        MeetAttend condition = new MeetAttend();
        condition.setMeetId(meetId);
        condition.setUserId(userId);
        MeetAttend attend = meetAttendDAO.selectOne(condition);
        return attend;
    }

    /**
     * 修改用户参会记录
     *
     * @param attend
     */
    @Override
    public void updateMeetAttend(MeetAttend attend) {
        meetAttendDAO.updateByPrimaryKey(attend);
    }


    /**
     * 添加会议 并添加模块
     *
     * @param meet
     * @param funIds
     * @return
     */
    @Override
    public String addMeet(Meet meet, Integer[] funIds) throws SystemException {
        String meetId = addMeet(meet);
        if (funIds != null && funIds.length > 0) {
            for (Integer funid : funIds) {
                addMeetModule(meet, funid);
            }
        }
        return meetId;
    }

    private void addMeetModule(Meet meet, Integer funid) throws SystemException {
        MeetModule module = new MeetModule();
        module.setActive(true);
        module.setMeetId(meet.getId());
        module.setFunctionId(funid);
        addMeetModule(module);
    }

    /**
     * 复制新的模块表
     * @param meetId
     * @param module
     * @return
     */
    @Override
    public Integer addModuleReturnId(String meetId,MeetModule module) throws SystemException{
        MeetModule newModule = new MeetModule();
        newModule.setActive(true);
        newModule.setMeetId(meetId);
        newModule.setFunctionId(module.getFunctionId());
        if (module.getFunctionId() == MeetModule.ModuleFunction.PPT.getFunId()) {
            newModule.setMainFlag(true);
        }
        newModule.setModuleName(MeetModule.ModuleFunction.getFunctionById(module.getFunctionId()).getFunName());
        meetModuleDAO.insert(newModule);
        Integer moduleId = newModule.getId();
        return moduleId;
    }

    /**
     * 修改会议 并修改功能模块
     *
     * @param meet
     * @param funIds
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'meetinfo_'+#meet.id")
    public void updateMeet(Meet meet, Integer[] funIds) throws SystemException {
        updateMeet(meet);
        //更新会议所处的文件夹
        InfinityTreeDetail detail = new InfinityTreeDetail();
        detail.setResourceId(meet.getId());
        InfinityTreeDetail result = meetFolderService.selectTreeDetail(detail);
        String folderId = meet.getFolderId();
        if(result != null){  //原来有目录，如果没有点击按钮，不做任何操作
            if(!StringUtils.isEmpty(folderId) && !"0".equals(folderId)){ //点击单选框的非根目录,更新目录
                detail.setId(result.getId());
                detail.setInfinityId(folderId);
                detail.setResourceName(meet.getMeetName());
                meetFolderService.updateTreeDetail(detail);
            }else if("0".equals(folderId)){ //点击单选框的根目录，删除原有目录
                meetFolderService.deleteTreeDetail(result);
            }
        }else{  //原来没有目录，如果没有点击按钮，不做任何操作
            if(!StringUtils.isEmpty(folderId) && !"0".equals(folderId)){ //点击单选框的非根目录
                detail.setInfinityId(folderId);
                detail.setResourceName(meet.getMeetName());
                meetFolderService.insertDetail(detail);
            }
        }


        List<MeetModule> modules = findAllModules(meet.getId());
        Map<Integer, MeetModule> moduleMap = Maps.newHashMap();
        for (MeetModule mm : modules) {
            moduleMap.put(mm.getFunctionId(), mm);
        }
        Map<Integer, Boolean> funIdMap = Maps.newHashMap();
        for (Integer funId : funIds) {
            funIdMap.put(funId, true);
        }
        //增加或者修改
        for (Integer funid : funIds) {
            MeetModule module = moduleMap.get(funid);
            if (module == null) {
                addMeetModule(meet, funid);
            } else {
                if (!module.getActive()) {
                    module.setActive(true);
                    meetModuleDAO.updateByPrimaryKeySelective(module);
                }
            }
        }
        //删除(将module的active属性设置为false)
        for (MeetModule module : modules) {
            if (funIdMap.get(module.getFunctionId()) == null || funIdMap.get(module.getFunctionId()) == false) {
                module.setActive(false);
                meetModuleDAO.updateByPrimaryKeySelective(module);
            }
        }

    }

    /**
     * 查询会议的所有激活的模块
     *
     * @param meetId
     * @return
     */
    @Override
    public List<MeetModule> findModules(String meetId) {
        List<MeetModule> list = meetModuleDAO.findActiveModules(meetId);
        return list;
    }


    /**
     * 查询所有的模块
     *
     * @param meetId
     * @return
     */
    @Override
    public List<MeetModule> findAllModules(String meetId) {
        MeetModule condition = new MeetModule();
        condition.setMeetId(meetId);
        List<MeetModule> list = meetModuleDAO.select(condition);
        return list;
    }

    /**
     * 检测用户是否收藏了会议
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public Integer checkFavorite(String meetId, Integer userId) {
        Favorite condition = new Favorite();
        condition.setUserId(userId);
        condition.setResourceId(meetId);
        int count = meetFavoriteDAO.selectCount(condition);
        return count;
    }


    /**
     * 查询会议简单信息
     *
     * @param meetId
     * @return
     */
    @Override
    public Meet findMeetSimple(String meetId) {
        return meetDAO.selectByPrimaryKey(meetId);
    }

    public String findSignPos(String meetId) {
        String pos = meetDAO.findSignPos(meetId);
        return pos;
    }


    /**
     * 发布会议
     * @param meetId
     * @param acceptId  csp投稿过来的会议需要根据接收者id更改csp记录的发布状态
     * @return
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'meetinfo_'+#meetId")
    public void doPublish(String meetId,Integer acceptId) throws SystemException {
        Meet meet = findMeetInfoById(meetId);
        MeetProperty condition = new MeetProperty();
        condition.setMeetId(meetId);
        MeetProperty property = meetPropertyDAO.selectOne(condition);
        if (property.getStartTime().before(new Date())) {
            meet.setState(Meet.MeetType.IN_USE.getState());
        } else {
            meet.setState(Meet.MeetType.NOT_START.getState());
        }
        meetDAO.updateByPrimaryKeySelective(meet);

        //如果是转载csp会议，发布完成需要更改csp的发布状态
        MeetAudio audio = audioService.findMeetAudioByMeetId(meetId);
        if(audio != null){
            CourseDelivery delivery = new CourseDelivery();
            delivery.setSourceId(audio.getCourseId());
            delivery.setAcceptId(acceptId);
            delivery = courseDeliveryService.selectOne(delivery);
            if(delivery != null){
                delivery.setPublishState(true);
                courseDeliveryService.updateByPrimaryKey(delivery);
            }
        }


    }


    /**
     * 保存草稿会议
     * @param meetId
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'meetinfo_'+#meetId")
    public void saveDraftMeet(String meetId) throws SystemException {
        Meet meet = findMeetInfoById(meetId);
        meet.setState(Meet.MeetType.DRAFT.getState());
        meetDAO.updateByPrimaryKeySelective(meet);
    }

    private Meet findMeetInfoById(String meetId) throws SystemException {
        List<MeetModule> modules = findModules(meetId);
        //在会议拥有考试 问卷 或者签到的时候 需要检测是否设置好信息
        for (MeetModule module : modules) {
            switch (module.getFunctionId()) {
                case 3://考试
                    checkExam(meetId, module.getId());
                    break;
                case 4://问卷
                    checkSurvey(meetId, module.getId());
                    break;
                case 5://签到
                    checkPosition(meetId, module.getId());
                default:
                    break;
            }
        }

        Meet meet = meetDAO.selectByPrimaryKey(meetId);
        meet.setPublishTime(new Date());
        return meet;
    }

    private void checkPosition(String meetId, Integer moduleId) throws SystemException {
        MeetPosition condition = new MeetPosition();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        MeetPosition position = meetPositionDAO.selectOne(condition);
        if (position == null) {
            throw new SystemException("会场签到设置未完善");
        }
        if (position.getStartTime() == null || position.getEndTime() == null || position.getPositionLat() == null || position.getPositionLng() == null) {
            throw new SystemException("会议签到设置未完善");
        }
    }

    private void checkSurvey(String meetId, Integer moduleId) throws SystemException {
        MeetSurvey condition = new MeetSurvey();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        MeetSurvey survey = meetSurveyDAO.selectOne(condition);
        if (survey == null) {
            throw new SystemException("问卷模块未设置完善");
        }
        //检测试卷
        if (survey.getPaperId() == null || survey.getPaperId() == 0) {
            throw new SystemException("问卷信息未设置完善");
        }
        SurveyQuestion questionCondition = new SurveyQuestion();
        questionCondition.setPaperId(survey.getPaperId());
        int count = surveyQuestionDAO.selectCount(questionCondition);
        if (count == 0) {
            throw new SystemException("请设置问卷试题");
        }

    }


    private void checkExam(String meetId, Integer moduleId) throws SystemException {
        MeetExam examC = new MeetExam();
        examC.setMeetId(meetId);
        //examC.setModuleId(moduleId);
        MeetExam exam = meetExamDAO.selectOne(examC);
        if (exam == null) {
            throw new SystemException("考试模块未完善");
        }
        if (exam.getUsetime() == null || exam.getUsetime() == 0) {
            throw new SystemException("请设置考试用时");
        }

        //检测试卷
        if (exam.getPaperId() == null || exam.getPaperId() == 0) {
            throw new SystemException("考试试卷未完善");
        }
        ExamPaper paper = examPaperDAO.selectByPrimaryKey(exam.getPaperId());
        if (paper == null || paper.getTotalPoint() == null || paper.getTotalPoint() == 0) {
            throw new SystemException("考试试卷未完善");
        }
    }


    /**
     * 检测用户是否可以参与会议
     *
     * @param meetInfoDTO
     * @param userId
     * @return
     */
    @Override
    public boolean checkAttenable(MeetInfoDTO meetInfoDTO, Integer userId) throws SystemException {
        if (checkAttend(meetInfoDTO.getId(), userId)) {
            return true;
        }
        MeetProperty condition = new MeetProperty();
        condition.setMeetId(meetInfoDTO.getId());
        MeetProperty property = meetPropertyDAO.selectOne(condition);
        //checkStartTime(property);
        checkAttendLimit(property, meetInfoDTO.getId());
        if (property.getGroupIds() != null && property.getGroupIds().length > 0) {
            checkMember(property, meetInfoDTO.getId(), userId);
        }
        checkZone(property, userId);
        return true;
    }

    /**
     * 删除会议
     * 同时删除会议属性、会议模块、
     * 会议主讲信息
     *
     * @param meetId
     */
    @Override
    public void deleteMeet(String meetId) {
        //删除会议主题
        meetDAO.deleteByPrimaryKey(meetId);
        //删除会议属性
        MeetProperty property = new MeetProperty();
        property.setMeetId(meetId);
        meetPropertyDAO.delete(property);
        MeetSetting setting = new MeetSetting();
        setting.setMeetId(meetId);
        meetSettingDAO.delete(setting);
        //删除会议模块
        MeetModule meetModule = new MeetModule();
        meetModule.setMeetId(meetId);
        meetModuleDAO.delete(meetModule);

    }

    @Override
    public void doModifyState() {
        meetDAO.modifyStartState();
        meetDAO.modifyEndState();
    }


    /**
     * 根据问卷id获取用户信息
     *
     * @param map
     * @return
     */
    @Override
    public MeetSurveyDetailDTO findUserInfo(Map<String, Object> map) {
        MeetSurveyDetailDTO dto = meetSurveyDAO.findUserInfo(map);
        return dto;
    }

    /**
     * 根据考试id获取用户信息
     *
     * @param map
     * @return
     */
    @Override
    public MeetExamDetailDTO getUserInfo(Map<String, Object> map) {
        MeetExamDetailDTO dto = meetExamDAO.getUserInfo(map);
        return dto;
    }

    /**
     * 更新会议收藏状态
     *
     * @param meetFavorite
     * @return
     */
    @Override
    public void updateFavoriteStatus(Favorite meetFavorite) {
        Favorite result = meetFavoriteDAO.selectOne(meetFavorite);
        boolean stored = false;
        if (result == null) { //执行收藏操作
            meetFavorite.setStoreTime(new Date());
            meetFavoriteDAO.insertSelective(meetFavorite);
            stored = true;
        } else {  //执行取消收藏操作
            meetFavoriteDAO.delete(result);
        }
        if (meetFavorite.getResourceType().intValue() == Favorite.FavoriteType.meet.ordinal()){
            meetNotifyService.addMeetNotify(meetFavorite.getUserId(), meetFavorite.getResourceId(), stored);
        }
    }


    /**
     * 添加或修改用户学习进度
     *
     * @param record
     */
    @Override
    public void saveOrUpdateLearnRecord(MeetLearningRecord record) {
        String meetId = record.getMeetId();
        Integer userId = record.getUserId();
        int ppt_funcId = MeetModule.ModuleFunction.PPT.getFunId();
        if (record.getFunctionId() != ppt_funcId
                && record.getFunctionId() != MeetModule.ModuleFunction.VIDEO.getFunId()) {
            record.setCompleteProgress(100);
        }

        MeetLearningRecord condition = new MeetLearningRecord();
        condition.setFunctionId(record.getFunctionId());
        condition.setMeetId(meetId);
        condition.setUserId(userId);
        MeetLearningRecord oldProgressRecord = meetLearningRecordDAO.selectOne(condition);
        // 查询用户是否已经有学习进度
        if (oldProgressRecord != null) {
            // 设置新的学习进度及学习总时长
            oldProgressRecord.setCompleteProgress(record.getCompleteProgress());
            if (oldProgressRecord.getUsedTime() != null) {
                oldProgressRecord.setUsedTime(oldProgressRecord.getUsedTime() + record.getUsedTime());
            } else {
                oldProgressRecord.setUsedTime(record.getUsedTime());
            }
            meetLearningRecordDAO.updateByPrimaryKey(oldProgressRecord);
        } else {// 没有 则添加记录
            meetLearningRecordDAO.insert(record);
        }

        // 如果会议有奖励象数或学分 且学习进度为100% ，需添加奖励象数或学分记录
        Integer learningProgress = findUserLearningRecord(meetId, userId);
        if (learningProgress.intValue() == 100) {
            Meet meet = meetDAO.selectByPrimaryKey(meetId);
            //  奖励象数
            if (meet != null ) {
                try {
                    saveAwardUserXs(meet,userId);
                } catch (NotEnoughCreditsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 奖励用户象数
     * @param meet
     * @param userId
     */
    private void saveAwardUserXs(Meet meet,Integer userId) throws NotEnoughCreditsException{

        MeetSetting st = new MeetSetting();
        st.setMeetId(meet.getId());
        st.setPropType(MeetSetting.propTypeLabel.XS.getPropType());
        MeetSetting setting = meetSettingDAO.selectOne(st);

        MeetRewardHistory history = new MeetRewardHistory();
        history.setMeetId(meet.getId());
        history.setRewardType(MeetRewardHistory.rewardLabel.XS.getRewardType());
        // 已经奖励的人数
        int awardCount = meetRewardHistoryDAO.selectCount(history);

        if (setting != null && setting.getRewardLimit().intValue() > awardCount){
            AppUser user = appUserService.selectByPrimaryKey(userId);
            CreditPayDTO payDTO = new CreditPayDTO();
            payDTO.setAccepter(userId);
            payDTO.setPayer(meet.getOwnerId());
            payDTO.setCredits(setting.getPropValue());
            payDTO.setPayerDescrib("用户 " + user.getNickname() + " 参加您的会议,您支付了" + setting.getPropValue() + "个象数作为奖励");
            payDTO.setAccepterDescrib("您参加了 " + "‘" + meet.getMeetName() + "’, " + meet.getOrganizer() + "奖励您" + setting.getPropValue() + "个象数");
            creditsService.executeAwardCredits(payDTO);
            saveRewardHistory(setting,userId);
        }
    }

    /**
     * 添加象数或学分奖励
     *
     * @param setting
     * @param userId
     */
    private void saveRewardHistory(MeetSetting setting, Integer userId) {
        Integer propMode = setting.getPropMode();
        MeetRewardHistory rewardHistory = new MeetRewardHistory();
        rewardHistory.setUserId(userId);
        rewardHistory.setMeetId(setting.getMeetId());

        if (propMode.equals(MeetSetting.modeLabel.REWARD_XS.getPropMode())) {
            rewardHistory.setRewardType(MeetRewardHistory.rewardLabel.XS.getRewardType());
        } else if (propMode.equals(MeetSetting.modeLabel.ONE_CREDIT.getPropMode())
                || propMode.equals(MeetSetting.modeLabel.TWO_CREDIT.getPropMode())) {
            rewardHistory.setRewardType(MeetRewardHistory.rewardLabel.CREDIT.getRewardType());
        }
        MeetRewardHistory history = meetRewardHistoryDAO.selectOne(rewardHistory);
        if (history == null) {
            rewardHistory.setRewardPoint(setting.getPropValue());
            rewardHistory.setRewardTime(new Date());
            meetRewardHistoryDAO.insert(rewardHistory);
        }
    }


    public List<MeetLearningRecord> findLearningRecord(String meetId,Integer userId){
        List<MeetLearningRecord> learningList = meetLearningRecordDAO.findLearningRecordList(meetId, userId);
        return learningList;
    }

    /**
     * 设置用户学习记录
     * @param folderDTO
     * @param userId
     */
    public void setUserLearningRecord(MeetFolderDTO folderDTO,Integer userId){
        if (folderDTO.getType() == MeetFolderDTO.FolderType.meet.ordinal()
                && (folderDTO.getState().intValue() == Meet.MeetType.IN_USE.getState().intValue()
                || folderDTO.getState().intValue() == Meet.MeetType.OVER.getState().intValue())) {
            folderDTO.setCompleteProgress(findUserLearningRecord(folderDTO.getId(),userId));
        }
    }

    protected int calCompleteProgress(List<MeetLearningRecord> records){
        float completeProgress = 0.0f;
        float remainRate = 1f;//剩余模块占比
        float pptRate = 0.5f;//ppt模块占比
        int avgSize = records.size();//记录条数
        int currentProgress = 0; //每条记录当前的进度

        for (MeetLearningRecord record : records){
            if(record.getCompleteProgress() != null) {
                currentProgress = record.getCompleteProgress();
                if (record.getFunctionId() == MeetModule.ModuleFunction.PPT.getFunId()){
                    completeProgress += (records.size() > 1 ? pptRate : remainRate) * currentProgress;
                    avgSize = avgSize - 1;
                    remainRate = pptRate;
                } else {
                    completeProgress += (records.size() > 1 ? remainRate / avgSize : 1) * currentProgress;
                }
            }
        }
        return Math.round(completeProgress > 100 ? 100 : completeProgress);
    }

    /**
     * 查询用户学习进度
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public Integer findUserLearningRecord(String meetId, Integer userId) {
        List<MeetLearningRecord> learningList = findLearningRecord(meetId, userId);
        int completeProgress = 0;
        if (!CheckUtils.isEmpty(learningList)) {
            completeProgress = calCompleteProgress(learningList);
        }
        return completeProgress;
    }


    /**
     * 查询用户是否有获取会议象数或学分奖励的记录
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public List<MeetRewardHistory> findUserGetRewardHistory(String meetId, Integer userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("meetId", meetId);
        params.put("userId", userId);
        List<MeetRewardHistory> rewardHistoryList = meetRewardHistoryDAO.findUserGetRewardHistory(params);
        return rewardHistoryList == null ? null : rewardHistoryList;
    }


    /**
     * 查询会议奖励象数或学分属性内容
     *
     * @param params
     * @return
     */
    @Override
    public List<MeetSetting> findMeetSetting(Map<String,Object> params) {
        List<MeetSetting> setting = meetDAO.findMeetSetting(params);
        return setting == null ? null : setting;
    }

    /**
     * 查询已经奖励的人数
     *
     * @param meetId
     * @param rewardType
     * @return
     */
    @Override
    public Integer findGetRewardUserCount(String meetId, Integer rewardType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("meetId", meetId);
        params.put("rewardType", rewardType);
        return meetRewardHistoryDAO.findGetRewardUserCount(params);
    }

    /**
     * 查询用户收藏的会议
     * 并包含每个会议的用户学习记录
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetInfoDTO> findMeetFavorite(Pageable pageable) {
        Integer userId = (Integer) pageable.get("userId");
        startPage(pageable, Pageable.countPage);
        MyPage<MeetInfoDTO> page = toMyPage((Page) meetDAO.findMeetFavorite(pageable.getParams()));
        if (!CheckUtils.isEmpty(page.getDataList())) {
            Integer completeProgress;
            for (MeetInfoDTO meetInfoDTO : page.getDataList()) {
                //设置完成进度
                completeProgress = findUserLearningRecord(meetInfoDTO.getId(), userId).intValue();
                if (completeProgress != null && completeProgress != 0) {
                    meetInfoDTO.setCompleteProgress(completeProgress);
                }
                MeetCheckHelper.checkMeetInfo(meetInfoDTO, appFileBase);
            }
        }
        return page;
    }


    /**
     * 用户查询会议详情
     *
     * @param meetId
     * @param userId
     * @return
     */
    @Override
    public MeetInfoDTO findFinalMeetInfo(String meetId, Integer userId) {
        MeetInfoDTO meetInfoDTO = findMeetInfo(meetId);
        if (meetInfoDTO == null) {
            return null;
        }
        // 奖励象数类型
        int rewardTypeXS = MeetRewardHistory.rewardLabel.XS.getRewardType();
        // 奖励学分类型
        int rewardTypeCredit = MeetRewardHistory.rewardLabel.CREDIT.getRewardType();

        // 收藏标记
        if (userId != null){
            if (meetInfoDTO.getState() == Meet.MeetType.IN_USE.getState().intValue()
                    || meetInfoDTO.getState() == Meet.MeetType.OVER.getState().intValue()) {
                // 设置用户学习进度
                Integer completeProgress = findUserLearningRecord(meetId, userId);
                if (completeProgress != null && completeProgress > 0) {
                    meetInfoDTO.setCompleteProgress(completeProgress);
                }

                // 学习进度为百分百的才能获得象数或学分
                if (completeProgress == 100) {
                    List<MeetRewardHistory> rewardHistoryList = findUserGetRewardHistory(meetId, userId);
                    if (!CheckUtils.isEmpty(rewardHistoryList)) {
                        for (MeetRewardHistory rewardHistory : rewardHistoryList) {
                            if (rewardHistory.getRewardType() == rewardTypeXS) {
                                meetInfoDTO.setReceiveAwardXs(rewardHistory.getRewardPoint());
                            } else if (rewardHistory.getRewardType() == rewardTypeCredit) {
                                meetInfoDTO.setReceiveAwardCredit(rewardHistory.getRewardPoint());
                            }
                        }
                    }
                }
            }
            // 是否收藏
            meetInfoDTO.setStored(checkFavorite(meetId, userId));
            // 是否参加过
            boolean attended = checkAttend(meetId, userId);
            meetInfoDTO.setAttended(attended);
            // 是否可以参加
            boolean attendAble = false;
            try {
                if (userId != null){
                    attendAble = checkAttenable(meetInfoDTO, userId);
                    meetInfoDTO.setAttendAble(attendAble);
                }
            } catch (SystemException e) {
                e.printStackTrace();
                meetInfoDTO.setAttendAble(false);
                meetInfoDTO.setReason(e.getMessage());
            }
        }

        // 奖励象数设置
        Boolean requiredXs = meetInfoDTO.getRequiredXs();
        if (requiredXs != null && requiredXs == true) { // 奖励象数
            // 查询已经奖励象数人数
            int getRewardXsCount = findGetRewardUserCount(meetId, rewardTypeXS);
            // 剩余奖励象数人数
            meetInfoDTO.setRemainAwardXsCount(getRewardXsCount > meetInfoDTO.getAwardLimit() ? 0 : meetInfoDTO.getAwardLimit() - getRewardXsCount);
        }

        // 奖励学分设置
        Boolean rewardCredit = meetInfoDTO.getRewardCredit();
        if (rewardCredit != null && rewardCredit == true) {
            // 查询会议已经奖励学分人数
            int getRewardCreditCount = findGetRewardUserCount(meetId, rewardTypeCredit);
            // 剩余奖励学分人数
            meetInfoDTO.setRemainAwardCreditCount(getRewardCreditCount > meetInfoDTO.getAwardCreditLimit() ? 0 : meetInfoDTO.getAwardCreditLimit() - getRewardCreditCount);
        }

        // 返回该会议从哪个单位号转载的
        Integer primitiveId = meetInfoDTO.getPrimitiveId();
        if (primitiveId != null && primitiveId != 0) {
            AppUser condition = new AppUser();
            condition.setId(primitiveId);
            AppUser unitUser = appUserService.selectByPrimaryKey(condition);
            meetInfoDTO.setReprintFromUnitUser(unitUser.getNickname());
        }

        MeetCheckHelper.checkMeetInfo(meetInfoDTO, appFileBase);
        return meetInfoDTO;
    }


    /**
     * 微信查询推荐会议
     * @param userId
     * @return
     */
    public List<MeetFolderDTO> findRecommendMeetFolder(Integer userId) {
        List<MeetFolderDTO> list = Lists.newArrayList();
        //首先查询出固定的一个推荐
        MeetFolderDTO fixedMeetFolder = meetDAO.findFixedRecommend();
        //如果是会议文件夹的情况 需要查询出下面的3个会议的主讲者
        int maxLecturerLimit = 3;
        if (fixedMeetFolder != null && fixedMeetFolder.getType() == 0){
            fixedMeetFolder.setLecturerList(meetDAO.findRecommendLecturers(fixedMeetFolder.getId(), maxLecturerLimit));
        }
        list.add(fixedMeetFolder);
        //查询出推荐的随机四个推荐的会议
        int recommendCount = Constants.NUMBER_FOUR;
        List<MeetFolderDTO> recommendList = meetDAO.findRandomRecommendMeets(recommendCount);
        list.addAll(recommendList);
        //如果不够四个 则在后面的三个的基础加上缺失量
        int diff = recommendCount - recommendList.size();
        //从会议表中随机抽取三个跟用户相关的会议
        int randomLimit = Constants.NUMBER_THREE + diff;
        list.addAll(findRandomLimitMeets(userId, randomLimit));
        for (MeetFolderDTO meetFolderDTO : list){
            MeetCheckHelper.checkMeetFolder(meetFolderDTO, appFileBase);
        }
        return list;
    }


    protected List<MeetFolderDTO> findRandomLimitMeets(Integer userId, int limit){
        SearchRandomMeetDTO searchRandomMeetDTO = new SearchRandomMeetDTO();
        searchRandomMeetDTO.setRandLimit(limit);
        if (userId != null && userId != 0) {
            AppUser appUser = appUserService.selectByPrimaryKey(userId);
            if (appUser != null) {
                AppDoctor doctor = (AppDoctor) appUserService.findUserDetail(userId, appUser.getRoleId());
                String specialtyName = doctor.getSpecialtyName();
                if (!CheckUtils.isEmpty(specialtyName)) {
                    String[] snArray = specialtyName.split(" ");
                    specialtyName = snArray.length == 2 ? snArray[1] : snArray[0];
                    searchRandomMeetDTO.setDepart(specialtyName);
                }

                String province = appUser.getProvince();
                if (!CheckUtils.isEmpty(province)){
                    province = province.substring(0, 2);
                    searchRandomMeetDTO.setProvince(province);
                }

                String city = appUser.getCity();
                if (!CheckUtils.isEmpty(city)){
                    city = province.substring(0, 2);
                    searchRandomMeetDTO.setCity(city);
                }
            }
        }
        searchRandomMeetDTO.check();
        List<MeetFolderDTO> list = meetDAO.findRandomMeets(searchRandomMeetDTO);
        if (list.size() < limit){
            searchRandomMeetDTO.clear();
            list = meetDAO.findRandomMeets(searchRandomMeetDTO);
        }
        return list;
    }


    @Override
    public MeetFolderDTO getMeetFolder(String id) {
        return meetDAO.getMeetFolder(id);
    }


    /**
     * 复制会议课程及明细
     * @param oldMeetId
     * @param newMeetId
     * @throws SystemException
     */
    @Override
    public void copyMeetModuleCourse(String oldMeetId, String newMeetId) throws SystemException {
        // 查询会议所有模块
        List<MeetModule> moduleList = findModules(oldMeetId);
        if (!CheckUtils.isEmpty(moduleList)) {
            for (MeetModule oldMeetModule : moduleList) {
                // 复制模块表 返回新的模块id
                Integer newModuleId = addModuleReturnId(newMeetId, oldMeetModule);

                // 获取草稿会议功能id
                int oldFunctionId = oldMeetModule.getFunctionId();

                // 查询草稿会议的课程id
                Integer oldCourseId = findModuleCourseId(oldMeetModule);
                if (oldCourseId != null && oldCourseId != 0) {
                    // 根据课程课程id查询 草稿会议的课程表 并复制创建新的会议课程表获取新的课程ID
                    if (oldFunctionId == MeetModule.ModuleFunction.PPT.getFunId()) {

                        // 复制PPT课程会议
                        copyAudioCourse(oldCourseId, newModuleId, newMeetId);

                    } else if (oldFunctionId == MeetModule.ModuleFunction.VIDEO.getFunId()) {

                        // 复制视频课程会议
                        copyVideoCourse(oldCourseId, newModuleId, newMeetId);

                    } else if (oldFunctionId == MeetModule.ModuleFunction.EXAM.getFunId()) {

                        // 复制考试课程会议
                        MeetExam oldExam = examService.findExam(oldMeetId, oldMeetModule.getId());
                        if (oldExam != null) {
                            copyExam(oldExam, newModuleId, newMeetId);
                        }

                    } else if (oldFunctionId == MeetModule.ModuleFunction.SURVEY.getFunId()) {

                        // 复制问卷调查课程会议
                        copySurvey(oldCourseId, newModuleId, newMeetId);

                    } else {
                        // 复制会议签到数据
                        copyMeetSign(oldMeetId, newMeetId, oldMeetModule.getId(), newModuleId);

                    }
                }
            }
        }
    }


    /**
     * 复制会议ppt语音课程及明细
     * @param oldCourseId
     * @param newModuleId
     * @param newMeetId
     */
    @Override
    public void copyAudioCourse(Integer oldCourseId, Integer newModuleId, String newMeetId) {
        AudioCourse oldAudioCourse = null;
        if (oldCourseId != null && oldCourseId != 0) {
            // 查询PPT语音课程
            oldAudioCourse = audioService.findAudioCourse(oldCourseId);
        }
        Integer newCourseId = 0;
        if (oldAudioCourse != null) {
            // 复制PPT语音课程
            AudioCourse newAudioCourse = oldAudioCourse;
            newAudioCourse.setId(null);
            audioService.insert(newAudioCourse);

            newCourseId = newAudioCourse.getId();
            // 复制PPT明细表
            if (!CheckUtils.isEmpty(oldAudioCourse.getDetails())) {
                List<AudioCourseDetail> oldDetailList = oldAudioCourse.getDetails();
                for (AudioCourseDetail detail : oldDetailList) {
                    detail.setId(null);
                    detail.setCourseId(newCourseId);
                }
                audioService.insertBatch(oldDetailList);
            }
        }

        // 复制PPT语音会议表
        MeetAudio condition = new MeetAudio();
        condition.setMeetId(newMeetId);
        condition.setModuleId(newModuleId);
        MeetAudio newAudio = meetAudioDAO.selectOne(condition);
        if (newAudio == null) { // 从草稿箱复制会议过来
            condition.setId(null);
            condition.setCourseId(newCourseId);
            audioService.addMeetAudio(condition);
        } else {
            // 发布会议从已获取会议 引用复制
            newAudio.setCourseId(newCourseId);
            audioService.updateMeetAudio(newAudio);
        }

    }


    /**
     * 复制会议视频课程及明细
     * @param oldCourseId
     * @param newModuleId
     * @param newMeetId
     */
    @Override
    public void copyVideoCourse(Integer oldCourseId, Integer newModuleId, String newMeetId) {
        VideoCourse videoCourse = new VideoCourse();
        videoCourse.setId(oldCourseId);
        VideoCourse oldVideoCourse = videoService.selectByPrimaryKey(videoCourse);
        Integer newCourseId = 0;
        if (oldVideoCourse != null) {
            // 复制视频课程
            VideoCourse newVideoCourse = oldVideoCourse;
            newVideoCourse.setId(null);
            videoService.insert(newVideoCourse);
            newCourseId = newVideoCourse.getId();
        }

        // 复制视频表
        MeetVideo newVideo = new MeetVideo();
        newVideo.setMeetId(newMeetId);
        newVideo.setModuleId(newModuleId);
        newVideo.setCourseId(newCourseId);
        videoService.insertMeetVideo(newVideo);

        List<VideoCourseDetail> oldDetailList = videoService.findRootDetail(oldCourseId);
        if (!CheckUtils.isEmpty(oldDetailList)) {
            for (VideoCourseDetail detail : oldDetailList) {
                Integer oldPreId = detail.getId();
                List<VideoCourseDetail> oldLeafDetailList = videoService.findByPreid(oldPreId);
                // 复制父目录数据
                detail.setId(null);
                detail.setCourseId(newCourseId);
                Integer newPreId = videoService.addDetail(detail);
                // 复制子目录数据
                if (!CheckUtils.isEmpty(oldLeafDetailList)) {
                    for (VideoCourseDetail leafDetail : oldLeafDetailList) {
                        leafDetail.setId(null);
                        leafDetail.setCourseId(newCourseId);
                        leafDetail.setPreId(newPreId);
                        videoService.addDetail(leafDetail);
                    }
                }
            }
        }
    }

    /**
     * 复制会议考题数据
     * @param oldExam
     * @param newModuleId
     * @param newMeetId
     */
    @Override
    public void copyExam(MeetExam oldExam, Integer newModuleId, String newMeetId) {
        Integer oldPaperId = oldExam.getPaperId();
        ExamPaper oldExamPaper = examService.findExamPaper(oldPaperId);
        if (oldExamPaper != null) {
            ExamPaper newExamPaper = oldExamPaper;
            newExamPaper.setId(null);
            Integer newPaperId = examService.addPaper(newExamPaper);

            MeetExam newExam = oldExam;
            newExam.setId(null);
            newExam.setPaperId(newPaperId);
            newExam.setModuleId(newModuleId);
            newExam.setMeetId(newMeetId);
            examService.insert(newExam);

            List<ExamPaperQuestion> oldQuestionList = examService.findQuestionListByPaperId(oldPaperId);
            if (!CheckUtils.isEmpty(oldQuestionList)) {
                for (ExamPaperQuestion paperQuestion : oldQuestionList) {
                    Integer oldQuestionId = paperQuestion.getQuestionId();
                    ExamQuestion oldQuestion = examService.findQuestion(paperQuestion.getPaperId(), oldQuestionId);
                    ExamQuestion newQuestion = oldQuestion;
                    newQuestion.setId(null);
                    Integer newQuestionId = examService.addQuestion(newQuestion);

                    paperQuestion.setId(null);
                    paperQuestion.setPaperId(newPaperId);
                    paperQuestion.setQuestionId(newQuestionId);
                    examService.insertExamPaperQuestion(paperQuestion);
                }
            }
        }
    }

    /**
     * 复制会议问卷数据
     * @param oldPaperId
     * @param newModuleId
     * @param newMeetId
     */
    @Override
    public void copySurvey(Integer oldPaperId, Integer newModuleId, String newMeetId) {
        SurveyPaper oldSurvey = surveyService.findSurveyPaper(oldPaperId);
        if (oldSurvey != null) {
            SurveyPaper newSurveyPaper = oldSurvey;
            newSurveyPaper.setId(null);
            Integer newPaperId = surveyService.addSurveyPaper(newSurveyPaper);

            MeetSurvey newSurvey = new MeetSurvey();
            newSurvey.setPaperId(newPaperId);
            newSurvey.setModuleId(newModuleId);
            newSurvey.setMeetId(newMeetId);
            surveyService.insert(newSurvey);

            List<SurveyQuestion> oldQuestionList = oldSurvey.getQuestionList();
            for (SurveyQuestion question : oldQuestionList) {
                question.setId(null);
                question.setPaperId(newPaperId);
                surveyService.addQuestion(question);
            }

        }
    }

    /**
     * 复制签到会议数据
     * @param oldMeetId
     * @param newMeetId
     * @param oldModuleId
     * @param newModuleId
     */
    @Override
    public void copyMeetSign(String oldMeetId, String newMeetId, Integer oldModuleId, Integer newModuleId) {
        MeetPosition oldPosition = signService.findPosition(oldMeetId, oldModuleId);
        if (oldPosition != null) {
            MeetPosition position = oldPosition;
            position.setId(null);
            position.setMeetId(newMeetId);
            position.setModuleId(newModuleId);
            signService.insert(position);
        }
    }

    /**
     * 根据会议名称得到会议
     * @param meetName
     * @return
     */
    @Override
    public Meet selectByMeetName(String meetName) {
        List<Meet> meets= meetDAO.selectByMeetName(meetName);
        if (meets != null){
            return meets.get(0);
        }
        return null;
    }

    @Override
    public List<Meet> selectAllMeet() {
        return meetDAO.selectAllMeet();
    }

    @Override
    public MyPage<Meet> selectMeetList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<Meet> page = MyPage.page2Mypage((Page) meetDAO.selectMeetList(pageable.getParams()));
        return page;
    }


    @Override
    public void doStartMeet(Integer courseId) {
        MeetAudio cond = new MeetAudio();
        cond.setCourseId(courseId);
        List<MeetAudio> list = meetAudioDAO.select(cond);
        if (!CheckUtils.isEmpty(list)){
            Meet meet;
            MeetProperty property;
            for (MeetAudio meetAudio : list) {
                meet = new Meet();
                meet.setId(meetAudio.getMeetId());
                meet.setState(Meet.MeetType.IN_USE.getState());
                meetDAO.updateByPrimaryKeySelective(meet);

                property = meetPropertyDAO.findProperty(meetAudio.getMeetId());
                property.setStartTime(new Date());
                property.setEndTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)));
                meetPropertyDAO.updateByPrimaryKey(property);
            }
        }
    }

    @Override
    public void doEndMeet(Integer courseId) {
        MeetAudio cond = new MeetAudio();
        cond.setCourseId(courseId);
        List<MeetAudio> list = meetAudioDAO.select(cond);
        if (!CheckUtils.isEmpty(list)){
            Meet meet;
            MeetProperty property;
            for (MeetAudio meetAudio : list) {
                meet = new Meet();
                meet.setId(meetAudio.getMeetId());
                meet.setState(Meet.MeetType.OVER.getState());
                meetDAO.updateByPrimaryKeySelective(meet);

                property = meetPropertyDAO.findProperty(meetAudio.getMeetId());
                property.setEndTime(new Date());
                meetPropertyDAO.updateByPrimaryKey(property);
            }
        }
    }

    @Override
    public Meet selectById(String id) {
        return meetDAO.selectById(id);
    }
}
