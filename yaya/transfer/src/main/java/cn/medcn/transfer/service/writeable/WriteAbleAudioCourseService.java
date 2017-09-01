package cn.medcn.transfer.service.writeable;

import cn.medcn.transfer.model.readonly.PptReadOnly;
import cn.medcn.transfer.model.writeable.AudioCourse;
import cn.medcn.transfer.model.writeable.VideoHistory;
import cn.medcn.transfer.service.base.WriteAbleBaseService;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public interface WriteAbleAudioCourseService extends WriteAbleBaseService<AudioCourse> {

    Integer transferCourse(AudioCourse course, List<PptReadOnly> pptList, String meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException;



}
