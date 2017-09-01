package cn.medcn.meet.service;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.dto.MeetListInfoDTO;
import cn.medcn.meet.model.InfinityTree;
import cn.medcn.meet.model.InfinityTreeDetail;

import java.util.List;

/**
 * Created by Liuchangling on 2017/7/27.
 */
public interface MeetFolderService extends BaseService<InfinityTree> {

    /**
     * 根据父文件夹ID 查询文件夹下的叶子数据
     *
     * @param pageable
     * @return
     */
    MyPage<MeetFolderDTO> findLeafMeetFolder(Pageable pageable);


    /**
     * 根据单位号ID 获取单位号的会议文件夹
     * @param pageable
     * @return
     */
    MyPage<MeetFolderDTO> findUnitMeetFolder(Pageable pageable);

    /**
     * 查询我的会议文件夹 及 会议列表
     * @param pageable
     * @return
     */
    MyPage<MeetFolderDTO> findMyMeetFolderList(Pageable pageable);


    /**
     * 查询已发布的会议列表
     * @param pageable
     * @return
     */
    MyPage<MeetFolderDTO> findPublishedMeeting(Pageable pageable);

    /**
     * 查询已发布的子文件列表
     * @param pageable
     * @return
     */
    MyPage<MeetFolderDTO> findPublishedSubList(Pageable pageable);


    /**
     * 移除文件夹和文件夹的会议
     * @param id
     * @return
     */
    Integer deleteFolderAndSDetail(String id);

    /**
     * 获取目录层级
     * @param preId
     * @return
     */
    String getAllFolder(String preId);

    /**
     * 移动会议至文件夹
     * @param detail
     * @return
     */
    Integer insertDetail(InfinityTreeDetail detail);

    InfinityTreeDetail selectTreeDetail(InfinityTreeDetail detail);

    void updateTreeDetail(InfinityTreeDetail detail);

    void deleteTreeDetail(InfinityTreeDetail detail);
}
