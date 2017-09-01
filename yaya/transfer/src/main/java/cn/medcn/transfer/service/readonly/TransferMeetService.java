package cn.medcn.transfer.service.readonly;

import cn.medcn.transfer.model.readonly.MeetMenuReadOnly;
import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.service.base.ReadOnlyBaseService;
import cn.medcn.transfer.support.Pageable;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/14.
 */
public interface TransferMeetService extends ReadOnlyBaseService<MeetSourceReadOnly> {



    void transferMeet(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException;

    List<MeetSourceReadOnly> findByUser(String owner) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void transfer(MeetSourceReadOnly meetSourceReadOnly, Integer pubUserId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException;

    void transferExamByOnwer(String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;
    /**
     * 查找会议菜单
     * @param meetId
     * @return
     */
    List<MeetMenuReadOnly> findMenus(Long meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void transfer(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void transferByOwner(String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;

    void transferSurveyHistoryByOwner(String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException;

    void transferByOwerAndDate(String owner, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException;
}
