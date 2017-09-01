package cn.medcn.transfer.service.readonly.impl;

import cn.medcn.transfer.model.readonly.*;
import cn.medcn.transfer.model.writeable.AppUser;
import cn.medcn.transfer.model.writeable.AppUserDetail;
import cn.medcn.transfer.model.writeable.xsCredits;
import cn.medcn.transfer.service.base.ReadOnlyBaseServiceImpl;
import cn.medcn.transfer.service.readonly.*;
import cn.medcn.transfer.service.writeable.WriteAbleAppUserDetailService;
import cn.medcn.transfer.service.writeable.WriteAbleAppUserService;
import cn.medcn.transfer.service.writeable.WriteAbleXsCreditService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleAppUserDetailServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleAppUserServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleXsCreditServiceImpl;
import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.FileUtils;
import cn.medcn.transfer.utils.LogUtils;

import javax.print.Doc;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class TransferDoctorServiceImpl extends ReadOnlyBaseServiceImpl<DoctorReadOnly> implements TransferDoctorService {
    @Override
    public String getTable() {
        return "t_doctor";
    }

    @Override
    public String getIdKey() {
        return "d_id";
    }

    private WriteAbleAppUserService writeAbleAppUserService = new WriteAbleAppUserServiceImpl();
    private WriteAbleAppUserDetailService writeAbleAppUserDetailService = new WriteAbleAppUserDetailServiceImpl();
    private TransferDepartmentService transferDepartmentService = new TransferDepartmentServiceImpl();
    private TransferHospitalService transferHospitalService = new TransferHospitalServiceImpl();
    private TransferPubuserService transferPubuserService = new TransferPubuserServiceImpl();

    private TransferPubuserMaterialService transferPubuserMaterialService = new TransferPubuserMaterialServiceImpl();
    private TransferPubuserMemberService transferPubuserMemberService = new TransferPubuserMemberServiceImpl();
    private TransferUserGroupService transferUserGroupService = new TransferUserGroupServiceImpl();

    private WriteAbleXsCreditService writeAbleXsCreditService = new WriteAbleXsCreditServiceImpl();
    /**
     * 转换医生信息
     * @param doctorReadOnly
     * @param headimg
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public void transfer(DoctorReadOnly doctorReadOnly,String headimg) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        // 插入数据之前 检查用户是否已经存在
        Integer appUserId = writeAbleAppUserService.findIdByOldName(doctorReadOnly.getD_uname());
        if(appUserId==null || appUserId==0) {
            // 插入公众号或用户信息数据
            addAppUser(doctorReadOnly,headimg);
        }

    }

    /**
     * 插入用户信息 及 用户明细表
     * @param doctorReadOnly
     * @param headimg
     * @throws IllegalAccessException
     */
    public void addAppUser(DoctorReadOnly doctorReadOnly,String headimg) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        // 插入用户信息
        AppUser appUser = AppUser.build(doctorReadOnly,headimg);
        Integer id = writeAbleAppUserService.addAppUserFromOldData(appUser);

        xsCredits xs = new xsCredits();
        xs.setUserId(doctorReadOnly.getD_id().intValue());
        xs.setCredit(doctorReadOnly.getD_credit());
        writeAbleXsCreditService.addXsCredit(xs);

        if(id == null){
            return ;
        }
        LogUtils.debug(TransferDoctorServiceImpl.class, "转换用户数据：" + appUser.getOldId() + " == 用户姓名：" + appUser.getNickname());
        // 插入用户明细
        AppUserDetail detail = AppUserDetail.build(doctorReadOnly, id);
        writeAbleAppUserDetailService.addAppUserDetailFromOldData(detail);
    }

    /**
     * 查找所有的用户数据
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<DoctorReadOnly> findUserList() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        String sql = "select d_id,d_did,d_hid,d_name,d_uname,d_degree,d_gender,d_age,d_zc,d_password,d_email,d_province,d_license," +
                "d_city,d_mobile,permission,d_zw,d_sign,d_credit,d_ic_cardnum,d_identifynum,zc_time,user_hos,user_ks,invite_count,user_role" +
                " from t_doctor where d_id between 32707 and 623318";
        List<DoctorReadOnly> doctorList = (List<DoctorReadOnly>) DAOUtils.selectList(getConnection(),sql,null,DoctorReadOnly.class);
        return doctorList;
    }


    /**
     * 获取用户头像
     * @param doctorId
     * @return
     */
    @Override
    public Blob getUserImgBlob(Long doctorId) throws SQLException {
        String sql = "select d_picture from t_doctor where d_id=? and d_picture is not null";
        Object params[] = {doctorId};
        Blob imgBlob = DAOUtils.getBlobData(getConnection(),sql,params);
        if(imgBlob!=null && imgBlob.length()!=0){
            return imgBlob;
        }else{
            return null;
        }
    }

    /**
     * 转换医生用户数据
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws SQLException
     */
    @Override
    public int transferAppUser(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException,IOException, SQLException  {
        Page page = findUserListByPage(pageable);
        List<DoctorReadOnly> doctorList = (List<DoctorReadOnly>)page.getDatas();
        LogUtils.debug(this.getClass(),"userlist size: "+doctorList.size());
        doTransferUserList(doctorList);
        return page.getPages();
    }


    private void doTransferUserList(List<DoctorReadOnly> doctorList) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, SQLException, IOException {
        for (DoctorReadOnly doctor : doctorList){
            LogUtils.debug(this.getClass(), "正在处理用户["+doctor.getD_uname()+"]的信息 ...");
            Integer did = writeAbleAppUserService.findIdByOldName(doctor.getD_uname());
            if(did!=null){
                continue;
            }
            // 根据医院ID查询医院名称、等级
            Long hosId = doctor.getD_hid();
            if(hosId!=null && hosId!=0){
                HospitalReadOnly hospitalReadOnly = transferHospitalService.getHospital(hosId);
                if (hospitalReadOnly!=null){
                    doctor.setD_hid(hosId);
                    doctor.setUser_hos(hospitalReadOnly.getH_cname());
                    doctor.setHoslevel(hospitalReadOnly.getH_level());
                }else{
                    doctor.setD_hid(0L);
                    doctor.setUser_hos("其他");
                    doctor.setHoslevel("其他");
                }

            }

            // 根据科室ID查询科室名称
            Long depId = doctor.getD_did();
            if (depId!=null && depId!=0){
                DepartmentReadOnly departmentReadOnly = transferDepartmentService.getDepartment(depId);
                doctor.setD_did(depId);
                doctor.setUser_ks(departmentReadOnly.getD_dname());
            }

            Long doctorId = doctor.getD_id();

            // 根据用户角色 如果是公众号 查找公众号信息
            if(doctor.getUser_role()==1){
                PubUserSourceReadOnly pubUserSourceReadOnly = transferPubuserService.transferUserInfo(doctorId);
                doctor.setPubInfo(pubUserSourceReadOnly.getPub_info());
                doctor.setPubType(pubUserSourceReadOnly.getPub_type());
                doctor.setAttentionGiveCredits(pubUserSourceReadOnly.getAttention_give_credits());
            }

            // 获取 用户头像
            Blob imgBlob = getUserImgBlob(doctorId);
            String headimg = "";
            if(imgBlob!=null && imgBlob.length()>1024){
                headimg = headimg + FileUtils.saveHeadImg(imgBlob,".jpg");
            }

            // 转换数据
            transfer(doctor,headimg);
        }
    }

    /**
     * 分页查询医生数据
     * @param pageable
     * @return
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Page findUserListByPage(Pageable pageable) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        String sql = "select d_id,d_did,d_hid,d_name,d_uname,d_degree,d_gender,d_age,d_zc,d_password,d_email,d_province,d_license," +
                "d_city,d_mobile,permission,d_zw,d_sign,d_credit,d_ic_cardnum,d_identifynum,zc_time,user_hos,user_ks,invite_count,user_role" +
                " from t_doctor where d_id >928192 ";
        Page userPage = DAOUtils.findByPage(pageable,getConnection(),sql,getTable(),getIdKey());
        return userPage;
    }

    @Override
    public void transferUserdata() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, SQLException {
        Pageable pageable = new Pageable();
        pageable.setPageNum(1);
        pageable.setPageSize(10000);
        DoctorReadOnly condition = new DoctorReadOnly();
        condition.setUser_role(0);
        pageable.setCondition(condition);
        int pages = transferAppUser(pageable);
        for (int i=2;i<=pages;i++){
            pageable.setPageNum(i);
            transferAppUser(pageable);

        }
    }


    @Override
    public DoctorReadOnly findPubUserByUname(String userName) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select d_id, d_uname from t_doctor where d_uname = ? ";
        Object[] params = new Object[]{userName};
        DoctorReadOnly doctorReadOnly = (DoctorReadOnly) DAOUtils.selectOne(getConnection(), sql, params, DoctorReadOnly.class);
        return doctorReadOnly;
    }


    // 根据用户名 转换用户相关数据
    public void transferUserByUsername(String username) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, SQLException, IOException {
        String sql = "select d_id,d_did,d_hid,d_name,d_uname,d_degree,d_gender,d_age,d_zc,d_password,d_email,d_province,d_license," +
                "d_city,d_mobile,permission,d_zw,d_sign,d_credit,d_ic_cardnum,d_identifynum,zc_time,user_hos,user_ks,invite_count," +
                "user_role from t_doctor where d_uname=?";
        Object[] params = new Object[]{username};
        Connection conn = getConnection();
        DoctorReadOnly doctorReadOnly = (DoctorReadOnly) DAOUtils.selectOne(getConnection(), sql, params, DoctorReadOnly.class);
        if(doctorReadOnly == null){
            return;
        }
        int userId = doctorReadOnly.getD_id().intValue();

        // 获取 用户头像
        Blob imgBlob = getUserImgBlob(Long.valueOf(userId));
        String headimg = "";
        if(imgBlob!=null){
            headimg = headimg + FileUtils.saveHeadImg(imgBlob,".jpg");
        }

        // 转移医生及公众号信息
        transfer(doctorReadOnly,headimg);
        // 转移公众号资料
        transferPubuserMaterialService.transferMaterialByUserId(userId);
        // 公众号分组
        transferUserGroupService.transferPubUserGroup(userId);
        // 公众号分组粉丝用户
        transferPubuserMemberService.transferPubUserMemberByUserId(userId);
        // 公众号粉丝所在分组数据
        transferPubuserMemberService.transferUserDoctorGroupByUserId(userId);
    }

    /**
     * 根据用户ID查询新的用户表是否已经存在
     * @param doctorId
     * @return
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public DoctorReadOnly findUserInfoById(Long doctorId) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        DoctorReadOnly condition = new DoctorReadOnly();
        condition.setD_id(doctorId);
        DoctorReadOnly doctorReadOnly = findOne(condition);
        return doctorReadOnly;
    }

    @Override
    public List<DoctorReadOnly> findByDate(String date) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select d_id,d_did,d_hid,d_name,d_uname,d_degree,d_gender,d_age,d_zc,d_password,d_email,d_province,d_license," +
                "d_city,district,d_mobile,permission,d_zw,d_sign,d_credit,d_ic_cardnum,d_identifynum,zc_time,user_hos,user_ks,invite_count,user_role" +
                " from t_doctor where D_id > ?";
        Object[] params = {Integer.valueOf(date)};
        List<DoctorReadOnly> list = (List<DoctorReadOnly>) DAOUtils.selectList(getConnection(), sql, params, DoctorReadOnly.class);
        return list;
    }

    @Override
    public void transferDoctorByDate(String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        List<DoctorReadOnly> list = findByDate(date);
        doTransferUserList(list);
    }


    @Override
    public void transferPubUserByDate(String owner, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        DoctorReadOnly doctorReadOnly = findPubUserByUname(owner);
        // 公众号分组
        transferUserGroupService.transferPubUserGroup(doctorReadOnly.getD_id().intValue());
        // 公众号分组粉丝用户以及分组
        transferPubuserMemberService.transferPubMemberByUserAndDate(doctorReadOnly.getD_id().intValue(), date);
    }
}
