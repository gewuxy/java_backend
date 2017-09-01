package cn.medcn.meet.dto;

import cn.medcn.meet.model.InfinityTree;
import cn.medcn.meet.model.Lecturer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchangling on 2017/7/26.
 * 推荐的会议文件夹信息 DTO
 */
@Data
@NoArgsConstructor
public class RecommendFolderDTO implements Serializable {

    // 父目录ID
    private String id;

    // 目录名称
    private String infinityName;

    // 单位号ID
    private Integer userId;

    // 单位号名称
    private String unitUserName;

    // 单位号头像
    private String headImg;

    // 会议数
    private Integer meetCount;

    // 讲者信息
    private List<Lecturer> lecturerList;



}
