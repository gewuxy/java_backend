package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.*;
import cn.medcn.transfer.model.writeable.Lecturer;
import cn.medcn.transfer.model.writeable.Meet;
import cn.medcn.transfer.model.writeable.MeetAttend;
import cn.medcn.transfer.model.writeable.MeetProperty;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.*;
import cn.medcn.transfer.service.writeable.WriteAbleMeetAttendService;
import cn.medcn.transfer.service.writeable.WriteAbleMeetService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetAttendServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMeetServiceImpl;
import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;
import cn.medcn.transfer.utils.StringUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/14.
 */
public class TransferMeetServiceImpl extends ReadOnlyBaseServiceImpl<MeetSourceReadOnly> implements TransferMeetService {

    private static final String MEET_MENU_TABLE_NAME = "t_meeting_menu";

    private TransferMessageService transferMessageService = new TransferMessageServiceImpl();

    private TransferExamService transferExamService = new TransferExamServiceImpl();

    private TransferRecomdService transferRecomdService = new TransferRecomdServiceImpl();

    private TransferVideoService transferVideoService = new TransferVideoServiceImpl();


    private TransferSignService transferSignService = new TransferSignServiceImpl();

    private TransferMaterialService transferMaterialService = new TransferMeetMaterialServiceImpl();

    private WriteAbleMeetService writeAbleMeetService = new WriteAbleMeetServiceImpl();

    private TransferPubuserService transferPubuserService = new TransferPubuserServiceImpl();

    private WriteAbleMeetAttendService writeAbleMeetAttendService = new WriteAbleMeetAttendServiceImpl();

    private TransferDoctorService transferDoctorService = new TransferDoctorServiceImpl();

    @Override
    public String getIdKey() {
        return "meetingId";
    }

    @Override
    public void transferMeet(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException {
        MeetSourceReadOnly condition = new MeetSourceReadOnly();
        condition.setMeetingId(meetId);
        MeetSourceReadOnly meetSourceReadOnly = findOne(condition);
        if(meetSourceReadOnly != null){
            //仅作为测试 默认单位号ID为14
            transfer(meetSourceReadOnly, 14);
        }
    }

    @Override
    public List<MeetSourceReadOnly> findByUser(String owner) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MeetSourceReadOnly condition = new MeetSourceReadOnly();
        condition.setOwner(owner);
        List<MeetSourceReadOnly> list = findList(condition);
        return list;
    }

    @Override
    public String getTable() {
        return "t_meeting";
    }


