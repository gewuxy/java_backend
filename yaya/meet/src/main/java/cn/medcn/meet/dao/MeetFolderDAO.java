package cn.medcn.meet.dao;

import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.dto.MeetListInfoDTO;
import cn.medcn.meet.dto.RecommendFolderDTO;
import cn.medcn.meet.model.InfinityTree;
import cn.medcn.meet.model.InfinityTreeDetail;
import cn.medcn.meet.model.Lecturer;
import cn.medcn.user.dto.MeetingDTO;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/7/26.
 */
public interface MeetFolderDAO extends Mapper<InfinityTree> {

    /**
     * 根据父节点ID 查询推荐的会议文件夹下的子目录数据
     *
     * @param params
     * @return
     */
    List<MeetFolderDTO> findLeafMeetFolder(Map<String,Object> params);

    /**
     * 根据单位号ID 获取单位号的会议文件夹
     * @return
     */
    List<MeetFolderDTO> findUnitMeetFolder(Map<String,Object> params);

    /**
     * 查询我关注的会议文件夹 及 会议列表
     * @param params
     * @return
     */
    List<MeetFolderDTO> findMyMeetFolder(Map<String,Object> params);

    /**
     * 查询已发布的会议列表
     * @param params
     * @return
     */
    List<MeetFolderDTO> findPublishedMeeting(Map<String, Object> params);

    /**
     * 查询已发布的子文件列表
     * @param params
     * @return
     */
    List<MeetFolderDTO>  findPublishedSubList(Map<String, Object> params);

}
