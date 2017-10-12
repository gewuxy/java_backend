package cn.medcn.meet.service;

import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.user.model.Favorite;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/20.
 */
public interface MeetService extends BaseService<Meet> {

    String MEETINFO_CACHE_KEY = "meetinfo_";

    Integer MEET_CACHE_EXPIRE = 24*3600;

    String MEET_PROPERTY_CACHE_KEY = "meetproperty_";

    /**
     * 查询推荐会议信息 分页
     * @param pageable
     * @param userId
     * @return
     */
    MyPage<MeetTuijianDTO> findMeetTuijian(Pageable pageable, Integer userId);

    /**
     * 已关注的会议类型统计
     * @param userId
     * @return
     */
    List<MeetTypeState> findMeetTypes(Integer userId);

    /**
     * 添加会议推荐信息
     * @param tuijian
     */
    void addMeetTuijian(MeetTuijian tuijian)throws SystemException;

    /**
     * 添加会议模块
     * @param module
     */
    void addMeetModule(MeetModule module)throws SystemException;



    /**
     * 查询会议详情
     * @param meetId
     * @return
     */
    MeetInfoDTO findMeetInfo(String meetId) ;

    /**
     * 按关键字搜索会议
     * @return
     */
    MyPage<MeetInfoDTO> searchMeetInfo(Pageable pageable);

    /**
     * 按关键字搜索会议文件夹及会议列表 分页
     * @param pageable
     * @return
     */
    MyPage<MeetFolderDTO> searchMeetFolderInfo(Pageable pageable);

    /**
     * 分页查询我关注过的公众号的会议
     * @param pageable
     * @return
     */
    MyPage<MeetInfoDTO> findMyMeets(Pageable pageable);

    /**
     * 参与会议
     * @param meetId
     * @param moduleId
     * @param userId
     */
    void executeAttend(String meetId, Integer moduleId, Integer userId) throws SystemException,NotEnoughCreditsException;

    /**
     * 判断用户是否已经存在会议记录
     * @param meetId
     * @param userId
     * @return
     */
    boolean checkAttend(String meetId, Integer userId);

    /***
     * 获取会议属性
     * @param meetId
     * @return
     */
    MeetProperty findMeetProperty(String meetId);

    /**
     * 增加会议限制属性
     * @param meetProperty
     */
    void insertMeetProp(MeetProperty meetProperty);

    /**
     * 修改会议限制属性
     * @param meetProperty
     */
    void updateMeetProp(MeetProperty meetProperty);

    /**
     * 添加或修改会议象数、学分设置
     * @param setting
     */
    void saveOrUpdateMeetSetting(MeetSetting setting,String meetId);

    /**
     * 添加会议主讲者
     * @param lecturer
     */
    int addMeetLecturer(Lecturer lecturer);

    /**
     * 修改会议信息
     * @param meet
     */
    void updateMeet(Meet meet);

    /**
     * 修改会议 并修改功能模块
     * @param meet
     * @param funIds
     */
    void updateMeet(Meet meet, Integer[] funIds)  throws SystemException ;


    void storeMeet(Favorite meetStore);

    void cancelFavorite(Favorite meetStore);

    MyPage<MeetInfoDTO> findMyFavorite(Pageable pageable);

    /**
     * 查询已发布列表
     * @param pageable
     * @return
     */
    MyPage<MeetListInfoDTO> findPublished(Pageable pageable);

    MyPage<MeetListInfoDTO> findMeetForSend(Pageable pageable);

    MyPage<MeetListInfoDTO> findPublishedHasCount(Pageable pageable);

    /**
     * 查询草稿箱列表
     * @param pageable
     * @return
     */
    MyPage<MeetListInfoDTO> findDraft(Pageable pageable);

    /**
     * 添加会议
     * @param meet
     */
    String addMeet(Meet meet);

    String addMeetInfo(Meet meet);
    /**
     * 添加会议 并添加模块
     * @param meet
     * @param funIds
     * @return
     */
    String addMeet(Meet meet, Integer[] funIds)  throws SystemException ;

    /**
     * 查询会议信息 包括主讲人信息 以及会议属性
     * @param meetId
     * @return
     */
    Meet findMeet(String meetId);

    /**
     * 查询会议简单信息
     * @param meetId
     * @return
     */
    Meet findMeetSimple(String meetId);

    /**
     * 检测会议是否是我的
     * @param userId
     * @param meetId
     * @return
     */
    Boolean checkMeetIsMine(Integer userId, String meetId);

    /**
     * 获取会议已参会人员数
     * @param meetId
     * @return
     */
    Integer getMeetAttendCount(String meetId);

    /**
     * 获取医生的会议历史(医生详情)
     * @param pageable
     * @return
     */
    MyPage<MeetHistoryDTO> getMeetHistory(Pageable pageable);


    /**
     * 查询用户参会记录
     * @param meetId
     * @param userId
     * @return
     */
    MeetAttend findMeetAttend(String meetId, Integer userId);

