package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.VideoReadOnly;
import cn.medcn.transfer.model.readonly.VideoRecordReadOnly;
import cn.medcn.transfer.model.writeable.*;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.TransferVideoService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetModuleService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetVideoService;
import cn.medcn.transfer.service.writeable.WriteAbleVideoCourseService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetModuleServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetVideoServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleVideoCourseServiceImpl;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.DateUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/16.
 */
public class TransferVideoServiceImpl extends ReadOnlyBaseServiceImpl<VideoReadOnly> implements TransferVideoService {
    @Override
    public String getTable() {
        return "t_meeting_video";
    }

    @Override
    public String getIdKey() {
        return "videoId";
    }

    private static final Long GUANG_YI_VIDEO_MEETING_ID = 13108L;//广一视频会议ID

    private static final Map<String, Boolean> GUANG_YI_LEVEL_ONE_CATEGORY = new HashMap<>();

    private static final String GUANG_YI_FIRST_LEVEL_CATEGORY = "专业技术类";

    static {
        GUANG_YI_LEVEL_ONE_CATEGORY.put("专业技术类", Boolean.TRUE);
        GUANG_YI_LEVEL_ONE_CATEGORY.put("政策法律法规类", Boolean.TRUE);
        GUANG_YI_LEVEL_ONE_CATEGORY.put("人文医学类", Boolean.TRUE);
        GUANG_YI_LEVEL_ONE_CATEGORY.put("教育技术类", Boolean.TRUE);
    }


    private WriteAbleVideoCourseService writeAbleVideoCourseService = new WriteAbleVideoCourseServiceImpl();

    private WriteAbleMeetModuleService writeAbleMeetModuleService = new WriteAbleMeetModuleServiceImpl();

    private WriteAbleMeetVideoService writeAbleMeetVideoService = new WriteAbleMeetVideoServiceImpl();

    /**
     * 转换会议视频
     *
     * @param source
     * @param meet
     */
    @Override
    public void transfer(MeetSourceReadOnly source, Meet meet) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        LogUtils.debug(this.getClass(), "开始转换会议["+source.getMeetingName()+"]视频信息 ...");
        List<VideoReadOnly> videoReadOnlyList = findVideoList(source.getMeetingId());
        Integer courseId = createCourseInfo(source, meet);
        //添加视频模块
        Integer moduleId = createVideoModule(meet.getId());
        LogUtils.debug(this.getClass(), "创建会议["+source.getMeetingName()+"]的视频模块成功 !!!");
        //添加会议视频
        createMeetVideo(meet.getId(), moduleId, courseId);
        if(source.getMeetingId().intValue() == GUANG_YI_VIDEO_MEETING_ID.intValue()){//广一需要创建以及栏目
            createGuangYiFolders(courseId);
            LogUtils.debug(this.getClass(),"创建广一视频目录");
            VideoCourseDetail firstLevelDetail = writeAbleVideoCourseService.findDetail(GUANG_YI_FIRST_LEVEL_CATEGORY, courseId);
            for(VideoReadOnly videoReadOnly : videoReadOnlyList){
                if(videoReadOnly.getVideoCatagroy()==null){
                    videoReadOnly.setVideoCatagroy("");
                }
                if(GUANG_YI_LEVEL_ONE_CATEGORY.get(videoReadOnly.getVideoCatagroy())!= null && GUANG_YI_LEVEL_ONE_CATEGORY.get(videoReadOnly.getVideoCatagroy())){//直接是一级栏目的情况
                    createVideoCourseDetail(courseId, videoReadOnly, 0, meet.getId());
                }else{
                    //存在二级栏目的情况 先查询出一级栏目
                    createVideoCourseDetail(courseId, videoReadOnly, firstLevelDetail.getId(), meet.getId());
                }
            }
        }else{
            for(VideoReadOnly videoReadOnly : videoReadOnlyList){
                createVideoCourseDetail(courseId, videoReadOnly, 0, meet.getId());
            }
        }
        LogUtils.debug(this.getClass(), "转换会议["+source.getMeetingName()+"]的视频信息成功 !!!");

