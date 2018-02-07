package cn.medcn.meet.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.goods.dto.CreditPayDTO;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.meet.dao.*;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CspStarRateService;
import cn.medcn.meet.service.LiveService;
import cn.medcn.user.dao.CspPackageDAO;
import cn.medcn.user.dao.CspUserInfoDAO;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspUserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.medcn.weixin.config.MiniProgramConfig.*;

/**
 * Created by lixuan on 2017/4/25.
 */
@Service
public class AudioServiceImpl extends BaseServiceImpl<AudioCourse> implements AudioService {

    @Autowired
    private AudioCourseDetailDAO audioCourseDetailDAO;

    @Autowired
    private AudioCourseDAO audioCourseDAO;

    @Autowired
    private MeetAudioDAO meetAudioDAO;

    @Autowired
    private AudioHistoryDAO audioHistoryDAO;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    protected LiveDetailDAO liveDetailDAO;

    @Autowired
    protected CourseDeliveryDAO courseDeliveryDAO;

    @Autowired
    protected CspUserInfoDAO cspUserInfoDAO;

    @Autowired
    protected AudioCourseThemeDAO audioCourseThemeDAO;

    @Autowired
    protected CspStarRateOptionDAO cspStarRateOptionDAO;

    @Autowired
    protected CspStarRateHistoryDAO cspStarRateHistoryDAO;

    @Autowired
    protected CspStarRateHistoryDetailDAO cspStarRateHistoryDetailDAO;

    @Autowired
    protected CspStarRateService cspStarRateService;


    @Value("${app.file.upload.base}")
    private String appFileUploadBase;

    @Value("${app.file.base}")
    private String appFileBase;

    //是否是正式线包
    @Value("${app.is.pro}")
    private Integer appPro;


    @Autowired
    protected AudioCoursePlayDAO audioCoursePlayDAO;

    @Autowired
    protected LiveService liveService;

    @Autowired
    protected MeetWatermarkDAO watermarkDAO;

    @Autowired
    protected CspPackageDAO cspPackageDAO;

    @Autowired
    protected AudioCourseThemeDAO courseThemeDAO;



    @Override
    public Mapper<AudioCourse> getBaseMapper() {
        return audioCourseDAO;
    }


    /**
     * 批量生成ppt+语音信息
     *
     * @param list
     */
    @Override
    public void insertBatch(List<AudioCourseDetail> list) {
        for (AudioCourseDetail ppt:list){
            audioCourseDetailDAO.insert(ppt);
        }
    }

    @Override
    @Cacheable(value = DEFAULT_CACHE, key = "'audio_course_'+#courseId")
    public AudioCourse findAudioCourse(Integer courseId) {
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);