    @Override
    public void transferExamByOnwer(String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<MeetSourceReadOnly> meetSourceReadOnlyList = findByUser(owner);
        for(MeetSourceReadOnly meetSourceReadOnly : meetSourceReadOnlyList){
            List<MeetMenuReadOnly> menuList = findMenus(meetSourceReadOnly.getMeetingId());
            if(menuList != null && menuList.size() > 0){
                Meet condition = new Meet();
                condition.setOldId(meetSourceReadOnly.getMeetingId().intValue());
                Meet meet = writeAbleMeetService.findOne(condition);
                for(MeetMenuReadOnly menu:menuList){
                    if(menu.getMenuName().equals(MeetMenuReadOnly.MEET_MENU_EXAM)){//考试模块
                        transferExamService.transferExamHistory(meetSourceReadOnly, meet);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void transfer(MeetSourceReadOnly meetSourceReadOnly, Integer pubUserId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException {
        if(checkMeetExisted(meetSourceReadOnly.getMeetingId().intValue())){
            return;
        }

        Long meetId = meetSourceReadOnly.getMeetingId();
        if(meetId == 0){
            return;
        }
        // ============保存会议基本信息 开始==============
        Meet meet = Meet.build(meetSourceReadOnly);
        meet.setOwnerId(pubUserId);
        MeetProperty property = MeetProperty.build(meetSourceReadOnly);

        // ============保存会议基本信息 结束==============

        // ============保存会议主讲者信息 开始==============
        MeetRecomdReadOnly recomd = transferRecomdService.findMeetRecomd(meetId.intValue());
        meet.setId(StringUtils.getNowStringID());
        writeAbleMeetService.transferMeet(meet);

        Lecturer lecturer = Lecturer.build(meetSourceReadOnly, recomd);
        lecturer.setMeetId(meet.getId());
        if(recomd != null && recomd.getReStatus() == 2){//正在推荐中
            meet.setTuijian(true);
        }else{
            meet.setTuijian(false);
        }
        writeAbleMeetService.addMeetLecurter(lecturer);

        property.setMeetId(meet.getId());
        writeAbleMeetService.transferMeetProperty(property);//转换会议属性

        // ============保存会议主讲者信息 结束==============

        //===============保存会议签到模块===================
        if(MeetSourceReadOnly.YES.equals(meetSourceReadOnly.getSignFlag())){//有签到模块
            transferSignService.transfer(meetSourceReadOnly, meet);
        }

        // ================保存会议PPT模块=======================
        transferMessageService.transfer(meetSourceReadOnly, meet);

        LogUtils.debug(this.getClass(), "开始转换会议资料 ...");
        //转换会议资料
        transferMaterialService.transfer(meetSourceReadOnly, meet);
        LogUtils.debug(this.getClass(), "处理会议资料成功 !!!");

        List<MeetMenuReadOnly> menuList = findMenus(meetId);
        if(menuList != null){
            boolean examUsed = false;
            for(MeetMenuReadOnly menu : menuList){
                if(menu.getMenuName().equals(MeetMenuReadOnly.MEET_MENU_EXAM)){//考试模块
                    if(examUsed == true){
                        continue;
                    }
                    transferExamService.transferExam(meetSourceReadOnly, meet);
                    examUsed = true;
                }else if(menu.getMenuName().equals(MeetMenuReadOnly.MEET_MENU_SURVEY)){//问卷模块
                    transferExamService.transferSurvey(meetSourceReadOnly, meet);
                }else if(menu.getMenuName().equals(MeetMenuReadOnly.MEET_MENU_VIDEO)){//视频模块
                    transferVideoService.transfer(meetSourceReadOnly, meet);
                }
            }
        }

        //转换会议参会记录
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 的参会记录 ...");
        transferMeetMembers(meetSourceReadOnly.getMeetingId(), meet.getId());
        LogUtils.debug(this.getClass(), "开始转换会议["+meetSourceReadOnly.getMeetingName()+"] 的参会记录 ...");
    }


    private List<MeetMemberReadOnly> findMeetMembers(Long meetId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select mm.* from t_meeting_member mm INNER JOIN t_meeting_group mg on mg.group_id = mm.group_id " +
                "where mg.meeting_id = ?";
        Object[] params = new Object[]{meetId};
        List<MeetMemberReadOnly> list = (List<MeetMemberReadOnly>) DAOUtils.selectList(getConnection(), sql, params, MeetMemberReadOnly.class);
        return list;
    }


    private void transferMeetMembers(Long oloMeetId, String newMeetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<MeetMemberReadOnly> list = findMeetMembers(oloMeetId);
        if(list.isEmpty()){
            LogUtils.warm(this.getClass(), "未找到会议的参会人员信息 ...");
        }else{
            for(MeetMemberReadOnly meetMemberReadOnly : list){
                LogUtils.debug(this.getClass(), "开始保存用户["+meetMemberReadOnly.getAttender()+"] 的参会记录 ....");
                DoctorReadOnly doctorReadOnly = transferDoctorService.findPubUserByUname(meetMemberReadOnly.getAttender());
                writeAbleMeetAttendService.insert(MeetAttend.build(meetMemberReadOnly, newMeetId, doctorReadOnly == null ? 0:doctorReadOnly.getD_id().intValue()));
                LogUtils.debug(this.getClass(), "保存用户["+meetMemberReadOnly.getAttender()+"] 的参会记录成功 !!!");
            }
        }

    }

    /**
     * 查找会议菜单
     *
     * @param meetId
     * @return
     */
    @Override
    public List<MeetMenuReadOnly> findMenus(Long meetId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        MeetMenuReadOnly condition = new MeetMenuReadOnly();
        condition.setMeetingId(meetId.intValue());
        List<MeetMenuReadOnly> list = (List<MeetMenuReadOnly>) DAOUtils.selectList(this.getConnection(), condition, MEET_MENU_TABLE_NAME);
        return list;
    }


    private boolean checkMeetExisted(Integer oldId) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return writeAbleMeetService.checkExisted(oldId);
    }

    private List<MeetSourceReadOnly> findAllMeets() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_meeting where state > 0 and state < 4";
        List<MeetSourceReadOnly> list = (List<MeetSourceReadOnly>) DAOUtils.selectList(getConnection(), sql, null, MeetSourceReadOnly.class);
        return list;
    }


    @Override
    public void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
//        List<MeetSourceReadOnly> list = findAllMeets();
//        transferList(list);
    }

    @Override
    public void transfer(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {

        Page page = findByPage(pageable);
        List<MeetSourceReadOnly> list = (List<MeetSourceReadOnly>) page.getDatas();
//        transferList(list);
    }

    private void transferList(List<MeetSourceReadOnly> list, String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        //先加载所有公众号放到map中备用
        Map<String, Integer> pubUserMap = transferPubuserService.findAll();
        PubUserSourceReadOnly pubUser = transferPubuserService.findPubUserByUname(owner);
        for(MeetSourceReadOnly meetSourceReadOnly : list){
            transfer(meetSourceReadOnly, pubUser==null?0:pubUserMap.get(pubUser.getPub_uname()));
        }
    }


    @Override
    public void transferByOwner(String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        List<MeetSourceReadOnly> meetList = findByUser(owner);
        transferList(meetList, owner);
    }

    @Override
    public void transferSurveyHistoryByOwner(String owner) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<MeetSourceReadOnly> meetSourceReadOnlyList = findByUser(owner);
        for(MeetSourceReadOnly meetSourceReadOnly : meetSourceReadOnlyList){
            List<MeetMenuReadOnly> menuList = findMenus(meetSourceReadOnly.getMeetingId());
            if(menuList != null && menuList.size() > 0){
                Meet condition = new Meet();
                condition.setOldId(meetSourceReadOnly.getMeetingId().intValue());
                Meet meet = writeAbleMeetService.findOne(condition);
                for(MeetMenuReadOnly menu:menuList){
                    if(menu.getMenuName().equals(MeetMenuReadOnly.MEET_MENU_SURVEY)){//问卷模块
                        transferExamService.transferSurveyHistory(meetSourceReadOnly, meet);
                        break;
                    }
                }
            }
        }
    }

    private List<MeetSourceReadOnly> findMeetListByOwnerAndDate(String owner, String date) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select * from t_meeting where owner=? and state < 4 and state > 0 and start_time > ? ";
        Object[] params = {owner, date};
        List<MeetSourceReadOnly> list = (List<MeetSourceReadOnly>) DAOUtils.selectList(getConnection(), sql, params, MeetSourceReadOnly.class);
        return list;
    }

    @Override
    public void transferByOwerAndDate(String owner, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        transferList(findMeetListByOwnerAndDate(owner, date), owner);
    }
}
