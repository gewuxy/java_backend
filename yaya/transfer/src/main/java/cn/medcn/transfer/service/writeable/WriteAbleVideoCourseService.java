package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.writeable.VideoCourse;
import cn.medcn.transfer.model.writeable.VideoCourseDetail;
import cn.medcn.transfer.model.writeable.VideoHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lixuan on 2017/6/16.
 */
public interface WriteAbleVideoCourseService extends WriteAbleBaseService<VideoCourse> {

    Integer addVideoCourse(VideoCourse videoCourse) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Integer addVideoCourseDetail(VideoCourseDetail courseDetail) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * 根据名称查找视频明细
     * @param category
     * @param courseId
     * @return
     */
    VideoCourseDetail findDetail(String category, Integer courseId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void addVideoHistory(VideoHistory videoHistory) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