        LogUtils.debug(this.getClass(), "创建会议["+source.getMeetingName()+"]会议视频成功 !!!");
    }


    private Long createMeetVideo(String meetId, Integer moduleId, Integer courseId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MeetVideo meetVideo = new MeetVideo();
        meetVideo.setMeetId(meetId);
        meetVideo.setCourseId(courseId);
        meetVideo.setModuleId(moduleId);
        return (Long)writeAbleMeetVideoService.insert(meetVideo);

    }


    private Integer createVideoModule(String meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        MeetModule module = new MeetModule();
        module.setModuleName(MeetModule.ModuleFunction.VIDEO.getFunName());
        module.setFunctionId(MeetModule.ModuleFunction.VIDEO.getFunId());
        module.setMeetId(meetId);
        module.setActive(true);
        module.setMainFlag(false);
        Integer moduleId = writeAbleMeetModuleService.addMeetmodule(module);
        return moduleId;
    }


    public void createVideoCourseDetail(Integer courseId, VideoReadOnly videoReadOnly, Integer firstLevelCategoryId,String meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        VideoCourseDetail parent = writeAbleVideoCourseService.findDetail(videoReadOnly.getVideoCatagroy(), courseId);
        Integer parentId = 100000;

        if(parent == null){//父级栏目不存在的情况 先生成父级栏目
            parentId = createParent(parentId+videoReadOnly.getVideoId().intValue(), courseId, videoReadOnly.getVideoCatagroy(), firstLevelCategoryId);
        }else{
            parentId = parent.getId();
        }
        VideoCourseDetail detail = new VideoCourseDetail();
        detail.setId(videoReadOnly.getVideoId().intValue());
        detail.setName(videoReadOnly.getVideoTitle());
        detail.setType(1);
        detail.setCourseId(courseId);
        detail.setPreId(parentId);
        detail.setDuration(videoReadOnly.getVideoTime());
        detail.setVideoType(1);
        detail.setUrl(videoReadOnly.getVideoUrl());
        writeAbleVideoCourseService.addVideoCourseDetail(detail);

        LogUtils.debug(this.getClass(), "开始转换视频学习记录 ...");
        List<VideoRecordReadOnly> list = findRecordByVideoId(videoReadOnly.getVideoId());
        for(VideoRecordReadOnly videoRecordReadOnly : list){
            writeAbleVideoCourseService.addVideoHistory(VideoHistory.build(videoRecordReadOnly, meetId, videoReadOnly.getVideoId().intValue()));
        }
        LogUtils.debug(this.getClass(), "转换视频学习记录成功 !!!");
    }


    private List<VideoRecordReadOnly> findRecordByVideoId(Long videoId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_video_record where video_id = ?";
        Object[] params = new Object[]{videoId};
        List<VideoRecordReadOnly> list = (List<VideoRecordReadOnly>) DAOUtils.selectList(getConnection(), sql, params, VideoRecordReadOnly.class);
        return list;
    }


    private Integer createCourseInfo(MeetSourceReadOnly source, Meet meet) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        VideoCourse course = new VideoCourse();
        course.setCategory(source.getKind());
        course.setCreateTime(DateUtils.parseDate(source.getStartTime()));
        course.setOwner(meet.getOwnerId());
        course.setPublished(true);
        course.setTitle(source.getMeetingName());
        Long courseId = (Long) writeAbleVideoCourseService.insert(course);
        return courseId.intValue();
    }


    private void createGuangYiFolders(Integer courseId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        Integer baseId = 500000;
        int sort = 1;
        for(String key : GUANG_YI_LEVEL_ONE_CATEGORY.keySet()){
            createParent(baseId+sort, courseId, key, 0);
            sort++;
        }
    }


    private Integer createParent(Integer id, Integer courseId, String name, Integer parentId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        VideoCourseDetail detail = new VideoCourseDetail();
        detail.setCourseId(courseId);
        detail.setId(id);
        detail.setPreId(parentId);
        detail.setName(name);
        detail.setType(0);
        return writeAbleVideoCourseService.addVideoCourseDetail(detail);
    }




    private List<VideoReadOnly> findVideoList(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        VideoReadOnly condition = new VideoReadOnly();
        condition.setMeetingId(meetId);
        List<VideoReadOnly> list = this.findList(condition);
        return list;
    }
}
