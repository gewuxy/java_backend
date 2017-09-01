package cn.medcn.meet.dto;

import cn.medcn.meet.model.InfinityTree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchangling on 2017/8/1.
 */
@Data
@NoArgsConstructor
public class MeetFolderTreeDTO implements Serializable {

    private List<InfinityTree> infinityTreeList;

    private List<MeetInfoDTO> meetInfoDTOList;

}