        if (course != null) {
            List<AudioCourseDetail> details = audioCourseDetailDAO.findDetailsByCourseId(courseId);
            course.setDetails(details);
        }
        return course;
    }

    /**
     * 查询会议音频模块信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public MeetAudio findMeetAudio(String meetId, Integer moduleId) {
        MeetAudio condition = new MeetAudio();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        MeetAudio meetAudio = meetAudioDAO.selectOne(condition);
        return meetAudio;
    }

    /**
     * 记录ppt语音学习记录
     *
     * @param history
     */
    @Override
    public void insertHistory(AudioHistory history) {
        AudioHistory condition = new AudioHistory();
        condition.setUserId(history.getUserId());
        condition.setDetailId(history.getDetailId());
        condition.setMeetId(history.getMeetId());
        AudioHistory existedHistory = audioHistoryDAO.selectOne(condition);
        if(existedHistory == null){
            history.setEndTime(new Date());
            history.setStartTime(new Date(history.getEndTime().getTime()- history.getUsedtime()*1000));
            audioHistoryDAO.insert(history);
        }else{
            existedHistory.setUsedtime(existedHistory.getUsedtime()+history.getUsedtime());
            existedHistory.setEndTime(new Date());
            existedHistory.setFinished(existedHistory.getFinished() || (history.getFinished()));
            audioHistoryDAO.updateByPrimaryKeySelective(existedHistory);
        }

    }

    /**
     * 查询资源列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseReprintDTO> findResource(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        MyPage<CourseReprintDTO> page = MyPage.page2Mypage((Page) audioCourseDAO.findResource(pageable.getParams()));
        return page;
    }

    /**
     * 查询所有的资源分类
     *
     * @return
     */
    @Override
    public List<ResourceCategoryDTO> findResourceCategorys(Integer userId) {
        return audioCourseDAO.findResourceCategorys(userId);
    }

    /**
     * 查询我的转载记录
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseReprintDTO> findMyReprints(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CourseReprintDTO> page = MyPage.page2Mypage((Page) audioCourseDAO.findMyReprints(pageable.getParams()));
        return page;
    }

    /**
     * 查询我的分享记录
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseSharedDTO> findMyShared(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CourseSharedDTO> page = MyPage.page2Mypage((Page) audioCourseDAO.findMyShared(pageable.getParams()));
        return page;
    }

    /**
     * 转载微课
     *
     * @param id
     * @param userId
     */
    @Override
    public void doReprint(Integer id, Integer userId) throws NotEnoughCreditsException,SystemException {
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(id);
        if(course == null){
            throw new SystemException("您转载的会议[id="+id+"]不存在");
        }
        if(course.getShareType()!= null && course.getCredits() != null && course.getCredits() > 0){
            if(course.getShareType() == 1){
                //支付象数
                CreditPayDTO payDTO = new CreditPayDTO();
                payDTO.setAccepterDescrib("您的资源["+course.getTitle()+"]被转载,获得"+course.getCredits()+"个象数");
                payDTO.setPayerDescrib("您转载了资源["+course.getTitle()+"],消耗"+course.getCredits()+"个象数");
                payDTO.setPayer(userId);
                payDTO.setAccepter(course.getOwner());
                payDTO.setCredits(course.getCredits());
                creditsService.executePlayCredits(payDTO);
            }else if(course.getShareType() == 2){//奖励象数
                CreditPayDTO payDTO = new CreditPayDTO();
                payDTO.setCredits(course.getCredits());
                payDTO.setAccepter(userId);
                payDTO.setPayer(course.getOwner());
                payDTO.setPayerDescrib("您的资源["+course.getTitle()+"]被转载,支付"+course.getCredits()+"个象数");
                payDTO.setAccepterDescrib("您转载了资源["+course.getTitle()+"],获取"+course.getCredits()+"个象数");
                creditsService.executeAwardCredits(payDTO);
            }
        }
        doCopyCourse(course, userId, null);
    }

    /**
     * 复制微课信息
     * @param course
     * @param userId
     * @param newTitle 标题为空的时候 默认设置为源课件的标题
     */
    @Override
    public Integer doCopyCourse(AudioCourse course, Integer userId, String newTitle){
        List<AudioCourseDetail> details = audioCourseDetailDAO.findDetailsByCourseId(course.getId());
        //复制微课信息
        AudioCourse reprintCourse = new AudioCourse();
        reprintCourse.setCredits(0);
        reprintCourse.setTitle(CheckUtils.isEmpty(newTitle) ? course.getTitle() : newTitle );
        reprintCourse.setPrimitiveId(course.getId());
        reprintCourse.setOwner(userId == null ? course.getOwner() : userId);
        reprintCourse.setCategory(course.getCategory());
        reprintCourse.setCategoryId(course.getCategoryId());
        reprintCourse.setPublished(course.getPublished());
        reprintCourse.setShared(false);
        reprintCourse.setDeleted(false);
        reprintCourse.setCreateTime(new Date());
        reprintCourse.setSourceType(course.getSourceType());
        reprintCourse.setPlayType(course.getPlayType());
        reprintCourse.setCspUserId(course.getCspUserId());
        reprintCourse.setInfo(course.getInfo());
        reprintCourse.setLocked(false);
        reprintCourse.setGuide(course.getGuide());
        reprintCourse.setStarRateFlag(course.getStarRateFlag());
        audioCourseDAO.insert(reprintCourse);
        //复制微课明细
        doCopyDetails(details, reprintCourse.getId());
        //复制水印
        doCopyWatermark(course.getId(),reprintCourse.getId());
        return reprintCourse.getId();
    }


    /**
     * 查询我的被转载记录
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseReprintDTO> findMyReprinted(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<CourseReprintDTO> page = MyPage.page2Mypage((Page) audioCourseDAO.findMyReprinted(pageable.getParams()));
        return page;
    }

    /**
     * 修改会议ppt属性
     *
     * @param meetAudio
     */
    @Override
    public void updateMeetAudio(MeetAudio meetAudio) {
        meetAudioDAO.updateByPrimaryKeySelective(meetAudio);
    }

    /**
     * 获取meetAudio简单信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public MeetAudio findMeetAudioSimple(String meetId, Integer moduleId) {
        MeetAudio condition = new MeetAudio();
        condition.setMeetId(meetId);
        //condition.setModuleId(moduleId);
        MeetAudio meetAudio = meetAudioDAO.selectOne(condition);
        return meetAudio;
    }

    /**
     * 根据id获取ppt明细记录
     *
     * @param detailId
     * @return
     */
    @Override
    public AudioCourseDetail findDetail(Integer detailId) {
        AudioCourseDetail detail = audioCourseDetailDAO.selectByPrimaryKey(detailId);
        return detail;
    }

    /**
     * 删除ppt明细
     * 同时维护本course的所有其他明细的下标
     *
     * @param detailId
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#courseId")
    public AudioCourseDetail deleteDetail(Integer courseId, Integer detailId) {
        AudioCourseDetail detail = audioCourseDetailDAO.selectByPrimaryKey(detailId);
        Integer startSort = detail.getSort();
        audioCourseDetailDAO.deleteByPrimaryKey(detailId);
        audioCourseDetailDAO.updateBatchDecreaseSort(detail.getCourseId(), startSort);
//        //同时删除文件
//        if(!StringUtils.isEmpty(detail.getAudioUrl())){
//            FileUtils.deleteTargetFile(appFileUploadBase+detail.getAudioUrl());
//        }
//        if(!StringUtils.isEmpty(detail.getImgUrl())){
//            FileUtils.deleteTargetFile(appFileUploadBase+detail.getImgUrl());
//        }
        return detail;
    }

    /**
     * 修改ppt明细
     *
     * @param detail
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#detail.courseId")
    public void updateDetail(AudioCourseDetail detail) {
        audioCourseDetailDAO.updateByPrimaryKeySelective(detail);
    }

    /**
     * 增加ppt明细
     * 同时维护本course下的所有明细的下标
     *
     * @param detail
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#detail.courseId")
    public void addDetail(AudioCourseDetail detail) {
        Integer startSort = detail.getSort();
        audioCourseDetailDAO.updateBatchAddSort(detail.getCourseId(), startSort);
        audioCourseDetailDAO.insert(detail);
    }

    /**
     * 查询明细列表
     *
     * @param courseId
     * @return
     */
    @Override
    public List<AudioCourseDetail> findDetails(Integer courseId) {
        return audioCourseDetailDAO.findDetailsByCourseId(courseId);
    }

    /**
     * 查询ppt完整观看人数（全部、本月、本周）
     * @param params
     * @return
     */
    @Override
    public List<AudioHistoryDTO> findViewPptCount(Map<String,Object> params) {
        List<AudioHistoryDTO> list = audioCourseDetailDAO.findViewPptCount(params);
        for (AudioHistoryDTO audioHistoryDTO :list){
            audioHistoryDTO.setTagNo((Integer) params.get("tagNo"));
            audioHistoryDTO.setStartTime(params.get("startTime")==null?"":params.get("startTime").toString());
            audioHistoryDTO.setEndTime(params.get("endTime")==null?"":params.get("endTime").toString());
        }
        return list;
    }

    /**
     * 查询某个用户观看ppt时长明细
     * @param pageable
     * @return
     */
    @Override
    public MyPage<AudioRecordDTO> findAudioRecord(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(), Pageable.countPage);
        MyPage<AudioRecordDTO> page = MyPage.page2Mypage((Page)audioCourseDetailDAO.findViewAudioList(pageable.getParams()));
        return page;
    }

    /**
     * 查询所有用户观看ppt时长明细
     * @param params
     * @return
     */
    @Override
    public List<AudioRecordDTO> findAudioRecordtoExcel(Map<String, Object> params) {
        List<AudioRecordDTO> list = audioCourseDetailDAO.findAudioRecordList(params);
        Map<Integer, Integer> map = new HashMap();
        Map<Integer,Integer> finishMap = new HashMap();
        for (AudioRecordDTO recordDTO : list){
            if(map.containsKey(recordDTO.getId())){//判断是否已经有该数值，如有，则累加观看时长
                map.put(recordDTO.getId(), map.get(recordDTO.getId()) + recordDTO.getUsedtime());
            }else{
                map.put(recordDTO.getId(), recordDTO.getUsedtime());
            }
            recordDTO.setTime(map.get(recordDTO.getId()));

            // 查询用户观看完的ppt记录
            params.put("id", recordDTO.getId());
            List<AudioRecordDTO> finishedPPTList = audioCourseDetailDAO.findFinishedPPtCount(params);
            if (finishedPPTList != null && finishedPPTList.size() != 0) {
                recordDTO.setPptCount(finishedPPTList.get(0).getPptCount());
            } else {
                recordDTO.setPptCount(0);
            }
        }

        return list;
    }



    /**
     * 查询ppt总页数
     * @param meetId
     * @return
     */
    public List<AudioCourseDetail> findPPtTotalCount(String meetId){
        List<AudioCourseDetail> list = audioCourseDetailDAO.findPPtTotalCount(meetId);
        return list;
    }

    /**
     * 查询观看ppt总人数
     * @param meetId
     * @return
     */
    @Override
    public Integer findViewCount(String meetId) {
        List list = audioCourseDetailDAO.findViewCount(meetId);
        Integer viewCount = 0;
        if(!CheckUtils.isEmpty(list)){
            viewCount = list.size();
        }
        return viewCount;
    }


    /**
     * 检测用户是否已经转载资源
     *
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public boolean checkReprinted(Integer courseId, Integer userId) {
        AudioCourse condition = new AudioCourse();
        condition.setOwner(userId);
        condition.setPrimitiveId(courseId);
        int count = audioCourseDAO.selectCount(condition);
        return count>0;
    }

    /**
     * 根据会议ID获取会议ppt模块信息
     *
     * @param meetId
     * @return
     */
    @Override
    public MeetAudio findMeetAudioByMeetId(String meetId) {
        MeetAudio condition = new MeetAudio();
        condition.setMeetId(meetId);
        return meetAudioDAO.selectOne(condition);
    }

    @Override
    public List<AudioRecordDTO> findFinishedPPtCount(Map<String,Object> params){
        List<AudioRecordDTO> recordList = audioCourseDetailDAO.findFinishedPPtCount(params);
        return recordList;
    }


    /**
     * 删除所有明细
     *
     * @param courseId
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#courseId")
    public void deleteAllDetails(Integer courseId) {
        AudioCourseDetail condition = new AudioCourseDetail();
        condition.setCourseId(courseId);
        audioCourseDetailDAO.delete(condition);
    }


    /**
     * 根据图片列表生成全新的微课明细
     *
     * @param courseId
     * @param pptImageList
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#courseId")
    public void updateAllDetails(Integer courseId, List<String> pptImageList) {
        if(pptImageList != null && pptImageList.size() != 0){
            deleteAllDetails(courseId);
            int sort = 1;
            for(String pptImage : pptImageList){
                AudioCourseDetail courseDetail = new AudioCourseDetail();
                courseDetail.setCourseId(courseId);
                courseDetail.setSort(sort);
                courseDetail.setImgUrl(pptImage);
                audioCourseDetailDAO.insert(courseDetail);
                sort++;
            }
        }
    }

    /**
     * 查询用户观看某个会议的ppt页数
     * @param meetId
     * @param userId
     * @return
     */
    public Integer findUserViewPPTCount(String meetId,Integer userId){
        return audioHistoryDAO.findUserViewPPTCount(meetId,userId);
    }

    public void addMeetAudio(MeetAudio audio){
        meetAudioDAO.insert(audio);
    }

    /**
     * 查询csp会议列表
     * @return
     */
    public MyPage<CourseDeliveryDTO> findCspMeetingList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        PageHelper.orderBy("c.create_time " + (pageable.get("sortType") == null ? "desc" : pageable.get("sortType")));
        return MyPage.page2Mypage((Page) audioCourseDAO.findCspMeetingList(pageable.getParams()));
    }

    /**
     * 查询csp会议列表
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseDeliveryDTO> findCspMeetingListForApp(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        PageHelper.orderBy("c.create_time " + (pageable.get("sortType") == null ? "desc" : pageable.get("sortType")));
        return MyPage.page2Mypage((Page) audioCourseDAO.findCspMeetingListForApp(pageable.getParams()));
    }

    @Override
    public AudioCourse findLastDraft(String cspUserId) {
        AudioCourse course = audioCourseDAO.findLastDraft(cspUserId);
        if (course != null) {
            List<AudioCourseDetail> details = audioCourseDetailDAO.findDetailsByCourseId(course.getId());
            course.setDetails(details);
        }
        return course;
    }

    @Override
    public AudioCoursePlay findPlayState(Integer courseId) {
        AudioCoursePlay cond = new AudioCoursePlay();
        cond.setCourseId(courseId);
        return audioCoursePlayDAO.selectOne(cond);
    }

    /**
     * 修改录播记录
     *
     * @param play
     */
    @Override
    public void updateAudioCoursePlay(AudioCoursePlay play) {
        audioCoursePlayDAO.updateByPrimaryKey(play);
    }

    /**
     * 投稿给指定用户的会议列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseDeliveryDTO> findHistoryDeliveryByAcceptId(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        return MyPage.page2Mypage((Page)audioCourseDAO.findHistoryDeliveryByAcceptId(pageable.getParams()));
    }

    /**
     * 删除course以及明细
     *
     * @param courseId
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#courseId")
    public void deleteAudioCourse(Integer courseId) {
        deleteAllDetails(courseId);
        audioCourseDAO.deleteByPrimaryKey(courseId);
    }

    /**
     * 复制课件 并复制直播和录播信息
     *
     * @param courseId
     */
    @Override
    public int addCourseCopy(Integer courseId, String newTitle) {
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        course.setGuide(false);
        Integer newCourseId = doCopyCourse(course, null, newTitle);

        course.setPlayType(course.getPlayType() == null ? AudioCourse.PlayType.normal.getType() : course.getPlayType());

        if (course.getPlayType().intValue() > AudioCourse.PlayType.normal.getType()) {
            Live live = liveService.findByCourseId(courseId);
            Live copy = new Live();
            copy.setId(cn.medcn.common.utils.StringUtils.nowStr());
            copy.setLiveState(AudioCoursePlay.PlayState.init.ordinal());
            if (live != null && live.getStartTime() != null) {
                copy.setStartTime(live == null ? new Date() : live.getStartTime());
                copy.setEndTime(live == null ? new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)) : live.getEndTime());
            } else {
                copy.setStartTime(new Date());
                copy.setEndTime(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)));
            }
            copy.setVideoLive(live.getVideoLive());
            copy.setLivePage(0);
            copy.setPlayCount(0);
            copy.setCourseId(newCourseId);
            liveService.insert(copy);
        } else {
            AudioCoursePlay copy = new AudioCoursePlay();
            copy.setId(cn.medcn.common.utils.StringUtils.nowStr());
            copy.setPlayState(AudioCoursePlay.PlayState.init.ordinal());
            copy.setPlayPage(0);
            copy.setCourseId(newCourseId);
            audioCoursePlayDAO.insert(copy);
        }

        return newCourseId;
    }

    /**
     * 修改课件基本信息以及直播信息
     *
     * @param audioCourse
     * @param live
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#audioCourse.id")
    public void updateAudioCourseInfo(AudioCourse audioCourse, Live live) {
            Live oldLive = liveService.findByCourseId(audioCourse.getId());
            if (oldLive == null) {
                oldLive = new Live();
                oldLive.setId(cn.medcn.common.utils.StringUtils.nowStr());
                oldLive.setCourseId(audioCourse.getId());
                oldLive.setLiveState(Live.LiveState.init.getType());
                oldLive.setLivePage(0);
                oldLive.setVideoLive(audioCourse.getPlayType().intValue() == AudioCourse.PlayType.live_video.getType());
                oldLive.setStartTime(live.getStartTime());
                oldLive.setEndTime(live.getEndTime());

                liveService.insert(oldLive);
            } else {
                oldLive.setVideoLive(audioCourse.getPlayType() != null && audioCourse.getPlayType().intValue() == AudioCourse.PlayType.live_video.getType());
                oldLive.setStartTime(live.getStartTime());
                oldLive.setEndTime(live.getEndTime());

                liveService.updateByPrimaryKeySelective(oldLive);
            }

            audioCourse.setCreateTime(new Date());
            audioCourse.setPublished(true);
            updateByPrimaryKeySelective(audioCourse);
    }


    /**
     * 修改课件基本信息以及修改录播信息
     *
     * @param audioCourse
     * @param play
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#audioCourse.id")
    public void updateAudioCourseInfo(AudioCourse audioCourse, AudioCoursePlay play) {
        AudioCoursePlay oldPlay = findPlayState(audioCourse.getId());

        if (oldPlay == null) {
            oldPlay = new AudioCoursePlay();
            oldPlay.setId(cn.medcn.common.utils.StringUtils.nowStr());
            oldPlay.setCourseId(audioCourse.getId());
            oldPlay.setPlayPage(0);
            oldPlay.setPlayState(AudioCoursePlay.PlayState.init.ordinal());

            audioCoursePlayDAO.insert(oldPlay);
        }
        //修改课件修改时间
        audioCourse.setCreateTime(new Date());
        audioCourse.setPublished(true);
        updateByPrimaryKeySelective(audioCourse);
    }

    @Override
    public void insertAudioCoursePlay(AudioCoursePlay play) {
        audioCoursePlayDAO.insert(play);
    }

    @Override
    @CacheEvict(value = DEFAULT_CACHE,  key = "'audio_course_'+#liveDetail.courseId")
    public void addLiveDetail(LiveDetail liveDetail) {
        liveDetailDAO.insert(liveDetail);
    }

    @Override
    public Integer findMaxLiveDetailSort(Integer courseId) {
        return liveDetailDAO.findMaxLiveDetailSort(courseId);
    }

    @Override
    public List<AudioCourseDetail> findLiveDetails(Integer courseId) {
        List<AudioCourseDetail> details = liveDetailDAO.findByCourseId(courseId);
        LiveOrderDTO orderDTO = liveService.findCachedOrder(courseId);
        if (details.size() == 0) {//直播明细为空时
            //如果缓存中也没有则加入第一张
            AudioCourseDetail detail = findFirstDetail(courseId);
            if (detail != null) {
                details.add(findFirstDetail(courseId));
            }
        } else {
            if (orderDTO != null) {
                AudioCourseDetail detail = new AudioCourseDetail();
                detail.setId(orderDTO.getDetailId());
                detail.setCourseId(Integer.valueOf(orderDTO.getCourseId()));
                detail.setImgUrl(orderDTO.getImgUrl());
                detail.setAudioUrl(orderDTO.getAudioUrl());
                detail.setVideoUrl(orderDTO.getVideoUrl());
                detail.setTemp(true);
                details.add(detail);
            }
        }

        return details;
    }

    @Override
    public MyPage<AudioCourse> findAllMeetForManage(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        return MyPage.page2Mypage((Page)audioCourseDAO.findAllMeetForManage(pageable.getParams()));
    }

    @Override
    public CourseDeliveryDTO findMeetDetail(Integer id) {
        return audioCourseDAO.findMeetDetail(id);
    }

    /**
     * 判断csp课件是否可编辑
     *
     * @param courseId
     * @return
     */
    @Override
    public void editAble(Integer courseId) throws SystemException{

        // 判断是否有录播或者直播记录
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        if (course == null) {
            throw new SystemException(local("source.not.exists"));
        }
        if (course.getDeleted() != null && course.getDeleted()) {
            throw new SystemException(local("source.has.deleted"));
        }
        if (course.getPlayType() == null) {
            course.setPlayType(AudioCourse.PlayType.normal.getType());
        }
        if (course.getPlayType().intValue() > AudioCourse.PlayType.normal.getType()) {
            Live live = liveService.findByCourseId(courseId);
            if (live != null && live.getLiveState().intValue() > AudioCoursePlay.PlayState.init.ordinal()) {
                throw new SystemException(local("course.error.editable"));
            }

            //判断是否有投稿历史
            CourseDelivery cond = new CourseDelivery();
            cond.setSourceId(courseId);
            List<CourseDelivery> deliveries = courseDeliveryDAO.select(cond);
            if (!CheckUtils.isEmpty(deliveries)){
                throw new SystemException(local("course.error.delivery.editable"));
            }
        }
    }

    @Override
    public void deleteAble(Integer courseId) throws SystemException {
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        if (course == null) {
            throw new SystemException(local("source.not.exists"));
        }
        if (course.getPlayType() == null) {
            course.setPlayType(AudioCourse.PlayType.normal.getType());
        }
        if (course.getPlayType().intValue() > AudioCourse.PlayType.normal.getType()) {
            Live live = liveService.findByCourseId(courseId);
            if (live != null && live.getLiveState().intValue() > AudioCoursePlay.PlayState.init.ordinal()) {
                throw new SystemException(local("course.error.delete"));
            }

            //判断是否有投稿历史
            CourseDelivery cond = new CourseDelivery();
            cond.setSourceId(courseId);
            List<CourseDelivery> deliveries = courseDeliveryDAO.select(cond);
            if (!CheckUtils.isEmpty(deliveries)){
                throw new SystemException(local("page.meeting.delete.error.delivery"));
            }
        }
    }

    @Override
    public Integer countLiveDetails(Integer courseId) {
        List<AudioCourseDetail> details = liveDetailDAO.findByCourseId(courseId);
        return CheckUtils.isEmpty(details) ? 0 : details.size();
    }

    /**
     * 获取没有缓存的直播明细
     *
     * @param courseId
     * @return
     */
    @Override
    public List<AudioCourseDetail> findNoCacheLiveDetails(Integer courseId) {
        List<AudioCourseDetail> details = liveDetailDAO.findByCourseId(courseId);
        if (CheckUtils.isEmpty(details)) {
            AudioCourseDetail detail = findFirstDetail(courseId);
            if (detail != null) {
                details.add(detail);
            }
        }
        return details;
    }

    protected AudioCourseDetail findFirstDetail(Integer courseId){
        AudioCourseDetail cond = new AudioCourseDetail();
        cond.setCourseId(courseId);
        cond.setSort(1);
        AudioCourseDetail firstDetail = audioCourseDetailDAO.selectOne(cond);
        firstDetail.setVideoUrl(null);
        firstDetail.setTemp(true);
        return firstDetail;
    }

    /**
     * 检查是否是当前用户的课程
     * @param userId
     * @param courseId
     * @return
     */
    public boolean checkCourseIsMine(String userId, Integer courseId) {
        if (StringUtils.isEmpty(userId)
                || courseId == null || courseId == 0) {
            return false;
        }

        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        if (course == null) {
            return false;
        }
        boolean isMine = userId.equals(course.getCspUserId());
        return isMine;
    }

    /**
     * 逻辑删除csp课件
     *
     * @param courseId
     */
    @Override
    public void deleteCspCourse(Integer courseId) {
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        if (course != null) {
            course.setDeleted(true);
            audioCourseDAO.updateByPrimaryKey(course);
        }
    }

    @Override
    public void handleHttpUrl(String fileBase, AudioCourse course){
        if (course != null && !CheckUtils.isEmpty(course.getDetails())) {
            for (AudioCourseDetail detail : course.getDetails()) {
                if (CheckUtils.isNotEmpty(detail.getAudioUrl()) && !detail.getAudioUrl().startsWith("http")) {
                    detail.setAudioUrl(fileBase + detail.getAudioUrl());
                }
                if (CheckUtils.isNotEmpty(detail.getImgUrl()) && !detail.getImgUrl().startsWith("http")) {
                    detail.setImgUrl(fileBase + detail.getImgUrl());
                }
                if (CheckUtils.isNotEmpty(detail.getVideoUrl()) && !detail.getVideoUrl().startsWith("http")) {
                    detail.setVideoUrl(fileBase + detail.getVideoUrl());
                }
            }
        }
    }

    /**
     * 将直播课件copy成录播课件
     *
     * @param courseId
     * @return
     */
    @Override
    public Integer doCopyLiveToRecord(Integer courseId) {

        AudioCourse course = audioCourseDAO.selectByPrimaryKey(courseId);
        if (course != null) {
            List<AudioCourseDetail> details = liveDetailDAO.findByCourseId(courseId);
            if (CheckUtils.isEmpty(details)) {
                details = audioCourseDetailDAO.findDetailsByCourseId(courseId);
            } else {
                for (AudioCourseDetail detail : details) {
                    detail.setSort(detail.getSort() + 1);//直播记录sort是从0开始的 所以加1
                }
            }

            AudioCourse copyCourse = new AudioCourse();
            BeanUtils.copyProperties(course, copyCourse);
            copyCourse.setId(null);
            copyCourse.setCspUserId(course.getCspUserId());
            copyCourse.setPrimitiveId(courseId);
            copyCourse.setCreateTime(new Date());
            copyCourse.setPlayType(AudioCourse.PlayType.normal.getType());//设置成录播模式


            audioCourseDAO.insert(copyCourse);

            //生成明细
            Integer copyCourseId = copyCourse.getId();
            doCopyDetails(details, copyCourseId);

            //复制水印
            doCopyWatermark(courseId,copyCourseId);

            //生成录播信息
            AudioCoursePlay play = new AudioCoursePlay();
            play.setPlayState(AudioCoursePlay.PlayState.init.ordinal());
            play.setCourseId(copyCourseId);
            play.setId(StringUtils.nowStr());
            play.setPlayPage(0);
            audioCoursePlayDAO.insert(play);

            //复制星评信息
            doCopyStarRateHistory(courseId, copyCourseId);

            return copyCourseId;
        }

        return null;
    }

    protected void doCopyStarRateHistory(Integer srcCourseId, Integer copyCourseId){

        //处理综合评分
        List<CspStarRateOption> options = cspStarRateService.findOptionsByCourseId(srcCourseId);
        List<Integer> newOptionIds = new ArrayList<>();
        for (CspStarRateOption option : options) {
            CspStarRateOption o = new CspStarRateOption();
            o.setCourseId(copyCourseId);
            o.setTitle(option.getTitle());
            cspStarRateOptionDAO.insert(o);
            newOptionIds.add(o.getId());
        }

        List<CspStarRateHistory> historyList = cspStarRateService.findHistoriesByCourseId(srcCourseId);

        if (!CheckUtils.isEmpty(historyList)) {
            for (CspStarRateHistory history : historyList) {
                CspStarRateHistory h = new CspStarRateHistory();
                h.setCourseId(copyCourseId);
                h.setRateTime(history.getRateTime());
                h.setScore(history.getScore());
                h.setTicket(history.getTicket());
                cspStarRateHistoryDAO.insert(h);

                //查询出评分历史明细
                List<CspStarRateHistoryDetail> oldDetails = cspStarRateService.findDetailsByHistoryId(history.getId());
                if (!CheckUtils.isEmpty(oldDetails)) {
                    for (int i = 0; i < oldDetails.size(); i ++) {
                        CspStarRateHistoryDetail d = new CspStarRateHistoryDetail();
                        d.setOptionId(newOptionIds.get(i));
                        d.setCourseId(copyCourseId);
                        d.setScore(oldDetails.get(i).getScore());
                        d.setHistoryId(h.getId());
                        cspStarRateHistoryDetailDAO.insert(d);
                    }
                }
            }
        }
    }


    /**
     * 复制水印
     * @param oldCourseId
     * @param newCourseId
     */
    @Override
    public void doCopyWatermark(Integer oldCourseId,Integer newCourseId) {
        MeetWatermark watermark = new MeetWatermark();
        watermark.setCourseId(oldCourseId);
         watermark = watermarkDAO.selectOne(watermark);
         if(watermark != null){
             watermark.setCourseId(newCourseId);
             watermark.setId(null);
             watermarkDAO.insert(watermark);
         }
    }

    /**
     * 更新水印信息和course信息
     * @param ac
     * @param live
     * @param newWatermark
     * @param packageId
     */
    @Override
    @CacheEvict(value = DEFAULT_CACHE, key = "'audio_course_'+#ac.id")
    public void updateInfo(AudioCourse ac, Live live, MeetWatermark newWatermark,Integer packageId) {
        //查找是否有水印记录
        MeetWatermark watermark = new MeetWatermark();
        watermark.setCourseId(ac.getId());
        watermark = watermarkDAO.selectOne(watermark);
        if(watermark != null){ //更新水印
            if(packageId != CspPackage.TypeId.STANDARD.getId()){  //非标准版才能更新水印
                watermark.setDirection(newWatermark.getDirection());
                watermark.setState(newWatermark.getState());
                if(packageId > CspPackage.TypeId.PREMIUM.getId()){ //专业版才可以更新名称
                    watermark.setName(newWatermark.getName());
                }
            }
            watermarkDAO.updateByPrimaryKey(watermark);
        }else{ //生成水印
            newWatermark.setCourseId(ac.getId());
            if(packageId < CspPackage.TypeId.PROFESSIONAL.getId()){  //用默认的水印名称
                newWatermark.setName(local("meet.default.watermark"));
            }
            if(packageId == CspPackage.TypeId.STANDARD.getId()){  //防止前台传假数据，使用默认的数据
                newWatermark.setState(true);
                newWatermark.setDirection(MeetWatermark.Direction.RIGHT_TOP.ordinal());
            }
            watermarkDAO.insert(newWatermark);
        }

        if (ac.getPlayType() != null && ac.getPlayType().intValue() > 0) {// 直播的情况下
            updateAudioCourseInfo(ac, live);
        } else {
            updateAudioCourseInfo(ac, new AudioCoursePlay());
        }
    }


    protected void doCopyDetails(List<AudioCourseDetail> details, Integer courseId){
        for (AudioCourseDetail detail : details) {
            //复制微课明细
            AudioCourseDetail copyDetail = new AudioCourseDetail();
            BeanUtils.copyProperties(detail, copyDetail);
            copyDetail.setId(null);
            copyDetail.setCourseId(courseId);
            audioCourseDetailDAO.insert(copyDetail);
        }
    }


    @Override
    public void doModifyAudioCourse(String cspUserId) {
        // 查询用户发布的会议 并加锁（发布最早的3个会议不需要加锁）
        List<AudioCourse> audioCourseList = audioCourseDAO.findAudioCourseList(cspUserId);
        if (!CheckUtils.isEmpty(audioCourseList)) {
            for (int i = 3; i < audioCourseList.size() ; i++) {
                AudioCourse course = audioCourseList.get(i);
                course.setLocked(true);
                audioCourseDAO.updateByPrimaryKey(course);
            }
        }
    }

    @Override
    public void doModifyAudioCourseByPackageId(String cspUserId, int packageId) {
        // 查询用户发布的会议
        CspPackage cspPackage = cspPackageDAO.selectByPrimaryKey(packageId);
        Integer meets = cspPackage.getLimitMeets();
        if(meets == 0){
            //放开所有的会议
            audioCourseDAO.updateByUserIdOpenAll(cspUserId);
        }else{
            //把所有的会议加锁
            audioCourseDAO.updateByUserId(cspUserId);
            //放开固定的会议
            audioCourseDAO.updateByUserIdOpen(cspUserId,meets);
        }

    }

    @Override
    public AudioCourse createNewCspCourse(String userId) {
        AudioCourse course = new AudioCourse();
        course.setPlayType(AudioCourse.PlayType.normal.getType());
        course.setPublished(false);
        course.setDeleted(false);
        course.setShared(false);
        course.setCspUserId(userId);
        course.setTitle("");
        course.setCreateTime(new Date());
        course.setSourceType(AudioCourse.SourceType.csp.ordinal());
        course.setLocked(false);
        course.setGuide(false);
        course.setStarRateFlag(false);
        audioCourseDAO.insert(course);
        return course;
    }


    /**
     * 查找用户最早未删除的且锁定的课件
     *
     * @param cspUserId
     * @return
     */
    @Override
    public AudioCourse findEarliestCourse(String cspUserId) {
        return audioCourseDAO.findEarliestCourse(cspUserId);
    }


    @Override
    public Integer doCopyGuideCourse(String cspUserId) {
        if (!checkGuideExists(cspUserId)) {
            CspUserInfo userInfo = cspUserInfoDAO.selectByPrimaryKey(cspUserId);
            if (userInfo != null) {
                if (userInfo.getAbroad() == null) {
                    userInfo.setAbroad(false);
                }

                AudioCourse course = selectByPrimaryKey(!userInfo.getAbroad() ? GUIDE_SOURCE_ID : ABROAD_GUIDE_SOURCE_ID);
                Integer courseId = null;
                if (course != null) {
                    course.setCspUserId(cspUserId);
                    course.setGuide(true);
                    courseId = doCopyCourse(course, null, null);

                    AudioCoursePlay copy = findPlayState(course.getId());
                    if (copy != null) {
                        copy.setId(cn.medcn.common.utils.StringUtils.nowStr());
                        copy.setPlayState(copy.getPlayState());
                        copy.setPlayPage(copy.getPlayPage());
                        copy.setCourseId(courseId);
                        audioCoursePlayDAO.insert(copy);
                    }
                }
                return courseId;
            }
           return 0;
        } else {
            return 0;
        }
    }


    /**
     * 检测用户是否已经存在新手引导课件
     *
     * @param cspUserId
     * @return
     */
    @Override
    public boolean checkGuideExists(String cspUserId) {
        AudioCourse cond = new AudioCourse();
        cond.setCspUserId(cspUserId);
        cond.setGuide(true);
        return selectCount(cond) > 0;
    }

    /**
     * 解锁用户最好的会议
     *
     * @param cspUserId
     */
    @Override
    public void doUnlockEarliestCourse(String cspUserId) {
        AudioCourse earliestActiveCourse = findEarliestCourse(cspUserId);
        if (earliestActiveCourse != null) {
            earliestActiveCourse.setLocked(false);
        }
        updateByPrimaryKey(earliestActiveCourse);
    }

    /**
     * 檢測用戶是否有已經上傳了ppt但是沒有完成發佈的課件
     *
     * @param cspUserId
     * @return
     */
    @Override
    public boolean hasUndoneCourse(String cspUserId) {
        AudioCourse draft = findLastDraft(cspUserId);
        if (draft == null) {
            return false;
        }
        List<AudioCourseDetail> details = audioCourseDetailDAO.findDetailsByCourseId(draft.getId());
        return !CheckUtils.isEmpty(details);
    }

    /**
     * 获取会议详情
     *
     * @param id
     * @return
     */
    @Override
    public List<AudioCourseDetail> findDetailsByCourseId(Integer id) {
        return audioCourseDetailDAO.findDetailsByCourseId(id);
    }

    /**
     * 获取会议分享接口地址
     * @param local
     * @param courseId
     * @param abroad
     * @return
     */
    @Override
    public String getMeetShareUrl(String appCspBase,String local, Integer courseId, boolean abroad) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("id=").append(courseId).append("&").append(Constants.LOCAL_KEY).append("=")
                .append(local).append("&abroad=" + (abroad ? CspUserInfo.AbroadType.abroad.ordinal() : CspUserInfo.AbroadType.home.ordinal()));
        String signature = DESUtils.encode(Constants.DES_PRIVATE_KEY, buffer.toString());

        StringBuffer buffer2 = new StringBuffer();
        try {
            buffer2.append(appCspBase)
                    .append("api/meeting/share?signature=")
                    .append(URLEncoder.encode(signature, Constants.CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer2.toString();
    }


    /**
     * 获取小程序二维码
     * @param id
     * @param page
     * @return
     */
    @Override
    public String getMiniQRCode(Integer id, String page, String accessToken) throws IOException {
        String path = appFileUploadBase + "/mini/qrcode";
        //创建文件夹
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //如果文件存在，直接返回
        String codeUrl =  path + "/" +  id + "." + FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix;
        String showUrl = appFileBase + "/mini/qrcode/" +  id + "." + FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix;
        File file = new File(codeUrl);
        if(file.exists()){
            return showUrl;
        }
        //获取小程序码
        Map<String,Object> map = new HashMap<>();
        Integer start = page.indexOf("?") ;
        String scene = page.substring(start + 1);
        //去掉page参数的第一个 / ，不然图片会生成出错
        page = page.substring(1,start);
        map.put(SCENE_STR,scene);
        //小程序发布需要提交page参数，如果小程序没有发布，提交此参数获取的图片无法打开
        map.put(PAGE_STR,page);
        map.put(CODE_WIDTH_STR,430);
        map.put(CODE_AUTO_COLOR_STR,false);
        Map<String,String> colorMap = new HashMap<>();
        colorMap.put("r","0");
        colorMap.put("g","0");
        colorMap.put("b","0");
        map.put(CODE_LINE_COLOR_STR,colorMap);
        String url = MINI_CODE_URL + "?" + ACCESS_TOKEN_STR + "=" + accessToken;
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(map));
        HttpUtils.postJsonResponseStream(url,params,codeUrl);
        showUrl = appFileBase + "/mini/qrcode/" +  id + "." + FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix;
        return showUrl;
    }


    /**
     * 根据图片和标题创建课件和明细
     * @param files
     * @param course
     * @param theme
     * @return
     * @throws SystemException
     */
    @Override
    public Integer createAudioAndDetail(MultipartFile[] files, AudioCourse course, AudioCourseTheme theme) throws SystemException {
        //生成课件
          course.setCreateTime(new Date());
          course.setSourceType(AudioCourse.SourceType.QuickMeet.ordinal());
          course.setPlayType(AudioCourse.PlayType.normal.getType());
          course.setPublished(true);
          course.setShared(false);
          course.setDeleted(false);
          course.setLocked(false);
          course.setGuide(false);
          course.setStarRateFlag(false);
          insert(course);
          Integer courseId = course.getId();

          //创建讲本的主题和背景音乐
        if(theme.getImageId() != null || theme.getMusicId() != null){
            theme.setCourseId(courseId);
            audioCourseThemeDAO.insert(theme);
        }

        //相对路径
        String relativePath = FilePath.COURSE.path + "/" + courseId + "/ppt/";
        //文件保存路径
        String savePath = appFileUploadBase + relativePath;
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = null;
        //文件绝对路径
        String absoluteName = null;

        List<String> imgList = new ArrayList<>();
        for(MultipartFile file : files){
            fileName = UUIDUtil.getNowStringID() + "." + FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix;
            absoluteName = savePath + fileName;
            File saveFile = new File(absoluteName);
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                throw new SystemException(local("upload.error"));
            }
            imgList.add(relativePath + fileName);
        }
            updateAllDetails(courseId,imgList);


        return courseId;
    }

    /**
     * 小程序活动贺卡模板列表
     * @return
     */
    @Override
    public List<AudioCourseDTO> findMiniTemplate(){
        return audioCourseDAO.findMiniTemplate();
    }

    /**
     * 通过小程序二维码（固定）或者搜索小程序（随机） 返回贺卡模板
     * @param id
     * @return
     */
    @Override
    public AudioCourseDTO findMiniTemplateByIdOrRand(Integer id){
        return audioCourseDAO.findMiniTemplateByIdOrRand(id);
    }

    /**
     * 小程序 选择贺卡模板 制作有声贺卡
     * @param id 贺卡模板id
     * @param cspUserId
     * @return
     */
    @Override
    public Integer doCopyCourseTemplate(Integer id, String cspUserId) {
        AudioCourse course = audioCourseDAO.selectByPrimaryKey(id);
        Integer courseId = null;
        if (course != null) {
            // 复制模板生成讲本
            AudioCourse newCourse = new AudioCourse();
            BeanUtils.copyProperties(course, newCourse);
            newCourse.setId(null);
            newCourse.setCspUserId(cspUserId);
            newCourse.setCreateTime(new Date());
            newCourse.setSourceType(AudioCourse.SourceType.QuickMeet.ordinal()); // 生成快捷讲本
            audioCourseDAO.insert(newCourse);

            courseId = newCourse.getId();

            // 复制课程明细
            List<AudioCourseDetail> details = audioCourseDetailDAO.findDetailsByCourseId(id);
            if (details != null) {
                doCopyDetails(details, courseId);
            }

            // 查询该模板是否有背景图片和背景音乐
            AudioCourseTheme courseTheme = courseThemeDAO.findCourseThemeByCourseId(id);
            if (courseTheme != null) {
                doCopyCourseTheme(courseTheme, courseId);
            }
        }

        return courseId;
    }


    /**
     * 复制课程主题背景音乐 背景图片
     * @param courseTheme
     * @param courseId
     */
    public void doCopyCourseTheme(AudioCourseTheme courseTheme, Integer courseId) {
        AudioCourseTheme newCourseTheme = new AudioCourseTheme();
        newCourseTheme.setCourseId(courseId);
        newCourseTheme.setImageId(courseTheme.getImageId());
        newCourseTheme.setMusicId(courseTheme.getMusicId());
        courseThemeDAO.insert(newCourseTheme);
    }




    /**
     * 修改课件密码
     *
     * @param course
     * @param password
     */
    @Override
    public void doModifyPassword(AudioCourse course, String password) {
        course.setPassword(password);
        updateByPrimaryKey(course);
    }


    /**
     * 根据会议来源和会议类型筛选出会议列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<CourseDeliveryDTO> findMiniMeetingListByType(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true);
        return MyPage.page2Mypage((Page) audioCourseDAO.findMiniMeetingListByType(pageable.getParams()));
    }


    /**
     * 创建课件或者添加课件图片
     * @param file
     * @param course
     * @param sort
     * @return
     */
    @Override
    public Integer createAudioOrAddDetail(MultipartFile file, AudioCourse course, Integer sort,Integer type) throws SystemException {
        //创建课件
        if(course.getId() == null){
            //生成课件
            course.setCreateTime(new Date());
            course.setSourceType(AudioCourse.SourceType.QuickMeet.ordinal());
            course.setPlayType(type);
            course.setPublished(false);
            course.setShared(false);
            course.setDeleted(false);
            course.setLocked(false);
            course.setGuide(false);
            course.setStarRateFlag(false);
            insert(course);

            if(type == AudioCourse.PlayType.normal.ordinal()){
                AudioCoursePlay play = new AudioCoursePlay();
                play.setCourseId(course.getId());
                play.setId(StringUtils.nowStr());
                play.setPlayPage(0);
                play.setPlayState(0);
                audioCoursePlayDAO.insert(play);
            }

        }
        Integer courseId = course.getId();
        //添加课件图片

        //相对路径
        String relativePath = FilePath.COURSE.path + "/" + courseId + "/ppt/";
        //文件保存路径
        String savePath = appFileUploadBase + relativePath;
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = null;
        //文件绝对路径
        String absoluteName = null;
        fileName = UUIDUtil.getNowStringID() + "." + FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix;
        absoluteName = savePath + fileName;
        File saveFile = new File(absoluteName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new SystemException(local("upload.error"));
        }
        String imgUrl = relativePath + fileName;
        AudioCourseDetail detail = new AudioCourseDetail();
        detail.setCourseId(courseId);
        detail.setImgUrl(imgUrl);
        //小程序传过来的排序号是从0开始
        detail.setSort(sort + 1);
        addDetail(detail);

        return courseId;
    }

    @Override
    public ActivityGuideDTO findActivityCourse(Integer courseId) {
        return audioCourseDAO.findActivityCourse(courseId);
    }


    /**
     * 完善课件标题，创建课件主题和背景音乐
     * @param course
     * @param imgId
     * @param musicId
     */
    @Override
    public void updateCourseAndCreateTheme(AudioCourse course, Integer imgId, Integer musicId) {

        updateByPrimaryKeySelective(course);
        //创建课件主题，背景音乐
        AudioCourseTheme theme = new AudioCourseTheme();
        theme.setCourseId(course.getId());
        AudioCourseTheme result = audioCourseThemeDAO.selectOne(theme);
        //没有相关记录并且imgId,musicId有不为null的值，执行插入操作
        if(result ==  null && (imgId != null || musicId != null)){
            theme.setImageId(imgId);
            theme.setMusicId(musicId);
            audioCourseThemeDAO.insert(theme);
        }
    }
}
