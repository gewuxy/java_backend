package cn.medcn.meet.service;

import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface AudioService extends BaseService<AudioCourse>  {

    //国内版用户引导课件ID
    Integer GUIDE_SOURCE_ID = 1;
    //国外版用户引导课件ID
    Integer ABROAD_GUIDE_SOURCE_ID = 2;


    /**
     * 批量生成ppt+语音信息
     * @param list
     */
    void insertBatch(List<AudioCourseDetail> list);

    AudioCourse findAudioCourse(Integer courseId);

    /**
     * 查询会议音频模块信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetAudio findMeetAudio(String meetId, Integer moduleId);

    /**
     * 记录ppt语音学习记录
     * @param history
     */
    void insertHistory(AudioHistory history);

    /**
     * 查询资源列表
     * @param pageable
     * @return
     */
    MyPage<CourseReprintDTO> findResource(Pageable pageable);

    /**
     * 查询所有的资源分类
     * @return
     */
    List<ResourceCategoryDTO> findResourceCategorys(Integer userId);

    /**
     * 查询我的转载记录
     * @param pageable
     * @return
     */
    MyPage<CourseReprintDTO> findMyReprints(Pageable pageable);

    /**
     * 查询我的分享记录
     * @param pageable
     * @return
     */
    MyPage<CourseSharedDTO> findMyShared(Pageable pageable);

    /**
     * 查询我的被转载记录
     * @param pageable
     * @return
     */
    MyPage<CourseReprintDTO> findMyReprinted(Pageable pageable);

    /**
     * 转载微课
     * @param id
     * @param userId
     */
    void doReprint(Integer id, Integer userId)  throws NotEnoughCreditsException, SystemException;

    /**
     * 修改会议ppt模块属性
     * @param meetAudio
     */
    void updateMeetAudio(MeetAudio meetAudio);

    /**
     * 获取meetAudio简单信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetAudio findMeetAudioSimple(String meetId, Integer moduleId);

    /**
     * 根据id获取ppt明细记录
     * @param detailId
     * @return
     */
    AudioCourseDetail findDetail(Integer detailId);

    /**
     * 删除ppt明细
     * 同时维护本course的所有其他明细的下标
     * @param detailId
     */
    AudioCourseDetail deleteDetail(Integer courseId, Integer detailId);

    /**
     * 修改ppt明细
     *
     * @param detail
     */
    void updateDetail(AudioCourseDetail detail);

    /**
     * 增加ppt明细
     * 同时维护本course下的所有明细的下标
     * @param detail
     */
    void addDetail(AudioCourseDetail detail);

    /**
     * 查询明细列表
     * @param courseId
     * @return
     */
    List<AudioCourseDetail> findDetails(Integer courseId);

    /**
     * 查询ppt完整观看人数（全部、本月、本周）
     * @param params
     * @return
     */
    List<AudioHistoryDTO> findViewPptCount(Map<String,Object> params);

    /**
     * 查询某个用户观看ppt时长明细
     * @param pageable
     * @return
     */
    MyPage<AudioRecordDTO> findAudioRecord(Pageable pageable);

    /**
     * 查询所有用户观看ppt时长明细
     * @param params
     * @return
     */
    List<AudioRecordDTO> findAudioRecordtoExcel(Map<String,Object> params);



    /**
     * 查询观看PPT总人数
     * @param meetId
     * @return
     */
    Integer findViewCount(String meetId);

    /**
     * 检测用户是否已经转载资源
     * @param courseId
     * @param userId
     * @return
     */
    boolean checkReprinted(Integer courseId, Integer userId);

    /**
     * 根据会议ID获取会议ppt模块信息
     * @param meetId
     * @return
     */
    MeetAudio findMeetAudioByMeetId(String meetId);

    List<AudioCourseDetail> findPPtTotalCount(String meetId);

    List<AudioRecordDTO> findFinishedPPtCount(Map<String,Object> params);

    /**
     * 删除所有明细
     * @param courseId
     */
    void deleteAllDetails(Integer courseId);

    /**
     * 根据图片列表生成全新的微课明细
     * @param courseId
     * @param pptImageList
     */
    void updateAllDetails(Integer courseId, List<String> pptImageList);

    /**
     * 查询用户观看某个会议的ppt页数
     * @param meetId
     * @param userId
     * @return
     */
    Integer findUserViewPPTCount(String meetId,Integer userId);


    void addMeetAudio(MeetAudio audio);


    /**
     * 查询csp会议列表
     * @return
     */
    MyPage<CourseDeliveryDTO> findCspMeetingList(Pageable pageable) ;

    /**
     * 查询csp会议列表
     * @return
     */
    MyPage<CourseDeliveryDTO> findCspMeetingListForApp(Pageable pageable) ;

    AudioCourse findLastDraft(String cspUserId);

    AudioCoursePlay findPlayState(Integer courseId);

    /**
     * 修改录播记录
     * @param play
     */
    void updateAudioCoursePlay(AudioCoursePlay play);

    /**
     * 删除course以及明细
     * @param courseId
     */
    void deleteAudioCourse(Integer courseId);

    /**
     * 复制课件 并复制直播和录播信息
     * @param courseId
     * @param newTitle
     */
    int addCourseCopy(Integer courseId, String newTitle);

    /**
     * 投稿给指定用户的会议列表
     * @param pageable
     * @return
     */
    MyPage<CourseDeliveryDTO> findHistoryDeliveryByAcceptId(Pageable pageable);

    /**
     * 修改课件基本信息以及修改直播信息
     * @param audioCourse
     */
    void updateAudioCourseInfo(AudioCourse audioCourse, Live live);

    /**
     * 修改课件基本信息以及修改录播信息
     * @param audioCourse
     */
    void updateAudioCourseInfo(AudioCourse audioCourse, AudioCoursePlay play);

    void insertAudioCoursePlay(AudioCoursePlay play);

    void addLiveDetail(LiveDetail liveDetail);

    Integer findMaxLiveDetailSort(Integer courseId);

    List<AudioCourseDetail> findLiveDetails(Integer courseId);

    MyPage<AudioCourse> findAllMeetForManage(Pageable pageable);

    MyPage<AudioCourse> findCourseByPage(Pageable pageable);

    CourseDeliveryDTO findMeetDetail(Integer id);

    /**
     * 判断csp课件是否可编辑
     * @param courseId
     * @return
     */
    void editAble(Integer courseId) throws SystemException;

    void deleteAble(Integer courseId) throws SystemException;

    Integer countLiveDetails(Integer courseId);

    /**
     * 获取没有缓存的直播明细
     * @param courseId
     * @return
     */
    List<AudioCourseDetail> findNoCacheLiveDetails(Integer courseId);

    // 检查课程是否是当前用户的会议课程
    boolean checkCourseIsMine(String userId, Integer courseId);

    /**
     * 逻辑删除csp课件
     * @param courseId
     */
    void deleteCspCourse(Integer courseId);

    Integer doCopyCourse(AudioCourse course, Integer userId, String newTitle);

    void handleHttpUrl(String fileBase, AudioCourse course);

    /**
     * 将直播课件copy成录播课件
     * @param courseId
     * @return
     */
    Integer doCopyLiveToRecord(Integer courseId);

    /**
     * 复制水印
     * @param oldCourseId
     * @param newCourseId
     */
    void doCopyWatermark(Integer oldCourseId,Integer newCourseId);


    /**
     * 更新水印信息和course信息
     * @param audioCourse
     * @param live
     * @param newWatermark
     * @param packageId
     */
    void updateInfo(AudioCourse audioCourse,Live live ,MeetWatermark newWatermark,Integer packageId);

    void doModifyAudioCourse(String cspUserId);

    void doModifyAudioCourseByPackageId(String cspUserId,int packageId);

    AudioCourse createNewCspCourse(String userId);

    /**
     * 查找用户最早未删除的且锁定的课件
     * @param cspUserId
     * @return
     */
    AudioCourse findEarliestCourse(String cspUserId);

    /**
     * 复制新手引导课件
     * 返回新的课件ID
     * @param cspUserId
     * @return
     */
    Integer doCopyGuideCourse(String cspUserId);

    /**
     * 检测用户是否已经存在新手引导课件
     * @param cspUserId
     * @return
     */
    boolean checkGuideExists(String cspUserId);

    /**
     * 解锁用户最早的会议
     * @param cspUserId
     */
    void doUnlockEarliestCourse(String cspUserId);

    /**
     * 檢測用戶是否有已經上傳了ppt但是沒有完成發佈的課件
     * @param cspUserId
     * @return
     */
    boolean hasUndoneCourse(String cspUserId);

    List<AudioCourseDetail> findDetailsByCourseId(Integer id);

    String getMeetShareUrl(String appCspBase ,String local,Integer courseId,boolean abroad);

    /**
     * 获取小程序二维码
     * @param id
     * @param page
     * @return
     */
    String getMiniQRCode(Integer id, String page, String accessToken) throws IOException;

    /**
     * 根据图片和标题创建课件和明细
     * @param files
     * @param course
     * @param theme
     * @return
     * @throws SystemException
     */
    Integer createAudioAndDetail(MultipartFile[] files, AudioCourse course, AudioCourseTheme theme) throws SystemException;

    /**
     * 小程序活动贺卡模板列表
     * @return
     */
    List<AudioCourseDTO> findMiniTemplate();

    /**
     * 通过小程序二维码（固定）或者搜索小程序（随机） 返回贺卡模板
     * @return
     */
    AudioCourseDTO findMiniTemplateByIdOrRand(Integer id);

    /**
     * 小程序 选择贺卡模板 制作有声贺卡
     * @param id 模板id
     * @param cspUserId
     * @return
     */
    Integer doCopyCourseTemplate(Integer id, String cspUserId);


    /**
     * 复制讲本主题 （背景图片、背景音乐）
     * @param courseTheme
     */
    void doCopyCourseTheme(AudioCourseTheme courseTheme, Integer courseId);


    /**
     * 修改课件密码
     * @param course
     * @param password
     */
    void doModifyPassword(AudioCourse course, String password);

    /**
     * 根据会议来源和会议类型筛选出会议列表
     * @param pageable
     * @return
     */
    MyPage<CourseDeliveryDTO> findMiniMeetingListByType(Pageable pageable);

    /**
     * 创建课件或者添加课件图片
     * @param file
     * @param course
     * @param sort
     * @return
     */
    Integer createAudioOrAddDetail(MultipartFile file, AudioCourse course, Integer sort,Integer type) throws SystemException;

    ActivityGuideDTO findActivityCourse(Integer courseId);

    /**
     * 生成或更新课件标题, 课件主题，背景音乐
     * @param course
     * @param imgId
     * @param musicId
     */
    void createOrUpdateCourseAndTheme(AudioCourse course, Integer imgId, Integer musicId);

    String getCoverUrl(Integer courseId);
}
