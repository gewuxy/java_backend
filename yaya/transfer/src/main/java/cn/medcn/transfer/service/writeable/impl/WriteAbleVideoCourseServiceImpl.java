package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.VideoCourse;
import cn.medcn.transfer.model.writeable.VideoCourseDetail;
import cn.medcn.transfer.model.writeable.VideoHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleVideoCourseDetailService;
import cn.medcn.transfer.service.writeable.WriteAbleVideoCourseService;
import cn.medcn.transfer.service.writeable.WriteAbleVideoHistoryService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/19.
 */
public class WriteAbleVideoCourseServiceImpl extends WriteAbleBaseServiceImpl<VideoCourse> implements WriteAbleVideoCourseService {
    @Override
    public String getTable() {
        return "t_video_course";
    }

    private WriteAbleVideoCourseDetailService writeAbleVideoCourseDetailService = new WriteAbleVideoCourseDetailServiceImpl();

    private WriteAbleVideoHistoryService writeAbleVideoHistoryService = new WriteAbleVideoHistoryServiceImpl();

    @Override
    public Integer addVideoCourse(VideoCourse videoCourse) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Integer courseId = (Integer) insert(videoCourse);
        return courseId;
    }


    @Override
    public Integer addVideoCourseDetail(VideoCourseDetail courseDetail) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Long detailId = (Long) writeAbleVideoCourseDetailService.insert(courseDetail);
        return detailId.intValue();
    }


    /**
     * 根据名称查找视频明细
     *
     * @param category
     * @param courseId
     * @return
     */
    @Override
    public VideoCourseDetail findDetail(String category, Integer courseId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        VideoCourseDetail condition = new VideoCourseDetail();
        condition.setName(category);
        condition.setCourseId(courseId);
        return writeAbleVideoCourseDetailService.findOne(condition);
    }


    @Override
    public void addVideoHistory(VideoHistory videoHistory) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        writeAbleVideoHistoryService.insert(videoHistory);
    }
}
