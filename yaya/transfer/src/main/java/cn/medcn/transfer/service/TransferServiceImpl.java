package cn.medcn.transfer.service;

import cn.medcn.transfer.service.readonly.*;
import cn.medcn.transfer.service.readonly.impl.*;
import cn.medcn.transfer.utils.CommonConnctionUtils;
import cn.medcn.transfer.utils.LogUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/14.
 */
public class TransferServiceImpl implements TransferService {


    private TransferMeetService transferMeetService = new TransferMeetServiceImpl();

    private TransferDoctorService transferDoctorService = new TransferDoctorServiceImpl();

    private TransferPubuserMaterialService transferPubuserMaterialService = new TransferPubuserMaterialServiceImpl();

    private TransferUserGroupService transferUserGroupService = new TransferUserGroupServiceImpl();

    private TransferPubuserMemberService transferPubuserMemberService = new TransferPubuserMemberServiceImpl();

    private TransferGoodsService transferGoodsService = new TransferGoodsServiceImpl();


    @Override
    public void transfer() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        try {
            //转移医生用户信息
            //LogUtils.debug(this.getClass(), "开始转换医生用户信息 ....");
          //  transferDoctorService.transferUserdata();

            //转换医生用户信息代码 ....
//            LogUtils.debug(this.getClass(), "完成医生用户信息转换操作");
//
//            // 转换公众号资料
//            transferPubuserMaterialService.transferMaterial();
//
//            // 转换公众号分组
//            transferUserGroupService.transferPubUserGroup();
//
//            // 转换公众号粉丝
//            transferPubuserMemberService.transferPubMemberdata();
//
//            // 转换公众号粉丝用户所在分组
//            transferPubuserMemberService.transferUserDoctorGroupData();
//
//            //转换会议信息
//            transferMeetService.transfer();

            LogUtils.debug(this.getClass(), "开始转换象城礼品数据 ....");
            transferGoodsService.transferGoods();

            LogUtils.debug(this.getClass(), " ========================= 操作成功 ======================== ");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            CommonConnctionUtils.closeConnection();
        }
    }


    @Override
    public void transferByOwner(String owner) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, IOException {

        transferDoctorService.transferUserByUsername(owner);

        transferMeetService.transferByOwner(owner);
    }

    /**
     * 根据起始会议ID转换单位号的会议
     *
     * @param owner
     * @param date
     */
    @Override
    public void transferMeetByOwner(String owner, String date) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, IOException {
        transferMeetService.transferByOwerAndDate(owner, date);
    }

    /**
     * 根据起始ID转换用户信息
     *
     * @param date
     */
    @Override
    public void transferDoctor(String date) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, IOException {
        transferDoctorService.transferDoctorByDate(date);
    }

    @Override
    public void transferPubUser(String owner, String date) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        transferDoctorService.transferPubUserByDate(owner, date);
    }

    @Override
    public void transferByOwnerAndDate(String owner, String date) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException, SQLException {
        transferPubUser(owner, date);
        transferMeetByOwner(owner,date);
    }
}
