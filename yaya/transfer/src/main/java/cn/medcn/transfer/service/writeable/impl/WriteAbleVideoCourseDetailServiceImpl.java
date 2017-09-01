package cn.medcn.transfer.service.writeable.impl;

import cn.medcn.transfer.model.writeable.VideoCourseDetail;
import cn.medcn.transfer.service.base.WriteAbleBaseServiceImpl;
import cn.medcn.transfer.service.writeable.WriteAbleVideoCourseDetailService;

/**
 * Created by lixuan on 2017/6/20.
 */
public class WriteAbleVideoCourseDetailServiceImpl extends WriteAbleBaseServiceImpl<VideoCourseDetail> implements WriteAbleVideoCourseDetailService {
    @Override
    public String getTable() {
        return "t_video_course_detail";
    }




}
