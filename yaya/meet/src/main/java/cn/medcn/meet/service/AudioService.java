package cn.medcn.meet.service;

import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/4/25.
 */
public interface AudioService extends BaseService<AudioCourse>  {
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

    CourseDeliveryDTO findMeetDetail(Integer id);

    /**
     * 判断csp课件是否可编辑
     * @param courseId
     * @return
     */
    boolean editAble(Integer courseId);

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


    void updateInfo(AudioCourse audioCourse,Live live ,MeetWatermark newWatermark,Integer packageId);
}
