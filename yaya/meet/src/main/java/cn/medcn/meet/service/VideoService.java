package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.VideoCourseRecordDTO;
import cn.medcn.meet.dto.VideoProgressDTO;
import cn.medcn.meet.model.MeetVideo;
import cn.medcn.meet.model.VideoCourse;
import cn.medcn.meet.model.VideoCourseDetail;
import cn.medcn.meet.model.VideoHistory;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/17.
 */
public interface VideoService extends BaseService<VideoCourse> {

    /**
     * 添加视频明细
     * @param detail
     */
    void insertDetail(VideoCourseDetail detail);

    /**
     * 根据视频课程ID和父节点ID获取子节点
     * @param preId
     * @return
     */
    List<VideoCourseDetail> findByPreid(Integer preId);

    /**
     * 查找会议视频信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetVideo findMeetVideo(String meetId, Integer moduleId);

    /**
     * 查询视频简单信息
     * @param meetId
     * @param moduleId
     * @return
     */
    MeetVideo findMeetVideoSimple(String meetId, Integer moduleId);

    void insertMeetVideo(MeetVideo video);

    /**
     * 添加或者更新视频记录
     * @param history
     */
    void insertHistory(VideoHistory history);

    /**
     * 查找视频课程下面的主目录
     * @param courseId
     * @return
     */
    List<VideoCourseDetail> findRootDetail(Integer courseId);

    /**
     * 添加视频课程 并管理对应的会议室视频模块
     * @param video
     * @param course
     */
    Integer addCourse(MeetVideo video, VideoCourse course);

    /**
     * 查找视频明细
     * @param detailId
     * @return
     */
    VideoCourseDetail findDetail(Integer detailId);

    /**
     * 添加明细
     * @param detail
     * @return
     */
    Integer addDetail(VideoCourseDetail detail);

    /**
     * 修改明细
     * @param detail
     * @return
     */
    Integer updateDetail(VideoCourseDetail detail);

    /**
     * 删除明细
     * @param id
     */
    void deleteDetail(Integer id);

    /**
     * 查询视频观看人数记录
     * @param pageable
     * @return
     */
    MyPage<VideoCourseRecordDTO> findVideoRecords(Pageable pageable);

    /**
     * 导出 查询视频观看人数记录
     * @param map
     * @return
     */
    List<VideoCourseRecordDTO> findVideoRecordExcel(Map<String,Object> map);

    /**
     * 查询视频观看进度 记录
     * @param pageable
     * @return
     */
    MyPage<VideoProgressDTO> findVideoProgress(Pageable pageable);

    /**
     * 导出 视频观看进度 记录
     * @param map
     * @return
     */
    List<VideoProgressDTO> findVProgressExcel(Map<String,Object> map);


    /**
     * 查看视频记录
     * @param history
     * @return
     */
    VideoHistory findVideoHistory(VideoHistory history);

    /**
     * 查询用户观看视频总时长
     * @param userId
     * @param meetId
     * @return
     */
    Integer findUserVideoWatchTime(String meetId,Integer userId);

    /**
     * 查询会议视频总时长
     * @param meetId
     * @return
     */
    Integer findVideoTotalTime(String meetId);
}
