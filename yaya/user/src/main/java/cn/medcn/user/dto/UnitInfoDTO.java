package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by LiuLP on 2017/5/8 .
 */
@NoArgsConstructor
@Data
public class UnitInfoDTO {

    private Integer id;

    private String nickname;

    private String headimg;

    //关注人数
    private Integer attentionNum;

    private String province;

    private String city;

    //简介
    private String sign;

    //资料总数量
    private Integer materialNum;

    //会议及文件夹列表
    private List MeetFolderList;

    //资料列表
    private List<MaterialDTO> materialList;

    //当前用户是否已关注此公众号，0表示未关注，1表示已关注
    private Integer attention;


}
