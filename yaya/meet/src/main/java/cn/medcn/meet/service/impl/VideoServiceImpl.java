package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.meet.dao.MeetVideoDAO;
import cn.medcn.meet.dao.VideoCourseDAO;
import cn.medcn.meet.dao.VideoCourseDetailDAO;
import cn.medcn.meet.dao.VideoHistoryDAO;
import cn.medcn.meet.dto.VideoCourseRecordDTO;
import cn.medcn.meet.dto.VideoProgressDTO;
import cn.medcn.meet.model.MeetVideo;
import cn.medcn.meet.model.VideoCourse;
import cn.medcn.meet.model.VideoCourseDetail;
import cn.medcn.meet.model.VideoHistory;
import cn.medcn.meet.service.VideoService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/17.
 */
@Service
public class VideoServiceImpl extends BaseServiceImpl<VideoCourse> implements VideoService {

    @Autowired
    private VideoCourseDAO videoCourseDAO;

    @Autowired
    private VideoCourseDetailDAO videoCourseDetailDAO;

    @Autowired
    private MeetVideoDAO meetVideoDAO;

    @Autowired
    private VideoHistoryDAO videoHistoryDAO;

    @Override
    public Mapper<VideoCourse> getBaseMapper() {
        return videoCourseDAO;
    }


    /**
     * 添加视频明细
     *
     * @param detail
     */
    @Override
    public void insertDetail(VideoCourseDetail detail) {
        videoCourseDetailDAO.insert(detail);
    }

    /**
     * 根据视频课程ID和父节点ID获取子节点
     *
     * @param preId
     * @return
     */
    @Override
    public List<VideoCourseDetail> findByPreid(Integer preId) {
        VideoCourseDetail condition = new VideoCourseDetail();
        condition.setPreId(preId);
        List<VideoCourseDetail> list = videoCourseDetailDAO.select(condition);
        return list;
    }


    /**
     * 查找会议视频信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    @Cacheable(value=DEFAULT_CACHE, key = "'meet_video_'+#meetId")
    public MeetVideo findMeetVideo(String meetId, Integer moduleId) {
        MeetVideo condition = new MeetVideo();
        //condition.setModuleId(moduleId);
        condition.setMeetId(meetId);
        MeetVideo video = meetVideoDAO.selectOne(condition);
        if(video.getCourseId() != null){
            VideoCourse course = videoCourseDAO.selectByPrimaryKey(video.getCourseId());
            VideoCourseDetail detailCondition = new VideoCourseDetail();
            detailCondition.setPreId(0);
            detailCondition.setCourseId(course.getId());
            List<VideoCourseDetail> details = videoCourseDetailDAO.select(detailCondition);
            course.setDetails(details);
            video.setCourse(course);
        }
        return video;
    }

    /**
     * 查询视频简单信息
     *
     * @param meetId
     * @param moduleId
     * @return
     */
    @Override
    public MeetVideo findMeetVideoSimple(String meetId, Integer moduleId) {
        MeetVideo condition = new MeetVideo();
        //condition.setModuleId(moduleId);
        condition.setMeetId(meetId);
        MeetVideo video = meetVideoDAO.selectOne(condition);
        return video;
    }

    /**
     * 添加或者更新视频记录
     *
     * @param history
     */
    @Override
    public void insertHistory(VideoHistory history) {
        VideoHistory condition = new VideoHistory();
        condition.setDetailId(history.getDetailId());
        condition.setUserId(history.getUserId());
        VideoHistory existedHistory = videoHistoryDAO.selectOne(condition);
        if(existedHistory == null){
            history.setEndTime(new Date());
            history.setStartTime(new Date(history.getEndTime().getTime()-history.getUsedtime()*1000));
            videoHistoryDAO.insert(history);
        }else{
            existedHistory.setEndTime(new Date());
            existedHistory.setFinished(existedHistory.getFinished() || (history.getFinished()));
            existedHistory.setUsedtime(history.getUsedtime());
            videoHistoryDAO.updateByPrimaryKeySelective(existedHistory);
        }
    }

