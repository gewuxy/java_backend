package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by LiuLP on 2017/8/23/023.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TitleDTO {

    //职称一级
    private String title;
    //职称二级
    private List<String> grade;

}
