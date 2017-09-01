package cn.medcn.meet.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.MeetDAO;
import cn.medcn.meet.dao.MeetFolderDAO;
import cn.medcn.meet.dao.MeetFolderDetailDAO;
import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.dto.MeetListInfoDTO;
import cn.medcn.meet.model.InfinityTree;
import cn.medcn.meet.model.InfinityTreeDetail;
import cn.medcn.meet.service.MeetFolderService;
import cn.medcn.meet.support.MeetCheckHelper;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Liuchangling on 2017/7/27.
 */
@Service
public class MeetFloderServiceImpl extends BaseServiceImpl<InfinityTree> implements MeetFolderService {

    @Autowired
    private MeetFolderDAO meetFolderDAO;

    @Autowired
    private MeetFolderDetailDAO meetFolderDetailDAO;

    @Value("${app.file.base}")
    private String appFileBase;

    @Override
    public Mapper<InfinityTree> getBaseMapper() {
        return meetFolderDAO;
    }


    /**
     * 根据父节点ID 查询叶子文件夹及会议
     *
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetFolderDTO> findLeafMeetFolder(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<MeetFolderDTO> page = MyPage.page2Mypage((Page)meetFolderDAO.findLeafMeetFolder(pageable.getParams()));
        checkMeetFolderData(page);
        return page;
    }


    /**
     * 根据单位号ID 获取单位号的会议文件夹及会议列表
     * @param pageable
     * @return
     */
    public MyPage<MeetFolderDTO> findUnitMeetFolder(Pageable pageable){
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<MeetFolderDTO> page = MyPage.page2Mypage((Page)meetFolderDAO.findUnitMeetFolder(pageable.getParams()));
        checkMeetFolderData(page);
        return page;
    }


    /**
     * 查询我的会议文件夹 及 会议列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetFolderDTO> findMyMeetFolderList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        MyPage<MeetFolderDTO> page = MyPage.page2Mypage((Page) meetFolderDAO.findMyMeetFolder(pageable.getParams()));
        checkMeetFolderData(page);
        return page;
    }

    /**
     * 查询已发布的会议列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetFolderDTO> findPublishedMeeting(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetFolderDTO> page = (Page) meetFolderDAO.findPublishedMeeting(pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 查询已发布的子文件列表
     * @param pageable
     * @return
     */
    @Override
    public MyPage<MeetFolderDTO> findPublishedSubList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), Pageable.countPage);
        Page<MeetFolderDTO> page = (Page) meetFolderDAO.findPublishedSubList(pageable.getParams());
        return MyPage.page2Mypage(page);
    }






    /**
     * 删除文件夹
     * @param id
     * @return
     */
    @Override
    public Integer deleteFolder(String id) {
        Integer count1 = deleteByPrimaryKey(id);
        InfinityTreeDetail detail = new InfinityTreeDetail();
        detail.setInfinityId(id);
        meetFolderDetailDAO.delete(detail);
        return count1 ;
    }

    /**
     * 递归删除文件夹
     * @param folderId
     */
    @Override
    public void recursiveDeleteFolder(String folderId) {
        InfinityTree tree = new InfinityTree();
        tree.setPreid(folderId);
        List<InfinityTree> list = select(tree);
        if (CheckUtils.isEmpty(list)) { //没有子文件夹
            deleteFolder(folderId);
        } else {
            for (InfinityTree tree1 : list) {
                recursiveDeleteFolder(tree1.getId());
            }
            deleteFolder(folderId);
        }
    }

    /**
     * 获取目录名称
     * @param id
     * @return
     */
    @Override
    public String getAllFolder(String id) {
        String s = null;
        InfinityTree tree = selectByPrimaryKey(id);
        if(!StringUtils.isEmpty(tree.getPreid()) && !"0".equals(tree.getPreid()) ){
            String preName = getAllFolder(tree.getPreid());
            return preName+ "_" +(tree.getInfinityName() + "-" + tree.getId());
        }else{
            return tree.getInfinityName() + "-" + tree.getId() ;
        }
    }

    /**
     * 移动会议至文件夹
     * @param detail
     * @return
     */
    @Override
    public Integer insertDetail(InfinityTreeDetail detail) {
        return meetFolderDetailDAO.insert(detail);
    }

    /**
     * 查找文件夹里的会议
     * @param detail
     * @return
     */
    @Override
    public InfinityTreeDetail selectTreeDetail(InfinityTreeDetail detail) {
        return meetFolderDetailDAO.selectOne(detail);
    }

    /**
     * 更新文件夹的会议
     * @param detail
     */
    @Override
    public void updateTreeDetail(InfinityTreeDetail detail) {
        meetFolderDetailDAO.updateByPrimaryKeySelective(detail);
    }


    /**
     * 删除文件夹里的会议
     * @param detail
     */
    @Override
    public void deleteTreeDetail(InfinityTreeDetail detail) {
        meetFolderDetailDAO.delete(detail);
    }

    private void checkMeetFolderData(MyPage<MeetFolderDTO> page){
        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (MeetFolderDTO folderDTO : page.getDataList()){
                MeetCheckHelper.checkMeetFolder(folderDTO, appFileBase);
            }
        }
    }

}