    public void insertMeetVideo(MeetVideo video){
        meetVideoDAO.insert(video);
    }


    /**
     * 查找视频课程下面的主目录
     *
     * @param courseId
     * @return
     */
    @Override
    public List<VideoCourseDetail> findRootDetail(Integer courseId) {
        List<VideoCourseDetail> list = videoCourseDetailDAO.findRootDetail(courseId);
        return list;
    }

    /**
     * 添加视频课程 并管理对应的会议室视频模块
     *
     * @param video
     * @param course
     */
    @Override
    public Integer addCourse(MeetVideo video, VideoCourse course) {
        videoCourseDAO.insert(course);
        video.setCourseId(course.getId());
        meetVideoDAO.updateByPrimaryKeySelective(video);
        return course.getId();
    }

    /**
     * 查找视频明细
     *
     * @param detailId
     * @return
     */
    @Override
    public VideoCourseDetail findDetail(Integer detailId) {
        VideoCourseDetail detail = videoCourseDetailDAO.selectByPrimaryKey(detailId);
        return detail;
    }

    /**
     * 添加明细
     *
     * @param detail
     * @return
     */
    @Override
    public Integer addDetail(VideoCourseDetail detail) {
        videoCourseDetailDAO.insert(detail);
        return detail.getId();
    }

    /**
     * 修改明细
     *
     * @param detail
     * @return
     */
    @Override
    public Integer updateDetail(VideoCourseDetail detail) {
        videoCourseDetailDAO.updateByPrimaryKeySelective(detail);
        return detail.getId();
    }

    /**
     * 删除明细
     *
     * @param id
     */
    @Override
    public void deleteDetail(Integer id) {
        if(id != 0){
            //删除子目录
            VideoCourseDetail condition = new VideoCourseDetail();
            condition.setPreId(id);
            videoCourseDetailDAO.delete(condition);
            videoCourseDetailDAO.deleteByPrimaryKey(id);
        }
    }

    /**
     * 查询视频观看人数记录
     * @param pageable
     * @return
     */
    @Override
    public MyPage<VideoCourseRecordDTO> findVideoRecords(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<VideoCourseRecordDTO> page = (Page)videoCourseDetailDAO.findVideoRecordByMeetId(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 导出 查询视频观看人数记录
     * @param map
     * @return
     */
    @Override
    public List<VideoCourseRecordDTO> findVideoRecordExcel(Map<String, Object> map) {
        List<VideoCourseRecordDTO> list = videoCourseDetailDAO.findVideoRecordByMeetId(map);
        return list;
    }

    /**
     * 查询视频观看进度 记录
     * @param pageable
     * @return
     */
    @Override
    public MyPage<VideoProgressDTO> findVideoProgress(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<VideoProgressDTO> page = (Page) videoHistoryDAO.findVProgressByMeetId(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 导出 视频观看进度 记录
     * @param map
     * @return
     */
    @Override
    public List<VideoProgressDTO> findVProgressExcel(Map<String,Object> map){
        List<VideoProgressDTO> list = videoHistoryDAO.findVProgressByMeetId(map);
        return list;
    }


    /**
     * 查看用户的视频记录
     * @param history
     * @return
     */
    @Override
    public VideoHistory findVideoHistory(VideoHistory history) {
        history = videoHistoryDAO.selectOne(history);
        return history;
    }

    /**
     * 查询用户观看视频总时长
     * @param userId
     * @param meetId
     * @return
     */
    @Override
    public Integer findUserVideoWatchTime(String meetId, Integer userId) {
        Integer watchTime = videoHistoryDAO.findUserVideoWatchTime(meetId,userId);
        return watchTime;
    }

    /**
     * 查询会议视频总时长
     * @param meetId
     * @return
     */
    @Override
    public Integer findVideoTotalTime(String meetId) {
        Integer totalTime = videoHistoryDAO.findVideoTotalTime(meetId);
        return totalTime;
    }
}