    /**
     * 修改用户参会记录
     * @param attend
     */
    void updateMeetAttend(MeetAttend attend);

    /**
     * 查询会议的所有模块
     * @param meetId
     * @return
     */
    List<MeetModule> findModules(String meetId);

    /**
     * 查询所有的模块
     * @param meetId
     * @return
     */
    List<MeetModule> findAllModules(String meetId);

    /**
     * 检测用户是否收藏了会议
     * @param meetId
     * @param userId
     * @return
     */
    Integer checkFavorite(String meetId, Integer userId);


    String findSignPos(String meetId);

    /**
     * 发布会议
     * @param meetId
     * @return
     */
    void doPublish(String meetId) throws SystemException;

    /**
     * 保存草稿会议
     * @param meetId
     * @throws SystemException
     */
    void saveDraftMeet(String meetId) throws SystemException;

    /**
     * 检测用户是否可以参与会议
     * @param meetInfoDTO
     * @param userId
     * @return
     */
    boolean checkAttenable(MeetInfoDTO meetInfoDTO, Integer userId)  throws SystemException;

    /**
     * 删除会议
     * 同时删除会议属性、会议模块
     * @param meetId
     */
    void deleteMeet(String meetId);


    void doModifyState();


    /**
     * 根据问卷id获取用户信息
     * @param map
     * @return
     */
    MeetSurveyDetailDTO findUserInfo(Map<String, Object> map);


    /**
     * 根据考试id获取用户信息
     * @param map
     * @return
     */
    MeetExamDetailDTO getUserInfo(Map<String, Object> map);

    /**
     * 更新会议收藏状态
     * @param meetFavorite
     * @return
     */
    void updateFavoriteStatus(Favorite meetFavorite);

    /**
     * 添加或修改用户学习进度
     * @param record
     */
    void saveOrUpdateLearnRecord(MeetLearningRecord record);

    /**
     * 查询用户学习进度
     * @param meetId
     * @param userId
     * @return
     */
    Integer findUserLearningRecord(String meetId,Integer userId);

    List<MeetLearningRecord> findLearningRecord(String meetId,Integer userId);

    /**
     *  设置用户学习记录
     * @param folderDTO
     * @param userId
     */
    void setUserLearningRecord(MeetFolderDTO folderDTO,Integer userId);

    /**
     * 查询用户是否有获取会议象数或学分奖励的记录
     * @param meetId
     * @param userId
     * @return
     */
    List<MeetRewardHistory> findUserGetRewardHistory(String meetId,Integer userId);

    /**
     * 查询会议奖励象数或学分属性内容
     * @param params
     * @return
     */
    List<MeetSetting> findMeetSetting(Map<String,Object> params);

    /**
     * 查询已经获得奖励的用户数
     * @param meetId
     * @param rewardType
     * @return
     */
    Integer findGetRewardUserCount(String meetId,Integer rewardType);

    /**
     * 查询用户收藏的会议
     * @param pageable
     * @return
     */
    MyPage<MeetInfoDTO> findMeetFavorite(Pageable pageable);

    /**
     * 用户查询会议详情
     * @param meetId
     * @param userId
     * @return
     */
    MeetInfoDTO findFinalMeetInfo(String meetId, Integer userId);

    /**
     * 微信推荐会议
     * @param userId
     * @return
     */
    List<MeetFolderDTO> findRecommendMeetFolder(Integer userId);

    MeetFolderDTO getMeetFolder(String id);

    // 复制新的模块ID
    Integer addModuleReturnId(String meetId,MeetModule module) throws SystemException;

    // 查询模块课程ID
    Integer findModuleCourseId(MeetModule module);

    /**
     * 复制会议模块课程及明细
     * @param oldMeetId
     * @param newMeetId
     * @throws SystemException
     */
    void copyMeetModuleCourse(String oldMeetId, String newMeetId) throws SystemException;

    /**
     * 复制会议ppt语音课程及明细
     * @param oldCourseId
     * @param newModuleId
     * @param newMeetId
     */
    void copyAudioCourse(Integer oldCourseId, Integer newModuleId, String newMeetId) ;

    /**
     * 复制会议视频课程及明细
     * @param oldCourseId
     * @param newModuleId
     * @param newMeetId
     */
    void copyVideoCourse(Integer oldCourseId, Integer newModuleId, String newMeetId);

    /**
     * 复制会议考题数据
     * @param oldExam
     * @param newModuleId
     * @param newMeetId
     */
    void copyExam(MeetExam oldExam, Integer newModuleId, String newMeetId);

    /**
     * 复制会议问卷数据
     * @param oldPaperId
     * @param newModuleId
     * @param newMeetId
     */
    void copySurvey(Integer oldPaperId, Integer newModuleId, String newMeetId);

    /**
     * 复制会议签到数据
     * @param oldMeetId
     * @param newMeetId
     * @param oldModuleId
     * @param newModuleId
     */
    void copyMeetSign(String oldMeetId, String newMeetId, Integer oldModuleId, Integer newModuleId);


}



